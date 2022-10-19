package com.kocoukot.holdgame.ui.leaderboard.model

import com.kocoukot.holdgame.domain.model.RecordType

data class LeaderboardState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val selectedRecords: RecordType = RecordType.PERSONAL,
    val data: LeaderboardModel? = null
)
