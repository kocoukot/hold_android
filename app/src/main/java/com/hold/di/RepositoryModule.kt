package com.hold.di

import com.hold.data.repository.AccountRepository
import com.hold.data.repository.AccountRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<AccountRepository> { AccountRepositoryImpl(get()) }

}