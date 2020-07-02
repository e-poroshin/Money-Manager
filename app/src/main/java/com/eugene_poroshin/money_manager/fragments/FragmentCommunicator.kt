package com.eugene_poroshin.money_manager.fragments

import com.eugene_poroshin.money_manager.repo.database.AccountEntity

interface FragmentCommunicator {
    fun onItemClickListener(categoryName: String?)
    fun onItemAccountClickListener(accountEntity: AccountEntity?)
}