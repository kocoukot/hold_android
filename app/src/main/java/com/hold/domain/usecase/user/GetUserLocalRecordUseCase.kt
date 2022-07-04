package com.hold.domain.usecase.user

import com.hold.data.repository.AccountRepository

class GetUserLocalRecordUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke() = accountRepository.getRecord()
}