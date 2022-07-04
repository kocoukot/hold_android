package com.kocoukot.holdgame.arch.mvvm.viewmodel

class SideEffectHandler(
    val onLoading: ((Boolean) -> Unit)? = null,
    val onError: ((Throwable) -> Unit)? = null,
)
