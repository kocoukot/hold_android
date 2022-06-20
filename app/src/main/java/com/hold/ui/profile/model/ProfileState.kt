package com.hold.ui.profile.model

data class ProfileState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val userNickname: String = ""
)
