package com.kocoukot.holdgame.ui.button.model

import com.android.billingclient.api.ProductDetails

sealed class ButtonRoute {
    object ToLeaderboard : ButtonRoute()
    object ToProfile : ButtonRoute()
    object CloseApp : ButtonRoute()
    object ShowAd : ButtonRoute()
    data class LaunchBill(val product: ProductDetails) : ButtonRoute()
}
