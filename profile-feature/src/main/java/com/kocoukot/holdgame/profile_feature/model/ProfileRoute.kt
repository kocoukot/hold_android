package com.kocoukot.holdgame.profile_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeRoute
import com.kocoukot.holdgame.core_mvi.ComposeRouteNavigation

sealed class ProfileRoute : ComposeRoute {
    object OnBack : ProfileRoute(), ComposeRouteNavigation
}
