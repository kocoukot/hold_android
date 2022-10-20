package com.kocoukot.holdgame.leaderboard_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeState
import com.kocoukot.holdgame.leaderboard_feature.domain.RecordType


data class LeaderboardState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val selectedRecords: RecordType = RecordType.PERSONAL,
    val data: LeaderboardModel? = null
) : ComposeState