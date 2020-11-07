package com.eugene_poroshin.money_manager.di

import com.eugene_poroshin.money_manager.repo.viewmodel.AccountsViewModel
import com.eugene_poroshin.money_manager.repo.viewmodel.CategoryViewModel
import com.eugene_poroshin.money_manager.repo.viewmodel.OperationsViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun inject(categoryViewModel: CategoryViewModel)
    fun inject(accountViewModel: AccountsViewModel)
    fun inject(operationsViewModel: OperationsViewModel)
}