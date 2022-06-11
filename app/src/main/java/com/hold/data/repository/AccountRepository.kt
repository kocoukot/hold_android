package com.hold.data.repository

import com.hold.domain.model.user.GameRecord
import com.hold.domain.model.user.GameUser


interface AccountRepository {

    var sessionToken: String

    fun setNewResult(result: GameRecord)

    suspend fun getUserResults(): GameUser?
}
