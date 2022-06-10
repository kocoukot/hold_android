package com.hold.ui.button

import android.os.Handler
import android.os.Looper
import com.hold.arch.mvvm.viewmodel.StatefulViewModel
import com.hold.domain.model.user.GameRecord
import com.hold.domain.usecase.user.SetNewResultUseCase

class ButtonViewModel(
    private val setNewResultUseCase: SetNewResultUseCase,
) : StatefulViewModel<ButtonScreenState>() {
    override val state: ButtonScreenState = ButtonScreenState()

    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var stopTime = 0L


    fun onButtonStartHold() {
        startTimer()
    }

    fun onButtonStopHold() {
        stopTime = System.currentTimeMillis()
        val newRecord = GameRecord(
            date = stopTime,
            result = stopTime - startTime
        )
        setNewResultUseCase.invoke(newRecord)
        handler.removeCallbacksAndMessages(null)
    }


    private fun startTimer() {
        startTime = System.currentTimeMillis()
        handler.postDelayed({
            update()
        }, 0)

    }

    private fun update() {
        state.timer = System.currentTimeMillis() - startTime
        handler.postDelayed({
            update()
        }, 0)
    }


}