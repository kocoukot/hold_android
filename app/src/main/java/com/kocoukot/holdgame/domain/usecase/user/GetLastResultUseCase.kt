package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.data.repository.AccountRepository

class GetLastResultUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke() = accountRepository.getLastResult()
}