package com.eugene_poroshin.money_manager

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.repo.database.AccountEntity


@BindingAdapter("accounts")
fun setAccounts(view: RecyclerView, accounts: List<AccountEntity>) {

}