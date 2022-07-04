package com.hold.domain.usecase.user

import com.hold.data.repository.AccountRepository


class SaveUserNameUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend fun saveName(userName: String, isNew: Boolean) =
        accountRepository.saveUserName(userName, isNew)
}