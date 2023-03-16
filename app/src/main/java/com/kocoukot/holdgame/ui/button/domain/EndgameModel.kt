package com.kocoukot.holdgame.ui.button.domain

import com.kocoukot.holdgame.domain.model.user.GameResult

data class EndgameModel(
    val recordValue: GameResult?,
    val currentValue: GameResult?
)