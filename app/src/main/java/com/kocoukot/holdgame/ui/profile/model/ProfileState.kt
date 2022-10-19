package com.kocoukot.holdgame.ui.profile.model

data class ProfileState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val userNickname: String = ""
)
