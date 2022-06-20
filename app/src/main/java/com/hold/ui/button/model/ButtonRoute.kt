package com.hold.ui.button.model

sealed class ButtonRoute {
    object ToLeaderboard : ButtonRoute()
    object ToProfile : ButtonRoute()
    object CloseApp : ButtonRoute()
}
