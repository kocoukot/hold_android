package com.hold.domain.usecase.user

import com.hold.data.repository.AccountRepository
import com.hold.domain.model.user.GameRecord

class SetNewResultUseCase(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(record: GameRecord) = accountRepository.setNewResult(record)
}