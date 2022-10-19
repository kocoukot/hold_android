package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.domain.repo.AccountRepository

class GetLastResultUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke() = accountRepository.getLastResult()
}