package com.kocoukot.holdgame.data.network.service

import com.kocoukot.holdgame.data.network.Endpoint
import com.kocoukot.holdgame.data.network.model.response.leaderboard.GlobalResultsResponse
import retrofit2.http.GET

interface LeaderboardService {

    @GET(Endpoint.Leaderboard.GET_GLOBAL_RESULTS)
    suspend fun getGlobalResults(): GlobalResultsResponse

//    @GET(Endpoint.Leaderboard.GET_USER_RESULTS)
//    suspend fun getUserResults(): List<GameResultResponse>
}