package com.eugene_poroshin.money_manager.di

import com.eugene_poroshin.money_manager.ui.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.ui.categories.CategoryViewModel
import com.eugene_poroshin.money_manager.ui.operations.OperationsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AccountsViewModel(get())
    }

    viewModel {
        CategoryViewModel(get())
    }

    viewModel {
        OperationsViewModel(get())
    }
}