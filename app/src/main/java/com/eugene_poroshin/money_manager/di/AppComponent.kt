package com.eugene_poroshin.money_manager.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [FragmentModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface  Builder {

        @BindsInstance
        fun withApplication(application: Application): Builder

        fun build(): AppComponent
    }

    fun viewModelSubComponentBuilder(): ViewModelSubComponent.Builder
    fun fragmentSubComponentBuilder(): FragmentSubComponent.Builder
}