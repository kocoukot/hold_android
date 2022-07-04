package com.hold.data.network.model.response.leaderboard

import com.google.gson.annotations.SerializedName

class UserInfoResponse(
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String?,
    @SerializedName("avatar") val avatar: String?,
)