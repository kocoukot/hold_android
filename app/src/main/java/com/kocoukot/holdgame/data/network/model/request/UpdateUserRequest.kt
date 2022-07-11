package com.kocoukot.holdgame.data.network.model.request

import com.google.gson.annotations.SerializedName

class UpdateUserRequest(
    @SerializedName("id") private val id: String,
    @SerializedName("name") private val name: String,
    @SerializedName("avatar") private val avatar: String,
)



