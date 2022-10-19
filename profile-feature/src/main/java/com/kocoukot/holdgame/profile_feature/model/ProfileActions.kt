package com.kocoukot.holdgame.profile_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeActions


sealed class ProfileActions : ComposeActions {
    object ClickOnBack : ProfileActions()
    data class ClickOnSaveNickname(val nickname: String) : ProfileActions()
}
