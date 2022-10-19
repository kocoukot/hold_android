package com.kocoukot.holdgame.ui.button

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.core_mvi.BaseViewModel
import com.kocoukot.holdgame.core_mvi.ComposeActions
import com.kocoukot.holdgame.core_mvi.ReceiveEvent
import com.kocoukot.holdgame.domain.usecase.user.*
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserMaxRecordUseCase
import com.kocoukot.holdgame.model.user.GameResult
import com.kocoukot.holdgame.model.user.GameUser
import com.kocoukot.holdgame.ui.button.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ButtonViewModel(
    private val saveNewResultUseCase: SaveNewResultUseCase,
    private val getUserLocalRecordUseCase: GetUserMaxRecordUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val saveLastResultUseCase: SaveLastResultUseCase,
    private val getLastResultUseCase: GetLastResultUseCase,
    private val saveDayPurchaseDateUseCase: SaveDayPurchaseDateUseCase,
    private val getDayPurchaseUseCase: GetDayPurchaseUseCase,
    private val getGlobalTimeUseCase: GetGlobalTimeUseCase,
) : BaseViewModel.Base<MainGameState>(
    mState = MutableStateFlow(MainGameState())
), ReceiveEvent {

    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var stopTime = 0L

    init {
        viewModelScope.launch {

            val purchaseDate =
                withContext(Dispatchers.Default) { getDayPurchaseUseCase.invoke() }

            updateInfo {
                copy(
                    gameUser = getUserNameUseCase.getName(),
                    gameRecord = getUserLocalRecordUseCase.invoke()?.result,
                    timer = getLastResultUseCase.invoke(),
                    couldContinue =
                    if (purchaseDate != null && purchaseDate > 0)
                        CouldContinueType.FOR_DAY
                    else if (timer != null && timer > 0)
                        CouldContinueType.SINGLE
                    else
                        CouldContinueType.NONE
                )
            }
        }
    }

    override fun setInputActions(action: ComposeActions) {
        when (action) {
            ButtonActions.ClickOnToLeaderboard -> sendEvent(ButtonRoute.ToLeaderboard)
            ButtonActions.ClickOnToProfile -> sendEvent(ButtonRoute.ToProfile)

            ButtonActions.PressDownButton -> onButtonStartHold()
            ButtonActions.PressUpButton -> onButtonStopHold()
            ButtonActions.ClickOnBack -> clickGoBack()
            ButtonActions.ClickOnCancel -> closeEndGame()
            ButtonActions.PressedBackButton -> backPressed()

            is ButtonActions.NickNameSave -> nicknameSave(action.nickname)

            ButtonActions.ClickOnContinue -> continueGame()
            ButtonActions.ClickOnPay -> payForGame()
            ButtonActions.ClickOnPayDay -> payMoney(state.value.productDetails.find { it.productId == "one_day_try" })
            ButtonActions.ClickOnPayOnce -> payMoney(state.value.productDetails.find { it.productId == "one_try" })
            ButtonActions.ClickOnWatchAdd -> showAd()
        }
    }

    private fun backPressed() {
        when (state.value.gameState) {
            GameState.BUTTON -> ButtonRoute.CloseApp
            GameState.END_GAME -> {
                when (mState.value.endgameState) {
                    EndgameState.END_OR_CONTINUE -> clickGoBack()
                    EndgameState.PAY_OR_WATCH ->
                        updateInfo {
                            copy(endgameState = EndgameState.END_OR_CONTINUE)
                        }
                    EndgameState.PAY_AMOUNT ->
                        updateInfo {
                            copy(endgameState = EndgameState.PAY_OR_WATCH)
                        }
                }
            }
            GameState.USERNAME_INPUT ->
                updateInfo { copy(gameState = GameState.BUTTON) }
        }
    }

    private fun clickGoBack() {
        if (mState.value.couldContinue == CouldContinueType.FOR_DAY) {
            checkPurchaseValidation {
                if (it) {
                    updateInfo {
                        copy(
                            gameState = GameState.BUTTON,
                            endgameState = EndgameState.END_OR_CONTINUE
                        )
                    }
                } else {
                    closeEndGame()
                }
            }
        } else {
            closeEndGame()
        }
    }

    private fun closeEndGame() {
        updateInfo { copy(isLoading = true) }
        viewModelScope.launch {
            mState.value.endGameData?.currentValue?.let { current ->
                kotlin.runCatching { saveNewResultUseCase.invoke(current) }
                    .onSuccess {
                        saveLastResultUseCase.invoke(null)
                        updateInfo {
                            copy(
                                endgameState = EndgameState.END_OR_CONTINUE,
                                timer = null
                            )
                        }
                        updateInfo {
                            copy(
                                gameState =
                                if (mState.value.gameUser == null ||
                                    mState.value.gameUser?.userName?.isEmpty() == true
                                )
                                    GameState.USERNAME_INPUT
                                else
                                    GameState.BUTTON
                            ).also { updateInfo { copy(isLoading = false) } }
                        }
                    }
                    .onFailure {
                        updateInfo {
                            copy(
                                errorText = it.localizedMessage ?: "Some error1 =(",
                                isLoading = false
                            )
                        }
                    }
            }
        }
    }

    private fun nicknameSave(nickName: String) {
        updateInfo { copy(isLoading = true) }

        viewModelScope.launch {
            var data = getUserNameUseCase.getName() ?: GameUser()
            data = data.copy(userName = nickName)
            data.let { user ->
                kotlin.runCatching { saveUserNameUseCase.saveName(nickName, true) }
                    .onSuccess {
                        updateInfo {
                            copy(
                                gameUser = user,
                                gameState = GameState.BUTTON,
                                isLoading = false
                            )
                        }
                    }
                    .onFailure {
                        updateInfo {
                            copy(
                                errorText = it.localizedMessage ?: "Some error! =(",
                                isLoading = false
                            )
                        }
                    }
            }
        }
    }


    private fun onButtonStartHold() {
        startTimer()
    }

    private fun onButtonStopHold() {
        handler.removeCallbacksAndMessages(null)
        stopTime = System.currentTimeMillis()
        val newRecord = GameResult(
            date = stopTime,
            result = stopTime - startTime
        )
        setEndGameData(newRecord)
    }

    private fun setEndGameData(newValue: GameResult) {
        viewModelScope.launch {
            val gameRecord = getUserLocalRecordUseCase.invoke()
            if (mState.value.couldContinue == CouldContinueType.FOR_DAY) {
                mState.value.timer?.let { timer ->
                    saveLastResultUseCase.invoke(timer)
                }
            }
            updateInfo {
                copy(
                    endGameData = EndgameModel(
                        recordValue = gameRecord,
                        currentValue = newValue,
                    ),
                    couldContinue = if (mState.value.couldContinue == CouldContinueType.FOR_DAY) CouldContinueType.FOR_DAY else CouldContinueType.NONE,
                    gameState = GameState.END_GAME
                )
            }
        }
    }


    private fun startTimer() {
        viewModelScope.launch {
            startTime = if (mState.value.couldContinue == CouldContinueType.NONE) {
                updateInfo { copy(timer = null) }
                System.currentTimeMillis()
            } else {
                System.currentTimeMillis() - (mState.value.timer ?: 0L)
            }
            getUserLocalRecordUseCase.invoke()?.result.let { result ->
                updateInfo { copy(gameRecord = result) }
            }
            handler.postDelayed({
                update()
            }, 0)
        }

    }

    private fun update() {
        updateInfo { copy(timer = System.currentTimeMillis() - startTime) }
        handler.postDelayed({
            update()
        }, 0)
    }

    private fun continueGame() {
        checkPurchaseValidation {
            if (it) {
                updateInfo {
                    copy(
                        endgameState = EndgameState.END_OR_CONTINUE,
                        gameState = GameState.BUTTON,
                        couldContinue = CouldContinueType.FOR_DAY,
                    )
                }
            } else {
                updateInfo {
                    copy(
                        endgameState = EndgameState.PAY_OR_WATCH,
                        couldContinue = CouldContinueType.NONE,
                    )
                }
            }
        }
    }

    private fun payForGame() {
        updateInfo { copy(endgameState = EndgameState.PAY_AMOUNT) }
    }

    private fun showAd() {
        sendEvent(ButtonRoute.ShowAd)
    }

    fun onAddLoaded(isLoaded: Boolean) {
        updateInfo { copy(isAddLoaded = isLoaded) }
    }

    fun onUserGotOneMoreTry() {
        mState.value.timer?.let { timer ->
            viewModelScope.launch {
                saveLastResultUseCase.invoke(timer)
            }
        }
        updateInfo {
            copy(
                gameState = GameState.BUTTON,
                couldContinue = CouldContinueType.SINGLE,
                endgameState = EndgameState.END_OR_CONTINUE
            )
        }
    }

    fun onUserGotTryForDay() {
        val currentDate = System.currentTimeMillis()
        mState.value.timer?.let { timer ->
            viewModelScope.launch {
                saveLastResultUseCase.invoke(timer)
            }
        }
        viewModelScope.launch {
            saveDayPurchaseDateUseCase.invoke(currentDate)
        }
        updateInfo {
            copy(
                gameState = GameState.BUTTON,
                couldContinue = CouldContinueType.FOR_DAY,
                endgameState = EndgameState.END_OR_CONTINUE
            )
        }
//        mState.value = mState.value.copy(
//            gameState = GameState.BUTTON,
//            couldContinue = CouldContinueType.FOR_DAY,
//            endgameState = EndgameState.END_OR_CONTINUE
//        )
    }

    fun onBillsGot(productDetails: List<ProductDetails>) {
        updateInfo { copy(productDetails = productDetails) }
    }

    private fun payMoney(product: ProductDetails?) {
        product?.let {
            sendEvent(ButtonRoute.LaunchBill(it))
        }
    }

    private fun checkPurchaseValidation(couldContinue: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (mState.value.couldContinue == CouldContinueType.FOR_DAY) {
                kotlin.runCatching {
                    getGlobalTimeUseCase.getGlobalTime()
                }
                    .onSuccess { globalTime ->
                        checkAvailable(globalTime ?: 0) { couldContinue.invoke(it) }
                    }
                    .onFailure {
                        checkAvailable(System.currentTimeMillis()) { couldContinue.invoke(it) }
                    }
            } else {
                saveDayPurchaseDateUseCase.invoke(null)
                couldContinue.invoke(false)
            }
        }
    }

    private suspend fun checkAvailable(time: Long, couldContinue: (Boolean) -> Unit) {
        val purchaseDate = getDayPurchaseUseCase.invoke() ?: 0
        Timber.i("checkAvailable time $time purchaseDate $purchaseDate dif ${time - purchaseDate}")
        if (((time - purchaseDate) / 1000) in 0..86400) {
            couldContinue.invoke(true)
        } else {
            saveDayPurchaseDateUseCase.invoke(null)
            couldContinue.invoke(false)
        }
    }


}