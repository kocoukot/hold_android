package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.domain.repo.AccountRepository

class SaveDayPurchaseDateUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(date: Long?) = accountRepository.saveDayPurchaseDate(date)
}