package com.kocoukot.holdgame.leaderboard_feature.domain.usecase

import com.kocoukot.holdgame.leaderboard_feature.domain.LeaderboardRepository


class GetUserResultsUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {
    suspend operator fun invoke() = leaderboardRepository.getUserResults()
}