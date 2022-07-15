package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.data.repository.AccountRepository

class SaveDayPurchaseDateUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(date: Long) = accountRepository.saveDayPurchaseDate(date)
}