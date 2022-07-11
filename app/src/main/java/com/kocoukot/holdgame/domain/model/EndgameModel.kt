package com.kocoukot.holdgame.domain.model

import com.kocoukot.holdgame.domain.model.user.GameResult

data class EndgameModel(
    val recordValue: GameResult?,
    val currentValue: GameResult?
)