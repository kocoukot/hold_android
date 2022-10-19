package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.domain.repo.AccountRepository


class GetGlobalTimeUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend fun getGlobalTime() = accountRepository.getGlobalTime()
}