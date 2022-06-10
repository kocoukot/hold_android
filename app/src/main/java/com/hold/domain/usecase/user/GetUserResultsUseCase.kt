package com.hold.domain.usecase.user

import com.hold.data.repository.AccountRepository

class GetUserResultsUseCase(
    private val accountRepository: AccountRepository,
) {

    operator fun invoke() = accountRepository.getUserResults()
}