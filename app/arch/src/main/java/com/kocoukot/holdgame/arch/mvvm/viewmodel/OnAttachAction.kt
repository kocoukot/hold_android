package com.kocoukot.holdgame.arch.mvvm.viewmodel

data class OnAttachAction(
    val action: () -> Unit,
    val executeAtFirstAttach: Boolean,
)