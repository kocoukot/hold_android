package com.hold.di

import com.google.android.libraries.places.api.Places
import com.hold.ui.button.ButtonViewModel
import com.hold.ui.endgame.ask.AskContinueViewModel
import com.hold.ui.leaderboard.LeaderboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val baseModule = module {

    single<KoinInjector> { GlobalKoinInjector(getKoin()) }

    single { Places.createClient(get()) }
}

val buttonModule = module {

    viewModel { ButtonViewModel(get()) }

    viewModel { LeaderboardViewModel(get(), get()) }

    viewModel { AskContinueViewModel(get()) }

}


val presentationModules = arrayOf(
    baseModule,
    buttonModule
)

