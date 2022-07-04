package com.hold.ui.leaderboard.model

import com.hold.domain.model.user.GameGlobalUser
import com.hold.domain.model.user.GameUser

data class LeaderboardModel(
    val personalRecords: GameUser? = null,
    val worldRecordRecords: List<GameGlobalUser>? = null
)
