package com.kocoukot.holdgame.data.repository


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kocoukot.holdgame.DateUtil.convertGlobalTime
import com.kocoukot.holdgame.data.local.AccountStorage
import com.kocoukot.holdgame.data.network.service.UserService
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.repo.AccountRepository
import timber.log.Timber
import java.time.ZoneId

class AccountRepositoryImpl(
    private val accountStorage: AccountStorage,
    private val userService: UserService,
) : AccountRepository {

    private val firestoreDb = Firebase.firestore

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
        accountStorage.apply {
            saveName(userName)
            accountStorage.getUserName()?.let { gameUser ->

                firestoreDb.collection("results")
                    .document(gameUser.id)
                    .set(gameUser.toGlobalUser())
                    .addOnSuccessListener { document ->
                        Timber.tag("hold_tag").d("document added")
                    }
                    .addOnFailureListener { exception ->
                        Timber.tag("hold_tag").w(exception, "Error getting documents: ")
                    }

            }
        }
        return isNew
    }

    override suspend fun setNewResult(result: GameResult) {
        accountStorage.apply {
            setNewResult(result)
            getUserName()?.let { gameUser ->
                val sendResult = gameUser.toGlobalUser()

                firestoreDb.collection("results")
                    .document(gameUser.id)
                    .set(sendResult)
                    .addOnSuccessListener { document ->
                        Timber.tag("hold_tag").d("document added")
                    }
                    .addOnFailureListener { exception ->
                        Timber.tag("hold_tag").w(exception, "Error getting documents: ")
                    }
            }
        }
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
