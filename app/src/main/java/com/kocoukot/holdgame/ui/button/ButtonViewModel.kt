package com.kocoukot.holdgame.ui.button

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.domain.model.EndgameModel
import com.kocoukot.holdgame.domain.model.EndgameState
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.model.user.GameUser
import com.kocoukot.holdgame.domain.usecase.leaderboard.GetUserLocalRecordUseCase
import com.kocoukot.holdgame.domain.usecase.user.*
import com.kocoukot.holdgame.ui.button.model.ButtonActions
import com.kocoukot.holdgame.ui.button.model.ButtonRoute
import com.kocoukot.holdgame.ui.button.model.GameState
import com.kocoukot.holdgame.ui.button.model.MainGameState
import com.kocoukot.holdgame.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ButtonViewModel(
    private val saveNewResultUseCase: SaveNewResultUseCase,
    private val getUserLocalRecordUseCase: GetUserLocalRecordUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val saveLastResultUseCase: SaveLastResultUseCase,
    private val clearLastResultUseCase: ClearLastResultUseCase,
    private val getLastResultUseCase: GetLastResultUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<MainGameState> =
        MutableStateFlow(MainGameState())
    val state = _state.asStateFlow()

    private val _steps: SingleLiveEvent<ButtonRoute> = SingleLiveEvent()
    val steps: SingleLiveEvent<ButtonRoute> = _steps


    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var stopTime = 0L

    init {

        viewModelScope.launch {
            val timer = getLastResultUseCase.invoke()
            _state.value = _state.value.copy(
                gameUser = getUserNameUseCase.getName(),
                gameRecord = getUserLocalRecordUseCase.invoke()?.result,
                timer = timer,
                couldContinue = timer != null && timer > 0
            )
        }
    }

    fun setInputActions(action: ButtonActions) {
        when (action) {
            ButtonActions.ClickOnToLeaderboard -> goLeaderboard()
            ButtonActions.ClickOnToProfile -> goProfile()

            ButtonActions.PressDownButton -> onButtonStartHold()
            ButtonActions.PressUpButton -> onButtonStopHold()
            ButtonActions.ClickOnBack, ButtonActions.ClickOnCancel -> closeEndGame()
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
                when (_state.value.endgameState) {
                    EndgameState.END_OR_CONTINUE -> closeEndGame()
                    EndgameState.PAY_OR_WATCH -> _state.value =
                        _state.value.copy(endgameState = EndgameState.END_OR_CONTINUE)
                    EndgameState.PAY_AMOUNT -> _state.value =
                        _state.value.copy(endgameState = EndgameState.PAY_OR_WATCH)
                }
            }
            GameState.USERNAME_INPUT -> _state.value =
                _state.value.copy(gameState = GameState.BUTTON)
        }
    }

    private fun closeEndGame() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            _state.value.endGameData?.currentValue?.let { current ->
                kotlin.runCatching { saveNewResultUseCase.invoke(current) }
                    .onSuccess {
                        clearLastResultUseCase.invoke()
                        _state.value =
                            _state.value.copy(
                                endgameState = EndgameState.END_OR_CONTINUE,
                                timer = null
                            )
                        if (_state.value.gameUser == null || _state.value.gameUser?.userName?.isEmpty() == true) {
                            _state.value = _state.value.copy(gameState = GameState.USERNAME_INPUT)
                        } else {
                            _state.value = _state.value.copy(gameState = GameState.BUTTON)
                        }
                        _state.value = _state.value.copy(isLoading = false)
                    }
                    .onFailure {
                        _state.value =
                            _state.value.copy(
                                errorText = it.localizedMessage ?: "Some error1 =(",
                                isLoading = false
                            )
                    }
            }
        }
    }

    private fun nicknameSave(nickName: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            var data = getUserNameUseCase.getName() ?: GameUser()// _state.value.gameUser
            data = data.copy(userName = nickName)
            data.let { user ->
                kotlin.runCatching {
                    saveUserNameUseCase.saveName(nickName, true)
                }
                    .onSuccess {
                        _state.value = _state.value.copy(
                            gameUser = user,
                            gameState = GameState.BUTTON,
                            isLoading = false
                        )
                    }
                    .onFailure {
                        _state.value =
                            _state.value.copy(
                                errorText = it.localizedMessage ?: "Some error! =(",
                                isLoading = false
                            )
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
            _state.value = _state.value.copy(
                endGameData = EndgameModel(
                    recordValue = gameRecord,
                    currentValue = newValue,

                    ),
                couldContinue = false,
                gameState = GameState.END_GAME
            )
        }
    }


    private fun startTimer() {
        viewModelScope.launch {
            if (!_state.value.couldContinue) {
                _state.value = _state.value.copy(timer = null)
                startTime = System.currentTimeMillis()
            } else {
                startTime = System.currentTimeMillis() - (_state.value.timer ?: 0L)
            }

            _state.value =
                _state.value.copy(gameRecord = getUserLocalRecordUseCase.invoke()?.result)
            handler.postDelayed({
                update()
            }, 0)
        }

    }

    private fun goLeaderboard() {
        _steps.value = ButtonRoute.ToLeaderboard
    }

    private fun goProfile() {
        _steps.value = ButtonRoute.ToProfile
    }

    private fun update() {
        _state.value = _state.value.copy(timer = System.currentTimeMillis() - startTime)
        handler.postDelayed({
            update()
        }, 0)
    }

    private fun continueGame() {
        _state.value = _state.value.copy(endgameState = EndgameState.PAY_OR_WATCH)
    }

    private fun payForGame() {
        _state.value = _state.value.copy(endgameState = EndgameState.PAY_AMOUNT)
    }

    private fun showAd() {
//        viewModelScope.launch {
//            delay(1000)
//            onUserGotOneMoreTry()
//        }
        _steps.value = ButtonRoute.ShowAd
    }

    fun onAddLoaded(isLoaded: Boolean) {
        _state.value = _state.value.copy(isAddLoaded = isLoaded)
    }

    fun onUserGotOneMoreTry() {
        _state.value.timer?.let { timer ->
            viewModelScope.launch {
                saveLastResultUseCase.invoke(timer)
            }
        }

        _state.value = _state.value.copy(
            gameState = GameState.BUTTON,
            couldContinue = true,
            endgameState = EndgameState.END_OR_CONTINUE
        )
    }

    fun onUserGotTryForDay() {
        _state.value = _state.value.copy(
            gameState = GameState.BUTTON,
            couldContinue = true,
            endgameState = EndgameState.END_OR_CONTINUE
        )
    }

    fun onBillsGot(productDetails: List<ProductDetails>) {
        _state.value = _state.value.copy(productDetails = productDetails)
    }

    private fun payMoney(product: ProductDetails?) {
        product?.let {
            _steps.value = ButtonRoute.LaunchBill(it)
        }
    }
}