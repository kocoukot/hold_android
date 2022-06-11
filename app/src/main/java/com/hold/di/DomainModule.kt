package com.hold.di

import com.hold.domain.usecase.user.GetGlobalResultsUseCase
import com.hold.domain.usecase.user.GetUserResultsUseCase
import com.hold.domain.usecase.user.SetNewResultUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetUserResultsUseCase(get()) }

    factory { SetNewResultUseCase(get()) }

    factory { GetGlobalResultsUseCase(get()) }


}



