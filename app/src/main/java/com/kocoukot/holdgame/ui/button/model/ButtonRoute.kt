package com.kocoukot.holdgame.ui.button.model

import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.core_mvi.ComposeRoute

sealed class ButtonRoute : ComposeRoute {
    object ToLeaderboard : ButtonRoute()
    object ToProfile : ButtonRoute()
    object CloseApp : ButtonRoute()
    object ShowAd : ButtonRoute()
    data class LaunchBill(val product: ProductDetails) : ButtonRoute()
}
