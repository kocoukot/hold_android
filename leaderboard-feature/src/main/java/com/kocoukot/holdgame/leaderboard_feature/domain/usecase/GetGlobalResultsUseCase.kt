package com.kocoukot.holdgame.leaderboard_feature.domain.usecase

import com.kocoukot.holdgame.leaderboard_feature.domain.LeaderboardRepository


class GetGlobalResultsUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {

    suspend operator fun invoke() = leaderboardRepository.getGlobalResults()
}