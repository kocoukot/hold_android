package com.kocoukot.holdgame.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameUser(
    val id: String = "",
    val avatar: String? = "",
    var userName: String = "",
    val records: MutableList<GameResult> = mutableListOf(),
) : Parcelable