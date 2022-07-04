package com.hold.data.network.model.request

import com.google.gson.annotations.SerializedName

class ResultRequest(
    @SerializedName("userId") private val userId: String,
    @SerializedName("date") private val date: Long,
    @SerializedName("value") private val value: Long,
)



