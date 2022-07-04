package com.hold.di

import com.hold.domain.usecase.SetSessionTokenUseCase
import com.hold.domain.usecase.leaderboard.GetGlobalResultsUseCase
import com.hold.domain.usecase.leaderboard.GetUserLocalRecordUseCase
import com.hold.domain.usecase.leaderboard.GetUserResultsUseCase
import com.hold.domain.usecase.user.GetUserNameUseCase
import com.hold.domain.usecase.user.SaveNewResultUseCase
import com.hold.domain.usecase.user.SaveUserNameUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetUserResultsUseCase(get()) }

    factory { SaveNewResultUseCase(get()) }

    factory { GetGlobalResultsUseCase(get()) }

    factory { GetUserLocalRecordUseCase(get()) }

    factory { GetUserNameUseCase(get()) }

    factory { SaveUserNameUseCase(get()) }

    factory { SetSessionTokenUseCase(get()) }
}



