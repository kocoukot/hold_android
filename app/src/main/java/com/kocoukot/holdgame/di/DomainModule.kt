package com.kocoukot.holdgame.di

import com.kocoukot.holdgame.domain.usecase.SetSessionTokenUseCase
import com.kocoukot.holdgame.domain.usecase.leaderboard.GetGlobalResultsUseCase
import com.kocoukot.holdgame.domain.usecase.leaderboard.GetUserLocalRecordUseCase
import com.kocoukot.holdgame.domain.usecase.leaderboard.GetUserResultsUseCase
import com.kocoukot.holdgame.domain.usecase.user.*
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetUserResultsUseCase(get()) }

    factory { SaveNewResultUseCase(get()) }

    factory { GetGlobalResultsUseCase(get()) }

    factory { GetUserLocalRecordUseCase(get()) }

    factory { GetUserNameUseCase(get()) }

    factory { SaveUserNameUseCase(get()) }

    factory { SetSessionTokenUseCase(get()) }

    factory { GetLastResultUseCase(get()) }

    factory { SaveLastResultUseCase(get()) }


    factory { SaveDayPurchaseDateUseCase(get()) }

    factory { GetDayPurchaseUseCase(get()) }

    factory { GetGlobalTimeUseCase(get()) }

}



