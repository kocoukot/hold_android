package com.hold.data.repository

import com.hold.domain.model.user.GameUser


interface GameRepository {


    suspend fun getGlobalResults(): List<GameUser>
}
