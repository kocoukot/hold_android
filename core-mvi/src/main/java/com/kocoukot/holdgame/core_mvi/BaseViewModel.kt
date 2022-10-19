package com.kocoukot.holdgame.core_mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface BaseViewModel : RouteCommunication, ReceiveEvent {


    open class Base<T : ComposeState>(
        private val mSteps: Channel<ComposeRoute> = Channel(),
        val mState: MutableStateFlow<T>,
    ) : ViewModel(), BaseViewModel, SendEvent, StateCommunication<T> {

        override val steps: Flow<ComposeRoute> = mSteps.receiveAsFlow()
        override val state = mState.asStateFlow()

        override fun setInputAction(event: ComposeActions) {

        }

        override fun sendEvent(event: ComposeRoute) {
            viewModelScope.launch { mSteps.send(event) }
        }

        override fun updateInfo(info: suspend T.() -> T) {
            viewModelScope.launch {
                mState.update { info.invoke(it) }
            }

        }
    }

}


interface SendEvent {
    fun sendEvent(event: ComposeRoute)
}

interface ReceiveEvent {
    fun setInputAction(event: ComposeActions)
}
