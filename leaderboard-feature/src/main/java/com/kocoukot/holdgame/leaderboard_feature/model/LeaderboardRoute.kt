package com.kocoukot.holdgame.leaderboard_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeRoute
import com.kocoukot.holdgame.core_mvi.ComposeRouteNavigation

sealed class LeaderboardRoute : ComposeRoute {
    object OnBack : LeaderboardRoute(), ComposeRouteNavigation
}
