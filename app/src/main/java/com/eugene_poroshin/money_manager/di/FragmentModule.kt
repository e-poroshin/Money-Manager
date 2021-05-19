package com.eugene_poroshin.money_manager.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.ui.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.ui.categories.CategoryViewModel
import com.eugene_poroshin.money_manager.ui.operations.OperationsViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun categoryViewModelProvider(fragment: Fragment): CategoryViewModel {
        return ViewModelProvider(fragment).get(CategoryViewModel::class.java)
    }

    @Provides
    fun accountViewModelProvider(fragment: Fragment): AccountsViewModel {
        return ViewModelProvider(fragment).get(AccountsViewModel::class.java)
    }

    @Provides
    fun operationViewModelProvider(fragment: Fragment): OperationsViewModel {
        return ViewModelProvider(fragment).get(OperationsViewModel::class.java)
    }
}