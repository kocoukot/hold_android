package com.hold.arch.mvvm.viewmodel

class SideEffectHandler(
    val onLoading: ((Boolean) -> Unit)? = null,
    val onError: ((Throwable) -> Unit)? = null,
)
