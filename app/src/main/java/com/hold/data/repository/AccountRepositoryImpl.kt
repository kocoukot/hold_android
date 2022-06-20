package com.hold.data.repository


import com.hold.data.local.AccountStorage
import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser

class AccountRepositoryImpl(private val accountStorage: AccountStorage) : AccountRepository {

    override var sessionToken: String = ""
        get() = accountStorage.sessionToken ?: ""
        set(value) {
            field = value
            accountStorage.sessionToken = value
        }

    override suspend fun setNewResult(result: GameResult) {
        accountStorage.setNewResult(result)
    }

    override suspend fun getUserResults(): GameUser = accountStorage.getUserResults() ?: GameUser()

    override suspend fun getRecord(): GameResult? = accountStorage.getUserResults()
        ?.records
        ?.takeIf { it.isNotEmpty() }
        ?.maxWithOrNull(Comparator.comparingLong { it.result })

    override suspend fun saveUserName(userName: String) = accountStorage.saveName(userName)

    override suspend fun getUserName() = accountStorage.getUserName()


//    override fun getUserAccountSelector() = accountStorage.getAccountSelector()
//
//    override fun setUserAccountType(accountType: AccountType) {
//        accountStorage.setUserAccountType(accountType)
//    }


//    override fun loginUser(sessionToken: String, user: User) {
//        accountStorage.apply {
//            accountStorage.isProfileDirty = true
//            loginUser(sessionToken, user)
//        }
//    }

    //
//    override var isUserLoggedIn: Boolean = false
//        get() = accountStorage.isUserLoggedIn
//        set(value) {
//            field = value
//            accountStorage.isUserLoggedIn = value
//        }
//
//    override fun updateUser(user: User) = accountStorage.updateUser(user)
//
//    override fun updateUserAvatar(userAvatar: User.UserAvatarInfo) =
//        accountStorage.updateUserAvatar(userAvatar)
//
//    override fun getUserAccountSelector() = accountStorage.getAccountSelector()
//    override fun getUserAccountType() = accountStorage.getAccountType()
//
//    override fun getUserShort(): UserShort? = accountStorage.getUserShort()


//    override var isUserLoggedIn: Boolean = false
//        get() = accountStorage.isUserLoggedIn
//        set(value) {
//            field = value
//            accountStorage.isUserLoggedIn = value
//        }

    //    override fun logoutUser() {
//        accountStorage.apply {
//            accountStorage.clearProfile()
//            isUserLoggedIn = false
//            sessionToken = null
//            passCode = ""
//        }
//        SessionVariablesStorage.isPassCodeCheckRequired = false
//    }
//
//

//

//
//    override fun updateProfile(profile: Profile) = accountStorage.updateProfile(profile)
//
//    override fun <T : Profile> getProfile(type: KClass<T>): Maybe<T> =
//        accountStorage.getProfile(type)
}
