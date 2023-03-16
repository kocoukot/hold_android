package com.kocoukot.holdgame.data.network.service

import com.kocoukot.holdgame.data.network.model.response.leaderboard.GlobalTimeResponseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
//
//    @POST(Endpoint.User.INSERT_USER)
//    suspend fun insertUser(@Body request: UpdateUserRequest): Boolean
//
//    @POST(Endpoint.User.UPDATE_USER)
//    suspend fun updateUser(@Body request: UpdateUserRequest): Boolean
//
//    @POST(Endpoint.User.ADD_USER_RESULT)
//    suspend fun addUserResult(@Body request: ResultRequest): Boolean

    //    https://timeapi.io/api/Time/current/zone?timeZone=Asia/Tashkent
    @GET("https://timeapi.io/api/Time/current/zone?")
    suspend fun getGlobalTime(
        @Query("timeZone") zone: String
    ): GlobalTimeResponseResponse
}