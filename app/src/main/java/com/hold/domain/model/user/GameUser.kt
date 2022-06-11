package com.hold.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameUser(
    val id: String? = "",
    val avatar: String? = "",
    val userName: String = "",
    val records: List<GameRecord> = mutableListOf(),
) : Parcelable