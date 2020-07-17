package com.eugene_poroshin.money_manager.di

import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.accounts.AccountsFragment
import com.eugene_poroshin.money_manager.categories.CategoriesFragment
import com.eugene_poroshin.money_manager.operations.OperationsFragment
import com.eugene_poroshin.money_manager.statistics.StatisticsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [FragmentModule::class])
interface FragmentSubComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun with(fragment: Fragment): Builder
        fun build(): FragmentSubComponent
    }

    fun inject(categoriesFragment: CategoriesFragment)
    fun inject(statisticsFragment: StatisticsFragment)
    fun inject(accountsFragment: AccountsFragment)
    fun inject(operationsFragment: OperationsFragment)
}