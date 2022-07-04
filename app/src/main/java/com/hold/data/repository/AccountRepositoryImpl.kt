package com.hold.data.repository


import com.hold.data.local.AccountStorage
import com.hold.data.network.model.request.ResultRequest
import com.hold.data.network.model.request.UpdateUserRequest
import com.hold.data.network.service.UserService
import com.hold.domain.model.user.GameResult

class AccountRepositoryImpl(
    private val accountStorage: AccountStorage,
    private val userService: UserService,
) : AccountRepository {

    override var sessionToken: String = ""
        get() = accountStorage.sessionToken ?: ""
        set(value) {
            field = value
            if (accountStorage.sessionToken.isNullOrEmpty()) {
                accountStorage.sessionToken = value
                accountStorage.updateUserId(value)
            }
        }

    override suspend fun saveUserName(userName: String, isNew: Boolean): Boolean {
        val request = UpdateUserRequest(
            id = sessionToken,
            name = userName,
            avatar = "",
        )
        return if (isNew) {
            userService.insertUser(request)
        } else {
            userService.updateUser(request)
        }.also {
            accountStorage.saveName(userName)
        }
    }

    override suspend fun setNewResult(result: GameResult) {
        userService.addUserResult(
            ResultRequest(
                userId = sessionToken,
                date = result.date,
                value = result.result,
            )
        )
        accountStorage.setNewResult(result)
    }

    override suspend fun getUser() = accountStorage.getUserName()
}
