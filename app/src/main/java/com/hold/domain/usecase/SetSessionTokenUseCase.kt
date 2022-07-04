package com.hold.domain.usecase

import com.hold.data.repository.AccountRepository


class SetSessionTokenUseCase(
    private val accountRepository: AccountRepository,
) {

    operator fun invoke(token: String) {
        accountRepository.sessionToken = token
    }
}