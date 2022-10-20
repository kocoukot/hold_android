package com.kocoukot.holdgame.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameGlobalUser(
    val id: String,
    val avatar: String?,
    var userName: String,
    val records: GameResult
) : Parcelable