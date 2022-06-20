package com.hold.ui.button.model

import com.hold.domain.model.user.GameResult

sealed class ButtonRoute {
    object ToLeaderboard : ButtonRoute()
    object ToProfile : ButtonRoute()
    data class ToEndGame(val gameResult: GameResult) : ButtonRoute()
}
