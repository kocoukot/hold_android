package com.hold.di


import com.google.gson.GsonBuilder
import com.hold.data.ext.RetrofitConverterFactory
import com.hold.data.network.support.ApiKeyInterceptor
import com.hold.data.network.support.ServiceErrorHandler
import com.hold.data.network.support.SessionIdInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.ParametersHolder
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
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(SessionIdInterceptor(get()))
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
//            .baseUrl("${BuildConfig.API_GATEWAY}/$path/")
            .addConverterFactory(RetrofitConverterFactory(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }


    single { ServiceErrorHandler(get()) }


//    single {
//        get<Retrofit> { parametersOf(Path.EMAIL) }
//            .create(EmailService::class.java)
//    }
//
//    single {
//        get<Retrofit> { parametersOf(Path.AUTH) }
//            .create(AuthService::class.java)
//    }
//
//    single {
//        get<Retrofit> { parametersOf(Path.PROFILE) }
//            .create(ProfileService::class.java)
//}
//
//    single {
//        get<Retrofit> { parametersOf(Path.EMERGENCY_CONTACTS) }
//            .create(BuyerService::class.java)
//    }
//
//    single {
//        get<Retrofit> { parametersOf(Path.HOME) }
//            .create(SellerService::class.java)
//    }
//
//    single {
//        get<Retrofit> { parametersOf(Path.STORE) }
//            .create(PublicService::class.java)
//    }
//
//    single {
//        get<Retrofit> { parametersOf(Path.RESERVATION) }
//            .create(PublicService::class.java)
//    }
//
//    single {
//        get<Retrofit>(GoogleRetrofit)
//            .create(GoogleOAuthService::class.java)
//    }

}