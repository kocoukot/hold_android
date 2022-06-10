package com.hold.ui.leaderboard.model

import com.hold.domain.model.RecordType

data class LeaderboardState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val selectedRecords: RecordType = RecordType.PERSONAL,
    val data: LeaderboardModel? = null
)
