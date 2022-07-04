package com.kocoukot.holdgame.data.network.model.response

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String?,
)