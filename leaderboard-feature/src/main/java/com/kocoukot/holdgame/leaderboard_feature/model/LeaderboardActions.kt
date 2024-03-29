package com.kocoukot.holdgame.leaderboard_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeActions
import com.kocoukot.holdgame.domain.model.RecordType


sealed class LeaderboardActions : ComposeActions {
    data class ClickOnRecordChange(val record: RecordType) : LeaderboardActions()
    object ClickOnBack : LeaderboardActions()
}
