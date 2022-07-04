package com.hold.domain.usecase.leaderboard

import com.hold.data.repository.LeaderboardRepository

class GetUserLocalRecordUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {
    suspend operator fun invoke() = leaderboardRepository.getRecord()
}