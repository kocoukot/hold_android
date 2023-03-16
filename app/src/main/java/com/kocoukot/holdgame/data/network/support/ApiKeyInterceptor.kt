package com.kocoukot.holdgame.data.network.support

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
//            .addHeader(HEADER_API_KEY, BuildConfig.API_KEY)
            .build()
            .let(chain::proceed)
    }

    companion object {
        private const val HEADER_API_KEY = "X-API-Key"
    }
}
