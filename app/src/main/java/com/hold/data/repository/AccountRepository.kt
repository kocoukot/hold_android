package com.hold.data.repository

import com.hold.domain.model.user.GameRecord
import com.hold.domain.model.user.GameUser


interface AccountRepository {

    var sessionToken: String

    fun setNewResult(result: GameRecord)

    fun getUserResults(): GameUser?


//    fun getUserAccountSelector(): AccountType?
//
//    fun setUserAccountType(accountType: AccountType)

//    fun loginUser(sessionToken: String, user: User)

//    var isUserLoggedIn: Boolean
//
//    fun logoutUser()
//
//fun updateUser(user: User)
//
//    fun updateUserAvatar(userAvatar: User.UserAvatarInfo)
//
//    fun getUserAccountType(): AccountType?
//
//    fun getUserAccountSelector(): AccountType?
//
//    fun getUserShort(): UserShort?

//    fun updateProfile(profile: Profile)

//    fun <T : Profile> getProfile(type: KClass<T>): Maybe<T>
}
