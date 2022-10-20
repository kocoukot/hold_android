package com.kocoukot.holdgame.ui.button.domain

import com.kocoukot.holdgame.model.user.GameResult

data class EndgameModel(
    val recordValue: GameResult?,
    val currentValue: GameResult?
)