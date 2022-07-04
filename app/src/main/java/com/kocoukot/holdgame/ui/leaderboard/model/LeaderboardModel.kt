package com.kocoukot.holdgame.ui.leaderboard.model

import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.model.user.GameUser

data class LeaderboardModel(
    val personalRecords: GameUser? = null,
    val worldRecordRecords: List<GameGlobalUser>? = null
)
