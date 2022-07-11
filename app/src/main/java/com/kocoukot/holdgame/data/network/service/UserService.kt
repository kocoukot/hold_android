package com.kocoukot.holdgame.data.network.service

import com.kocoukot.holdgame.data.network.Endpoint
import com.kocoukot.holdgame.data.network.model.request.ResultRequest
import com.kocoukot.holdgame.data.network.model.request.UpdateUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST(Endpoint.User.INSERT_USER)
    suspend fun insertUser(@Body request: UpdateUserRequest): Boolean

    @POST(Endpoint.User.UPDATE_USER)
    suspend fun updateUser(@Body request: UpdateUserRequest): Boolean

    @POST(Endpoint.User.ADD_USER_RESULT)
    suspend fun addUserResult(@Body request: ResultRequest): Boolean
}