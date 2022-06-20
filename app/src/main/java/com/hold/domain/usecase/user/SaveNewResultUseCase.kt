package com.hold.domain.usecase.user

import com.hold.data.repository.AccountRepository
import com.hold.domain.model.user.GameResult

class SaveNewResultUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(record: GameResult) = accountRepository.setNewResult(record)
}