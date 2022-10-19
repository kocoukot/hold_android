package com.kocoukot.holdgame.domain.usecase.user

import com.kocoukot.holdgame.domain.repo.AccountRepository


class SaveUserNameUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend fun saveName(userName: String, isNew: Boolean) =
        accountRepository.saveUserName(userName, isNew)
}