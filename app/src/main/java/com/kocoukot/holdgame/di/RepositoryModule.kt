package com.kocoukot.holdgame.di

import com.kocoukot.holdgame.data.repository.AccountRepository
import com.kocoukot.holdgame.data.repository.AccountRepositoryImpl
import com.kocoukot.holdgame.data.repository.LeaderboardRepository
import com.kocoukot.holdgame.data.repository.LeaderboardRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<AccountRepository> { AccountRepositoryImpl(get(), get()) }

    factory<LeaderboardRepository> { LeaderboardRepositoryImpl(get(), get()) }


}