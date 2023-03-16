package com.kocoukot.holdgame.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val date: Long = 0,
    val result: Long = 0,
) : Parcelable {

    companion object {
        fun from(map: Map<String, Any>) = object {
            val date: Long by map
            val result: Long by map

            val data = GameResult(date, result)
        }.data
    }
}

