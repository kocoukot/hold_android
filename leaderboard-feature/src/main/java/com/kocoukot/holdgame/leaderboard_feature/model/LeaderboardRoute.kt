package com.kocoukot.holdgame.leaderboard_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeRoute

sealed class LeaderboardRoute : ComposeRoute {
    object OnBack : LeaderboardRoute()
}
