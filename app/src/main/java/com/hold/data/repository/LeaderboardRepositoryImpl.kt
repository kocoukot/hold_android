package com.hold.data.repository


import com.hold.data.local.AccountStorage
import com.hold.data.network.service.LeaderboardService
import com.hold.domain.model.user.GameGlobalUser
import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser
import com.hold.utils.UNNAMED_USER

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
