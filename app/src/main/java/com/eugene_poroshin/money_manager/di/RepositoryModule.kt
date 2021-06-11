package com.eugene_poroshin.money_manager.di.module

import com.eugene_poroshin.money_manager.repo.AccountRepository
import com.eugene_poroshin.money_manager.repo.CategoryRepository
import com.eugene_poroshin.money_manager.repo.OperationRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { AccountRepository(get()) }

    single { CategoryRepository(get()) }

    single { OperationRepository(get()) }
}