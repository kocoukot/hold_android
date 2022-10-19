package com.kocoukot.holdgame.core_mvi

import kotlinx.coroutines.flow.Flow

interface ComposeRoute

interface RouteCommunication {
    //    val mSteps: Channel<T>
    val steps: Flow<ComposeRoute>
}
