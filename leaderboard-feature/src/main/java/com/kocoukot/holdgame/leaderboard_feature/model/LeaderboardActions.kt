package com.kocoukot.holdgame.leaderboard_feature.model


sealed class LeaderboardActions {
    data class ClickOnRecordChange(val record: RecordType) : LeaderboardActions()
    object ClickOnBack : LeaderboardActions()
}
