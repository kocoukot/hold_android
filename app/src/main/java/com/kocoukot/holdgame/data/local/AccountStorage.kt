package com.kocoukot.holdgame.data.local

import android.accounts.Account
import android.accounts.AccountManager
//import com.hold.domain.model.User
import com.google.gson.Gson
import com.kocoukot.holdgame.model.user.GameResult
import com.kocoukot.holdgame.model.user.GameUser


class AccountStorage(
    private val accountManager: AccountManager,
    private val gson: Gson,
) {
    var sessionToken: String?
        get() = accountManager.peekAuthToken(getOrCreateAccount(), SESSION_TOKEN)
        set(value) {
            accountManager.setAuthToken(getOrCreateAccount(), SESSION_TOKEN, value)
        }


    var lastSavedTimer: Long?
        get() {
            return getAccount()
                ?.let { account ->
                    try {
                        accountManager.getUserData(account, SAVED_TIMER)?.toLong()
                    } catch (e: Exception) {
                        null
                    }
                }
        }
        set(value) {
            accountManager.setUserData(getOrCreateAccount(), SAVED_TIMER, value.toString())
        }

    var purchaseDayDate: Long?
        get() {
            return getAccount()
                ?.let { account ->
                    try {
                        accountManager.getUserData(account, PURCHASE_FOR_DAY)?.toLong()
                    } catch (e: Exception) {
                        null
                    }
                }
        }
        set(value) {
            accountManager.setUserData(getOrCreateAccount(), PURCHASE_FOR_DAY, value.toString())
        }

    fun setNewResult(result: GameResult) = setNewResult(getOrCreateAccount(), result)


    private fun setNewResult(currentAccount: Account, result: GameResult) {

        var user = accountManager.getUserData(currentAccount, USER)
            ?.takeIf { it.isNotEmpty() }
            ?.let { gson.fromJson(it, GameUser::class.java) } ?: GameUser()

        val list = user.records

        list.let { results ->
            results.add(result)
            results.sortByDescending { result ->
                result.result
            }
        }
        val slicedList = if (list.size >= 15) list.slice(0..14) else list

        user = user.copy(records = slicedList.toMutableList())
        println("new record storage $user")
        return accountManager.setUserData(currentAccount, USER, gson.toJson(user))

    }

    private fun updateUser(currentAccount: Account, user: GameUser) =
        accountManager.setUserData(currentAccount, USER, gson.toJson(user))


    private fun getUser(): GameUser? =
        getAccount()
            ?.let { account ->
                accountManager.getUserData(account, USER)
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { gson.fromJson(it, GameUser::class.java) }
            }

    fun getUserResults() = getUser()

    private fun getOrCreateAccount(): Account {
        return getAccount()
            ?: run {
                Account(ACCOUNT_NAME, ACCOUNT_TYPE)
                    .also { accountManager.addAccountExplicitly(it, ACCOUNT_PASSWORD, null) }
            }
    }

    fun saveName(userName: String) = saveName(getOrCreateAccount(), userName)

    private fun saveName(currentAccount: Account, userName: String) {
        var user = accountManager.getUserData(currentAccount, USER)
            ?.takeIf { it.isNotEmpty() }
            ?.let { gson.fromJson(it, GameUser::class.java) } ?: GameUser()
        user = user.copy(userName = userName)
        return accountManager.setUserData(currentAccount, USER, gson.toJson(user))
    }

    fun updateUserId(userId: String) = updateUserId(getOrCreateAccount(), userId)

    private fun updateUserId(currentAccount: Account, userId: String) {
        var user = accountManager.getUserData(currentAccount, USER)
            ?.takeIf { it.isNotEmpty() }
            ?.let { gson.fromJson(it, GameUser::class.java) } ?: GameUser()
        user = user.copy(id = userId)
        return accountManager.setUserData(currentAccount, USER, gson.toJson(user))
    }

    fun getUserName(): GameUser? = accountManager.getUserData(getOrCreateAccount(), USER)
        ?.let { gson.fromJson(it, GameUser::class.java) }


    private fun getAccount(): Account? =
        accountManager.getAccountsByType(ACCOUNT_TYPE).singleOrNull()

    companion object {
        private const val ACCOUNT_NAME = "Hold"
        private const val ACCOUNT_TYPE = "com.kocoukot.holdgame.holdgame"
        private const val ACCOUNT_PASSWORD = "Password"

        private const val SESSION_TOKEN = "$ACCOUNT_TYPE.session.token"
        private const val SAVED_TIMER = "$ACCOUNT_TYPE.user.timer"
        private const val PURCHASE_FOR_DAY = "$ACCOUNT_TYPE.user.dayPurchase"

        private const val USER = "$ACCOUNT_TYPE.user"
    }

}