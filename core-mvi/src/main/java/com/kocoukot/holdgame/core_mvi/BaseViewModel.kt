package com.kocoukot.holdgame.core_mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface BaseViewModel : SendEvent {

    open class Base<T : ComposeState>(
        private val mSteps: Channel<ComposeRoute> = Channel(),
        protected val mState: MutableStateFlow<T>,
    ) : ViewModel(), BaseViewModel, StateCommunication<T>, ObserveSteps<ComposeRoute> {

        override val state = mState.asStateFlow()

        override fun sendEvent(event: ComposeRoute) {
            viewModelScope.launch { mSteps.send(event) }
        }


        override fun updateInfo(info: suspend T.() -> T) {
            viewModelScope.launch {
                mState.update { info.invoke(it) }
            }
        }

        override fun observeSteps(): Flow<ComposeRoute> = mSteps.receiveAsFlow()
    }

}

interface ObserveSteps<T : ComposeRoute> {
    fun observeSteps(): Flow<T>
}

interface SendEvent {
    fun sendEvent(event: ComposeRoute)
}

interface ReceiveEvent {
    fun setInputActions(action: ComposeActions)
}
