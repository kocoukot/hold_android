package com.kocoukot.holdgame.ui.profile.model


sealed class ProfileActions {
    object ClickOnBack : ProfileActions()
    data class ClickOnSaveNickname(val nickname: String) : ProfileActions()
}
