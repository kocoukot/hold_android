package com.hold.data.repository

import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser


interface AccountRepository {

    var sessionToken: String

    suspend fun saveUserName(userName: String, isNew: Boolean): Boolean

    suspend fun setNewResult(result: GameResult)

    suspend fun getUser(): GameUser?

}
