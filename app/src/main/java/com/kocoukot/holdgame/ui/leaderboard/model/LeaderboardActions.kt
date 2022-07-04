package com.kocoukot.holdgame.ui.leaderboard.model

import com.kocoukot.holdgame.domain.model.RecordType

sealed class LeaderboardActions {
    data class ClickOnRecordChange(val record: RecordType) : LeaderboardActions()
    object ClickOnBack : LeaderboardActions()
}
