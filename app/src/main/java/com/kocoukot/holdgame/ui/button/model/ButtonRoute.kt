package com.kocoukot.holdgame.ui.button.model

sealed class ButtonRoute {
    object ToLeaderboard : ButtonRoute()
    object ToProfile : ButtonRoute()
    object CloseApp : ButtonRoute()
    object ShowAd : ButtonRoute()

}
