package com.kocoukot.holdgame.domain.model


import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.model.user.GameUser

data class LeaderboardModel(
    val localUSerRecord: GameResult?,
    val personalRecords: GameUser? = null,
    val worldRecordRecords: List<GameGlobalUser>? = null
)
