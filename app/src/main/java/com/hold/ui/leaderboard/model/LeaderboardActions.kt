package com.hold.ui.leaderboard.model

import com.hold.domain.model.RecordType

sealed class LeaderboardActions {
    data class ClickOnRecordChange(val record: RecordType) : LeaderboardActions()
    object ClickOnBack : LeaderboardActions()
}
