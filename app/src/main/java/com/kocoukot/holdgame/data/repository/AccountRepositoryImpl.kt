package com.kocoukot.holdgame.data.repository


import com.kocoukot.holdgame.DateUtil.convertGlobalTime
import com.kocoukot.holdgame.data.local.AccountStorage
import com.kocoukot.holdgame.data.network.model.request.ResultRequest
import com.kocoukot.holdgame.data.network.model.request.UpdateUserRequest
import com.kocoukot.holdgame.data.network.service.UserService
import com.kocoukot.holdgame.domain.repo.AccountRepository
import com.kocoukot.holdgame.model.user.GameResult
import timber.log.Timber
import java.time.ZoneId

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

    override suspend fun saveLastResult(timer: Long?) {
        accountStorage.lastSavedTimer = timer
    }

    override suspend fun getLastResult(): Long? = accountStorage.lastSavedTimer


    override suspend fun getDayPurchaseDate() = accountStorage.purchaseDayDate

    override suspend fun saveDayPurchaseDate(date: Long?) {
        accountStorage.purchaseDayDate = date
    }

    override suspend fun getGlobalTime(): Long? {
        val response = userService.getGlobalTime(ZoneId.systemDefault().toString())
        val converted = convertGlobalTime(response.globalTime)
        Timber.i("ZoneId ${response.globalTime} isoDateFormatter $converted")
        return converted
    }
}
