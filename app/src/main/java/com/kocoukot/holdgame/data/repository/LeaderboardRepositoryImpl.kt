package com.kocoukot.holdgame.data.repository


import com.kocoukot.holdgame.data.local.AccountStorage
import com.kocoukot.holdgame.data.network.service.LeaderboardService
import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.model.user.GameUser
import com.kocoukot.holdgame.utils.UNNAMED_USER

class LeaderboardRepositoryImpl(
    private val accountStorage: AccountStorage,
    private val leaderboardService: LeaderboardService,
) : LeaderboardRepository {

    override suspend fun getRecord(): GameResult? = accountStorage.getUserResults()
        ?.records
        ?.takeIf { it.isNotEmpty() }
        ?.maxWithOrNull(Comparator.comparingLong { it.result })

    override suspend fun getUserResults(): GameUser = accountStorage.getUserResults() ?: GameUser()

    override suspend fun getGlobalResults(): List<GameGlobalUser> {
        val response = leaderboardService.getGlobalResults()
        return response.leaderboard.map { user ->
            GameGlobalUser(
                id = user.userInfo.userId,
                avatar = user.userInfo.avatar,
                userName = user.userInfo.userName ?: UNNAMED_USER,
                records = GameResult(
                    date = user.result.date,
                    result = user.result.value,
                ),
            )
        }
    }
}
