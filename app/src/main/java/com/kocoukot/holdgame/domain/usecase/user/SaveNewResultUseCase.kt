package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.data.repository.AccountRepository
import com.kocoukot.holdgame.domain.model.user.GameResult

class SaveNewResultUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(record: GameResult) = accountRepository.setNewResult(record)
}