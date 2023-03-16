package com.kocoukot.holdgame.di

import com.kocoukot.holdgame.data.repository.AccountRepositoryImpl
import com.kocoukot.holdgame.data.repository.LeaderboardRepositoryImpl
import com.kocoukot.holdgame.domain.repo.AccountRepository
import com.kocoukot.holdgame.domain.repo.LeaderboardRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<AccountRepository> { AccountRepositoryImpl(get(), get()) }

    factory<LeaderboardRepository> { LeaderboardRepositoryImpl(get(), get()) }


}