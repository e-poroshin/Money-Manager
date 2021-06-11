package com.eugene_poroshin.money_manager.di

import androidx.room.Room
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room
            .databaseBuilder(androidApplication(), AppDatabase::class.java,
                androidApplication().getString(R.string.database))
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().operationDao() }
}