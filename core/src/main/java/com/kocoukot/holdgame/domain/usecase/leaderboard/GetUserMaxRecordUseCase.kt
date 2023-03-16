package com.kocoukot.holdgame.domain.usecase.leaderboard

import com.kocoukot.holdgame.domain.repo.LeaderboardRepository


class GetUserMaxRecordUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {
    suspend operator fun invoke() = leaderboardRepository.getRecord()
}