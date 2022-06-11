package com.hold.di

import com.hold.data.repository.AccountRepository
import com.hold.data.repository.AccountRepositoryImpl
import com.hold.data.repository.GameRepository
import com.hold.data.repository.GameRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<AccountRepository> { AccountRepositoryImpl(get()) }

    factory<GameRepository> { GameRepositoryImpl() }


}