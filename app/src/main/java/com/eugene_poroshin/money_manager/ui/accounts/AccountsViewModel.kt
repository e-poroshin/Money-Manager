package com.eugene_poroshin.money_manager.ui.accounts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.databinding.ActivityAddAccountBinding
import com.eugene_poroshin.money_manager.databinding.ActivityEditAccountBinding
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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

    fun addAccount(binding: ActivityAddAccountBinding) {
        //это та логика, которая должна быть в viewModel - OK?
        val name: String = binding.editTextAccountName.text?.toString()?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }.orEmpty()
        val balance: Double = binding.editTextBalance.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String = binding.editTextCurrency.text?.toString()
            ?.takeIf { it.isNotBlank() }?.uppercase(Locale.getDefault()) ?: "BYN"

        val accountEntity = AccountEntity(name, balance, currency)
        insert(accountEntity)
    }

    fun updateAccount(binding: ActivityEditAccountBinding, idAccountEntity: Int) {
        //этот метод должен быть в viewModel - OK?
        val name: String = binding.editTextAccountNameNew.text?.toString()
            ?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }.orEmpty()
        val balance: Double = binding.editTextBalanceNew.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String = binding.editTextCurrencyNew.text?.toString()?.takeIf { it.isNotBlank() }
            ?.uppercase(Locale.getDefault()) ?: "BYN"
        val accountEntity = AccountEntity(name, balance, currency, idAccountEntity)
        update(accountEntity)
    }
}