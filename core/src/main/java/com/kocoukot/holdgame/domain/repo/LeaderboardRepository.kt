package com.kocoukot.holdgame.domain.repo

import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.model.user.GameUser


interface LeaderboardRepository {

    suspend fun getUserResults(): GameUser

    suspend fun getRecord(): GameResult?

    suspend fun getGlobalResults(callBack: (List<GameGlobalUser>) -> Unit)
}
