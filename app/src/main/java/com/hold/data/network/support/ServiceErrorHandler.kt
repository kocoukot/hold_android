package com.hold.data.network.support

import com.google.gson.Gson
import com.hold.data.mapper.ErrorMapper
import com.hold.data.network.model.response.ErrorResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.HttpException

class ServiceErrorHandler(private val gson: Gson) {
    private val errorMapper = ErrorMapper()

    fun <T> onSingleError(throwable: Throwable) = Single.error<T>(handleError(throwable))

    fun onCompletableError(throwable: Throwable) = Completable.error(handleError(throwable))

    private fun handleError(throwable: Throwable): Throwable {
        return if (throwable is HttpException) {
            throwable.response()
                .let { response ->
                    kotlin
                        .runCatching {
                            response?.errorBody()
                                ?.string()
                                ?.let { gson.fromJson(it, ErrorResponse::class.java) }
                                ?.let(errorMapper::mapData)
                        }
                        .getOrNull()
                        ?: throwable
                }
        } else {
            throwable
        }
    }
}
