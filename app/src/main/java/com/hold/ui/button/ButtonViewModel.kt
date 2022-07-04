package com.hold.ui.button

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hold.arch.common.livedata.SingleLiveEvent
import com.hold.domain.model.EndgameModel
import com.hold.domain.model.EndgameState
import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser
import com.hold.domain.usecase.leaderboard.GetUserLocalRecordUseCase
import com.hold.domain.usecase.user.GetUserNameUseCase
import com.hold.domain.usecase.user.SaveNewResultUseCase
import com.hold.domain.usecase.user.SaveUserNameUseCase
import com.hold.ui.button.model.ButtonActions
import com.hold.ui.button.model.ButtonRoute
import com.hold.ui.button.model.GameState
import com.hold.ui.button.model.MainGameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ButtonViewModel(
    private val saveNewResultUseCase: SaveNewResultUseCase,
    private val getUserLocalRecordUseCase: GetUserLocalRecordUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
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
            val gameUser = getUserNameUseCase.getName()
            _state.value = _state.value.copy(gameUser = gameUser)
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
            ButtonActions.ClickOnPayDay -> {}
            ButtonActions.ClickOnPayOnce -> {}
            ButtonActions.ClickOnWatchAdd -> {}

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
        viewModelScope.launch {
            _state.value.endGameData?.currentValue?.let { current ->
                kotlin.runCatching {
                    saveNewResultUseCase.invoke(current)
                }
                    .onSuccess {
                        _state.value = _state.value.copy(timer = null)
                        _state.value =
                            _state.value.copy(endgameState = EndgameState.END_OR_CONTINUE)
                        if (_state.value.gameUser == null || _state.value.gameUser?.userName?.isEmpty() == true) {
                            _state.value = _state.value.copy(gameState = GameState.USERNAME_INPUT)
                        } else {
                            _state.value = _state.value.copy(gameState = GameState.BUTTON)
                        }
                    }
                    .onFailure {
                        _state.value =
                            _state.value.copy(errorText = it.localizedMessage ?: "Some error1 =(")
                    }
            }
        }
    }

    private fun nicknameSave(nickName: String) {
        Timber.d("nicknameSave $nickName")
        viewModelScope.launch {
            var data = getUserNameUseCase.getName() ?: GameUser()// _state.value.gameUser
            data = data.copy(userName = nickName)
            data.let { user ->
                kotlin.runCatching {
                    saveUserNameUseCase.saveName(nickName, true)
                }
                    .onSuccess {
                        _state.value = _state.value.copy(gameUser = user)
                        _state.value = _state.value.copy(gameState = GameState.BUTTON)
                    }
                    .onFailure {
                        _state.value =
                            _state.value.copy(errorText = it.localizedMessage ?: "Some error! =(")
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
                )
            )
            _state.value = _state.value.copy(gameState = GameState.END_GAME)
        }
    }


    private fun startTimer() {
        viewModelScope.launch {
            _state.value = _state.value.copy(timer = null)
            _state.value =
                _state.value.copy(gameRecord = getUserLocalRecordUseCase.invoke()?.result)
            startTime = System.currentTimeMillis()
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


}