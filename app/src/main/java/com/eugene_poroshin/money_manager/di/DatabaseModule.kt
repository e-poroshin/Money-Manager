package com.eugene_poroshin.money_manager.di

import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        AppDatabase.getDatabase(androidApplication())
    }

    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().operationDao() }
}