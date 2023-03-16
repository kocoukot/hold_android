package com.kocoukot.holdgame.domain.usecase.leaderboard

import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.repo.LeaderboardRepository


class GetGlobalResultsUseCase(
    private val leaderboardRepository: LeaderboardRepository,
) {

    suspend operator fun invoke(callBack: (List<GameGlobalUser>) -> Unit) {
        leaderboardRepository.getGlobalResults(callBack = callBack)
    }
}