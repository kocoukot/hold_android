package com.kocoukot.holdgame.leaderboard_feature.domain

import com.kocoukot.holdgame.model.user.GameGlobalUser
import com.kocoukot.holdgame.model.user.GameResult
import com.kocoukot.holdgame.model.user.GameUser


interface LeaderboardRepository {

    suspend fun getUserResults(): GameUser

    suspend fun getRecord(): GameResult?

    suspend fun getGlobalResults(): List<GameGlobalUser>
}
