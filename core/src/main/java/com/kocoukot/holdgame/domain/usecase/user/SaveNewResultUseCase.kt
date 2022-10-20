package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.domain.repo.AccountRepository
import com.kocoukot.holdgame.model.user.GameResult

class SaveNewResultUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(record: GameResult) = accountRepository.setNewResult(record)
}