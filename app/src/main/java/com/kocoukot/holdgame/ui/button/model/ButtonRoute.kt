package com.kocoukot.holdgame.ui.button.model

import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.core_mvi.ComposeRoute
import com.kocoukot.holdgame.core_mvi.ComposeRouteFinishApp
import com.kocoukot.holdgame.core_mvi.ComposeRouteNavigation

sealed class ButtonRoute : ComposeRoute {
    object ToLeaderboard : ButtonRoute(), ComposeRouteNavigation {
        override val destination: Int = R.id.action_buttonFragment_to_leaderboardFragment
    }

    object ToProfile : ButtonRoute(), ComposeRouteNavigation {
        override val destination: Int = R.id.action_buttonFragment_to_profileFragment
    }

    object CloseApp : ButtonRoute(), ComposeRouteFinishApp

    object ShowAd : ButtonRoute()
    data class LaunchBill(val product: ProductDetails) : ButtonRoute()
}
