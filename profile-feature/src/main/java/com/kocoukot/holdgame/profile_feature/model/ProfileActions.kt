package com.kocoukot.holdgame.profile_feature.model


sealed class ProfileActions {
    object ClickOnBack : ProfileActions()
    data class ClickOnSaveNickname(val nickname: String) : ProfileActions()
}
