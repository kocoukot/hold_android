package com.kocoukot.holdgame.domain.model

data class BillingModel(
    val productId: String,
    val formattedPrice: String,
    val skuDetailsToken: String
)
