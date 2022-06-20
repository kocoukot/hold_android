package com.hold.data.local

import android.accounts.Account
import android.accounts.AccountManager
//import com.hold.domain.model.User
import com.google.gson.Gson
import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser


class AccountStorage(
    private val accountManager: AccountManager,
    private val gson: Gson,
) {
    var sessionToken: String?
        get() = accountManager.peekAuthToken(getOrCreateAccount(), SESSION_TOKEN)
        set(value) {
            accountManager.setAuthToken(getOrCreateAccount(), SESSION_TOKEN, value)
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

    private fun getAccount(): Account? =
        accountManager.getAccountsByType(ACCOUNT_TYPE).singleOrNull()

    companion object {
        private const val ACCOUNT_NAME = "Hold"
        private const val ACCOUNT_TYPE = "com.hold.hold"
        private const val ACCOUNT_PASSWORD = "Password"

        private const val SESSION_TOKEN = "$ACCOUNT_TYPE.session.token"
        private const val PASS_CODE = "$ACCOUNT_TYPE.pass.code"

        private const val USER = "$ACCOUNT_TYPE.user"
        private const val PROFILE = "$ACCOUNT_TYPE.user.profile"
        private const val IS_USER_LOGGED_IN = "$ACCOUNT_TYPE.user.isLoggedIn"
    }

}