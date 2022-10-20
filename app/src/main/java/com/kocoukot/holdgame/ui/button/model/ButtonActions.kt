package com.kocoukot.holdgame.ui.button.model

import com.kocoukot.holdgame.core_mvi.ComposeActions


sealed class ButtonActions : ComposeActions {
    object ClickOnToLeaderboard : ButtonActions()
    object ClickOnToProfile : ButtonActions()
    object ClickOnBack : ButtonActions()
    object PressDownButton : ButtonActions()
    object PressUpButton : ButtonActions()
    object PressedBackButton : ButtonActions()


    object ClickOnContinue : ButtonActions()
    object ClickOnCancel : ButtonActions()

    object ClickOnPay : ButtonActions()
    object ClickOnWatchAdd : ButtonActions()

    object ClickOnPayOnce : ButtonActions()
    object ClickOnPayDay : ButtonActions()

    data class NickNameSave(val nickname: String) : ButtonActions()


}
