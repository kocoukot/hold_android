package com.kocoukot.holdgame.data.repository


import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kocoukot.holdgame.data.local.AccountStorage
import com.kocoukot.holdgame.data.network.service.LeaderboardService
import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.domain.model.user.GameUser
import com.kocoukot.holdgame.domain.repo.LeaderboardRepository
import timber.log.Timber

class LeaderboardRepositoryImpl(
    private val accountStorage: AccountStorage,
    private val leaderboardService: LeaderboardService,
) : LeaderboardRepository {

    private val firestoreDb = Firebase.firestore


    override suspend fun getRecord(): GameResult? = accountStorage.getUserResults()
        ?.records
        ?.takeIf { it.isNotEmpty() }
        ?.maxWithOrNull(Comparator.comparingLong { it.result })


    override suspend fun getUserResults(): GameUser = accountStorage.getUserResults() ?: GameUser()

    override suspend fun getGlobalResults(callBack: (List<GameGlobalUser>) -> Unit) {
        val successListener = OnSuccessListener<QuerySnapshot> { results ->
            val resultsList = results.map { result ->
                GameGlobalUser.from(result.data)
            }
            Timber.tag("hold_tag").d("resultsList $resultsList")
            callBack.invoke(resultsList.sortedByDescending { it.records.result })
        }

        firestoreDb.collection("results")
            .get()
            .addOnSuccessListener(successListener)
            .addOnFailureListener { exception ->
                Timber.tag("hold_tag").d(exception, "get failed with ")
            }
    }
}
