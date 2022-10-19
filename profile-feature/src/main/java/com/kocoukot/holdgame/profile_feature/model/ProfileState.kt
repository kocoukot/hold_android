package com.kocoukot.holdgame.profile_feature.model

data class ProfileState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val userNickname: String = ""
)
