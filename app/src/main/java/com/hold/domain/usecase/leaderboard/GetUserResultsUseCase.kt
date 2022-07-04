package com.hold.domain.usecase.leaderboard

import com.hold.data.repository.LeaderboardRepository

class GetUserResultsUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {
    suspend operator fun invoke() = leaderboardRepository.getUserResults()
}