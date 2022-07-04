package com.hold.domain.model

import com.hold.domain.model.user.GameResult

data class EndgameModel(
    val recordValue: GameResult?,
    val currentValue: GameResult?
)