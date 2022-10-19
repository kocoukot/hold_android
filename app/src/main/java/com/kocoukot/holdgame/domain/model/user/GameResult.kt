package com.kocoukot.holdgame.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val date: Long = 0,
    val result: Long = 0,
) : Parcelable

