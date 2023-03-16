package com.kocoukot.holdgame.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameUser(
    val id: String = "",
    val avatar: String? = "",
    var userName: String = "",
    val records: MutableList<GameResult> = mutableListOf(),
) : Parcelable {

    fun toGlobalUser() = GameGlobalUser(
        id = id,
        avatar = avatar,
        userName = userName,
        records = records.maxBy { it.result }
            .let { maxResult -> GameResult(date = maxResult.date, result = maxResult.result) }
    )

    companion object {
        fun from(map: Map<String, Any>) = object {
            val id: String by map
            val avatar: String? by map
            val userName: String by map
            val records: MutableList<GameResult> by map

            val data = GameUser(id, avatar, userName, records)
        }.data
    }
}