package com.eugene_poroshin.money_manager.ui.accounts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.databinding.ActivityAddAccountBinding
import com.eugene_poroshin.money_manager.databinding.ActivityEditAccountBinding
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import com.eugene_poroshin.money_manager.ui.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AccountsViewModel(application: Application) : ViewModel() {
    //нам нужен будет только Репо
    val accountName = MutableLiveData<String>()
    val balance = MutableLiveData<String>()
    val currency = MutableLiveData<String>()

    private val _finishEvent = SingleLiveEvent<Void>()
    val finishEvent: LiveData<Void> = _finishEvent

    private val accountDao = AppDatabase.getDatabase(application).accountDao()
    private val repository = Repository.AccountRepository(accountDao)
    //как-то странно, у нас есть DI но он не используется, создаем сущности внутри класса - нехорошо

    val liveDataAccounts: LiveData<List<AccountEntity>> = repository.allAccounts

    fun update(accounts: AccountEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(accounts)
    }

    fun delete(accounts: AccountEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(accounts)
    }

    //Примерно так должен выглядеть метод в
    fun addAccount() {
        val name = accountName.value?.replaceFirstChar { it.titlecase(Locale.getDefault()) }.orEmpty()
        val balance = balance.value?.toDoubleOrNull() ?: 0.0
        val currency = currency.value?.takeIf { it.isNotBlank() }?.uppercase(Locale.getDefault()) ?: "BYN"

        val accountEntity = AccountEntity(name, balance, currency)
        viewModelScope.launch {
            repository.insert(accountEntity)
            _finishEvent.call()
        }
    }

    fun updateAccount(binding: ActivityEditAccountBinding, idAccountEntity: Int) {
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