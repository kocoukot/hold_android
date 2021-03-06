package com.kocoukot.holdgame.di

import com.kocoukot.holdgame.ui.button.ButtonViewModel
import com.kocoukot.holdgame.ui.leaderboard.LeaderboardViewModel
import com.kocoukot.holdgame.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val baseModule = module {

    single<KoinInjector> { GlobalKoinInjector(getKoin()) }

}

val buttonModule = module {

    viewModel { ButtonViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    viewModel { ProfileViewModel(get(), get()) }

    viewModel { LeaderboardViewModel(get(), get(), get()) }

}


val presentationModules = arrayOf(
    baseModule,
    buttonModule
)

