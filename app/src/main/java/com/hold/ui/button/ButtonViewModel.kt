package com.hold.ui.button

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hold.arch.common.livedata.SingleLiveEvent
import com.hold.domain.model.EndgameModel
import com.hold.domain.model.EndgameState
import com.hold.domain.model.user.GameResult
import com.hold.domain.usecase.user.GetUserLocalRecordUseCase
import com.hold.domain.usecase.user.SaveNewResultUseCase
import com.hold.ui.button.model.ButtonActions
import com.hold.ui.button.model.ButtonRoute
import com.hold.ui.button.model.MainGameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ButtonViewModel(
    private val saveNewResultUseCase: SaveNewResultUseCase,
    private val getUserLocalRecordUseCase: GetUserLocalRecordUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<MainGameState> =
        MutableStateFlow(MainGameState())
    val state: StateFlow<MainGameState> = _state

    private val _steps: SingleLiveEvent<ButtonRoute> = SingleLiveEvent()
    val steps: SingleLiveEvent<ButtonRoute> = _steps


    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var stopTime = 0L


    fun setInputActions(action: ButtonActions) {
        when (action) {
            ButtonActions.ClickOnToLeaderboard -> goLeaderboard()
            ButtonActions.ClickOnToProfile -> goProfile()

            ButtonActions.PressDownButton -> onButtonStartHold()
            ButtonActions.PressUpButton -> onButtonStopHold()
            ButtonActions.ClickOnBack, ButtonActions.ClickOnCancel -> closeEndGame()


            ButtonActions.ClickOnContinue -> continueGame()
            ButtonActions.ClickOnPay -> payForGame()
            ButtonActions.ClickOnPayDay -> {}
            ButtonActions.ClickOnPayOnce -> {}
            ButtonActions.ClickOnWatchAdd -> {}
        }
    }

    private fun closeEndGame() {
        viewModelScope.launch {
            _state.value.endGameData?.currentValue?.let { current ->
                saveNewResultUseCase.invoke(current)
            }
            _state.value = _state.value.copy(timer = null)

        }
        _state.value = _state.value.copy(endgameState = EndgameState.END_OR_CONTINUE)
        _state.value = _state.value.copy(isEndGame = false)
    }

    private fun goLeaderboard() {
        _steps.value = ButtonRoute.ToLeaderboard
    }

    private fun goProfile() {
        _steps.value = ButtonRoute.ToProfile
    }


    private fun onButtonStartHold() {
        startTimer()
    }

    private fun onButtonStopHold() {
        stopTime = System.currentTimeMillis()
        val newRecord = GameResult(
            date = stopTime,
            result = stopTime - startTime
        )
        viewModelScope.launch {
            println("new record $newRecord")
//            saveNewResultUseCase.invoke(newRecord)
        }

        handler.removeCallbacksAndMessages(null)
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
            _state.value = _state.value.copy(isEndGame = true)
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