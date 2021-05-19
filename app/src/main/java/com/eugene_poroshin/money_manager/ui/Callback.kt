package com.eugene_poroshin.money_manager.ui

import com.eugene_poroshin.money_manager.repo.database.AccountEntity

interface Callback {
    fun onItemClick(categoryName: String?)
    fun onItemAccountClick(accountEntity: AccountEntity?)
}