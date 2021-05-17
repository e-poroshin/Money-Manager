package com.eugene_poroshin.money_manager.ui

import com.eugene_poroshin.money_manager.repo.database.AccountEntity

interface FragmentCommunicator {
    fun onItemClickListener(categoryName: String?)
    fun onItemAccountClickListener(accountEntity: AccountEntity?)
    //это не Listener-ы, это события
    //onItemClick(), onItemAccountClick()
}