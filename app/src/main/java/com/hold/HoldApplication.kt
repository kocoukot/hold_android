package com.hold

import android.app.Application
import com.hold.di.*
import com.hold.domain.usecase.SetSessionTokenUseCase
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.*


class HoldApplication : Application() {

    private val setSessionTokenUseCase: SetSessionTokenUseCase by inject()

    override fun onCreate() {
        super.onCreate()

        instance = this

        startTimber()
        startKoin {
            androidLogger()
            androidContext(this@HoldApplication)
            modules(*presentationModules + networkModule + useCaseModule + repositoryModule + storageModule)
        }
        setSessionTokenUseCase.invoke(UUID.randomUUID().toString())
    }

    private fun startTimber() = Timber.plant(Timber.DebugTree())


//    private fun startAppsFlyerSDK() {
//
//        AppsFlyerLib.getInstance().apply {
//            init(BuildConfig.APPSFLYER_DEV_KEY, null, this@HeavyeApplication)
//            start(this@HeavyeApplication)
//            setDebugLog(true)
//        }
//    }
//
//    private fun startFireBaseNotification() {
//
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener(OnCompleteListener { task ->
//
//                if (!task.isSuccessful) {
//                    Timber.d("Fetching FCM registration token failed $task.exception")
//                    return@OnCompleteListener
//                }
//
//                val token = task.result
//                setFcmTokenUseCase(token)
//
//                Timber.d("FCM TOKEN $token")
//
//            })
//    }
//
//    private fun startFacebookSDK() {
//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)
//    }


    // Context
    companion object {
        lateinit var instance: HoldApplication
            private set
    }

}