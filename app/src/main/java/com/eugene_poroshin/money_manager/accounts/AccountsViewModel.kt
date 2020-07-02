package com.eugene_poroshin.money_manager.accounts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountsViewModel(application: Application) : AndroidViewModel(application) {

    private val accountDao = AppDatabase.getDatabase(application, viewModelScope).accountDao()
    private val repository = Repository.AccountRepository(accountDao)

    val liveDataAccounts: LiveData<List<AccountEntity>> = repository.allAccounts
    val liveDataAccountNames: LiveData<List<String>> = repository.accountNames

    fun insert(accounts: AccountEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(accounts)
    }

    fun update(accounts: AccountEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(accounts)
    }

    fun delete(accounts: AccountEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(accounts)
    }
}