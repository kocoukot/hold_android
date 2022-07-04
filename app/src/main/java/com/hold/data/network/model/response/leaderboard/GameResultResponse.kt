package com.hold.data.network.model.response.leaderboard

import com.google.gson.annotations.SerializedName

class GameResultResponse(
    @SerializedName("date") val date: Long,
    @SerializedName("value") val value: Long,
)