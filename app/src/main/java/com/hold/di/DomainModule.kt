package com.hold.di

import com.hold.domain.usecase.user.GetGlobalResultsUseCase
import com.hold.domain.usecase.user.GetUserLocalRecordUseCase
import com.hold.domain.usecase.user.GetUserResultsUseCase
import com.hold.domain.usecase.user.SaveNewResultUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetUserResultsUseCase(get()) }

    factory { SaveNewResultUseCase(get()) }

    factory { GetGlobalResultsUseCase(get()) }

    factory { GetUserLocalRecordUseCase(get()) }


}



