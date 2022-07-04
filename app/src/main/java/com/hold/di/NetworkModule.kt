package com.hold.di


import com.google.gson.GsonBuilder
import com.hold.BuildConfig
import com.hold.data.ext.RetrofitConverterFactory
import com.hold.data.network.Path
import com.hold.data.network.service.LeaderboardService
import com.hold.data.network.service.UserService
import com.hold.data.network.support.ServiceErrorHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

private const val API_CONNECT_TIMEOUT = 15L
private const val API_READ_WRITE_TIMEOUT = 30L

val networkModule = module {

    single {
        GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    factory { parameters: ParametersHolder ->
        val path = parameters.getOrNull<String>()
        Retrofit.Builder()
            .client(get())
            .baseUrl("${BuildConfig.API_GATEWAY}/$path/")
            .addConverterFactory(RetrofitConverterFactory(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }


    single { ServiceErrorHandler(get()) }


    single {
        get<Retrofit> { parametersOf(Path.USER) }
            .create(UserService::class.java)
    }

    single {
        get<Retrofit> { parametersOf(Path.USER) }
            .create(LeaderboardService::class.java)
    }

}