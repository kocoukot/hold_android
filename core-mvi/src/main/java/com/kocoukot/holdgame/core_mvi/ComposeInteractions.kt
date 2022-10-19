package com.kocoukot.holdgame.core_mvi

import kotlinx.coroutines.flow.StateFlow


interface ComposeActions


interface ComposeRoute {
    object OnBack : ComposeRoute
}

interface ComposeState

interface StateCommunication<T> {
    val state: StateFlow<ComposeState>
    fun updateInfo(info: suspend T.() -> T)
}

