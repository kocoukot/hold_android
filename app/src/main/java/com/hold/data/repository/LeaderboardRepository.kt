package com.hold.data.repository

import com.hold.domain.model.user.GameGlobalUser
import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser


interface LeaderboardRepository {

    suspend fun getUserResults(): GameUser

    suspend fun getRecord(): GameResult?

    suspend fun getGlobalResults(): List<GameGlobalUser>
}
