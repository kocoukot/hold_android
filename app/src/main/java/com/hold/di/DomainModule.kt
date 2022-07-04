package com.hold.di

import com.hold.domain.usecase.SetSessionTokenUseCase
import com.hold.domain.usecase.user.*
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



