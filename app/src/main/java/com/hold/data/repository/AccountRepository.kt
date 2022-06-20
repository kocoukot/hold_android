package com.hold.data.repository

import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser


interface AccountRepository {

    var sessionToken: String

    suspend fun setNewResult(result: GameResult)

    suspend fun getUserResults(): GameUser?

    suspend fun getRecord(): GameResult?

    suspend fun saveUserName(userName: String)

    suspend fun getUserName(): GameUser?

}
