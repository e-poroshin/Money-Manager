package com.eugene_poroshin.money_manager.ui.accounts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountsViewModel(application: Application) : ViewModel() {
    //нам нужен будет только Репо

    private val accountDao = AppDatabase.getDatabase(application).accountDao()
    private val repository = Repository.AccountRepository(accountDao)
    //как-то странно, у нас есть DI но он не используется, создаем сущности внутри класса - нехорошо

    val liveDataAccounts: LiveData<List<AccountEntity>> = repository.allAccounts

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