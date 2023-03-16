package com.kocoukot.holdgame.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameGlobalUser(
    val id: String,
    val avatar: String?,
    var userName: String,
    val records: GameResult
) : Parcelable {

    companion object {
        fun from(map: Map<String, Any>) = object {
            val id: String by map
            val avatar: String? by map
            val userName: String by map
            val records = GameResult.from(map["records"] as Map<String, Any>)

            val data = GameGlobalUser(id, avatar, userName, records)
        }.data
    }
}