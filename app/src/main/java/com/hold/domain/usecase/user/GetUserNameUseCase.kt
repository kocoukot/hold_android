package com.hold.domain.usecase.user

import com.hold.data.repository.AccountRepository


class GetUserNameUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend fun getName() = accountRepository.getUser()
}