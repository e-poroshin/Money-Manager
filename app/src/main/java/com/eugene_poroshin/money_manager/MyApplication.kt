package com.eugene_poroshin.money_manager

import android.app.Application
import com.eugene_poroshin.money_manager.di.databaseModule
import com.eugene_poroshin.money_manager.di.repositoryModule
import com.eugene_poroshin.money_manager.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(listOf(databaseModule, repositoryModule, viewModelModule))
        }
    }
}