package com.kocoukot.holdgame.data.repository


import com.kocoukot.holdgame.data.local.AccountStorage
import com.kocoukot.holdgame.data.network.model.request.ResultRequest
import com.kocoukot.holdgame.data.network.model.request.UpdateUserRequest
import com.kocoukot.holdgame.data.network.service.UserService
import com.kocoukot.holdgame.domain.model.user.GameResult

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