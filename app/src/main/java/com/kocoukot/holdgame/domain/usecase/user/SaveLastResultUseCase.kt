package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.data.repository.AccountRepository

class SaveLastResultUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(timer: Long?) = accountRepository.saveLastResult(timer)
}