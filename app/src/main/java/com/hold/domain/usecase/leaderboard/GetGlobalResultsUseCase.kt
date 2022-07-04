package com.hold.domain.usecase.leaderboard

import com.hold.data.repository.LeaderboardRepository


class GetGlobalResultsUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {

    suspend operator fun invoke() = leaderboardRepository.getGlobalResults()
}