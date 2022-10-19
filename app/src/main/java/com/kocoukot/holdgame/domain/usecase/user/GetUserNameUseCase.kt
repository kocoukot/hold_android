package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.data.repository.AccountRepository


class GetUserNameUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend fun getName() = accountRepository.getUser()
}