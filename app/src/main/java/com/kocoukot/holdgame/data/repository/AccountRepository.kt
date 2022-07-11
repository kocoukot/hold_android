package com.kocoukot.holdgame.data.repository

import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.model.user.GameUser


interface AccountRepository {

    var sessionToken: String

    suspend fun saveUserName(userName: String, isNew: Boolean): Boolean

    suspend fun setNewResult(result: GameResult)

    suspend fun getUser(): GameUser?

}
