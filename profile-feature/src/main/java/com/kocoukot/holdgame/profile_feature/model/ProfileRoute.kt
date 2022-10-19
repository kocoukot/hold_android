package com.kocoukot.holdgame.profile_feature.model

import com.kocoukot.holdgame.core_mvi.ComposeRoute

sealed class ProfileRoute : ComposeRoute {
    object OnBack : ProfileRoute()
}
