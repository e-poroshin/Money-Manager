package com.eugene_poroshin.money_manager.accounts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class AccountsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)
    val liveDataAccounts: LiveData<MutableList<AccountEntity>> = repository.allAccounts
    val liveDataAccountNames: LiveData<MutableList<String>> = repository.accountNames

    fun insert(accounts: AccountEntity?) {
        repository.insert(accounts)
    }

    fun update(accounts: AccountEntity?) {
        repository.update(accounts)
    }

    fun delete(accounts: AccountEntity?) {
        repository.delete(accounts)
    }
}