package com.kocoukot.holdgame.leaderboard_feature.model


import com.kocoukot.holdgame.model.user.GameGlobalUser
import com.kocoukot.holdgame.model.user.GameResult
import com.kocoukot.holdgame.model.user.GameUser

data class LeaderboardModel(
    val localUSerRecord: GameResult?,
    val personalRecords: GameUser? = null,
    val worldRecordRecords: List<GameGlobalUser>? = null
)
