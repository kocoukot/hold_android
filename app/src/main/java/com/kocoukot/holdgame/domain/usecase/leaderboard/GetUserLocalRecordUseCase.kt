package com.kocoukot.holdgame.domain.usecase.leaderboard

import com.kocoukot.holdgame.data.repository.LeaderboardRepository

class GetUserLocalRecordUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {
    suspend operator fun invoke() = leaderboardRepository.getRecord()
}