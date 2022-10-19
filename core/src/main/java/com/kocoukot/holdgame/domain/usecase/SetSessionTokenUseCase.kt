package com.kocoukot.holdgame.domain.usecase

import com.kocoukot.holdgame.domain.repo.AccountRepository


class SetSessionTokenUseCase(
    private val accountRepository: AccountRepository,
) {

    operator fun invoke(token: String) {
        accountRepository.sessionToken = token
    }
}