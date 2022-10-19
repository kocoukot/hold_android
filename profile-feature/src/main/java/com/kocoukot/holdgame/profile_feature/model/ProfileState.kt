package com.kocoukot.holdgame.profile_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeState

data class ProfileState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val userNickname: String = ""
) : ComposeState
