package com.hold.di

import com.hold.data.repository.AccountRepository
import com.hold.data.repository.AccountRepositoryImpl
import com.hold.data.repository.LeaderboardRepository
import com.hold.data.repository.LeaderboardRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<AccountRepository> { AccountRepositoryImpl(get(), get()) }

    factory<LeaderboardRepository> { LeaderboardRepositoryImpl(get(), get()) }


}