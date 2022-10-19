package com.kocoukot.holdgame.leaderboard_feature.model



data class LeaderboardState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val selectedRecords: RecordType = RecordType.PERSONAL,
    val data: LeaderboardModel? = null
)