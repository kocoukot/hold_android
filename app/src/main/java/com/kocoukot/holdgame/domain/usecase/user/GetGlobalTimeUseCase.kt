package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.data.repository.AccountRepository


class GetGlobalTimeUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend fun getGlobalTime() = accountRepository.getGlobalTime()
}