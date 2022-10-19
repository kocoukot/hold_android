package com.kocoukot.holdgame.data.network.model.response.leaderboard

import com.google.gson.annotations.SerializedName

class GlobalTimeResponseResponse(
    @SerializedName("dateTime") val globalTime: String,
)