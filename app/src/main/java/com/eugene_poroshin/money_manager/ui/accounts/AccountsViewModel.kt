package com.eugene_poroshin.money_manager.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.AccountRepository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.ui.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class AccountsViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    val accountName = MutableLiveData<String>()
    val balance = MutableLiveData<String>()
    val currency = MutableLiveData<String>()

    private val _finishEvent = SingleLiveEvent<Void>()
    val finishEvent: LiveData<Void> = _finishEvent

    val liveDataAccounts: LiveData<List<AccountEntity>> = accountRepository.allAccounts

    fun update(accounts: AccountEntity) = viewModelScope.launch {
        accountRepository.update(accounts)
    }

    fun delete(accounts: AccountEntity) = viewModelScope.launch {
        accountRepository.delete(accounts)
    }

    fun addAccount() {
        val name = accountName.value?.replaceFirstChar { it.titlecase(Locale.getDefault()) }.orEmpty()
        val balance = balance.value?.toDoubleOrNull() ?: 0.0
        val currency = currency.value?.takeIf { it.isNotBlank() }?.uppercase(Locale.getDefault()) ?: "BYN"

        val accountEntity = AccountEntity(name, balance, currency)
        viewModelScope.launch {
            accountRepository.insert(accountEntity)
            _finishEvent.call()
        }
    }

    fun updateAccount(idAccountEntity: Int) {
        val name = accountName.value?.replaceFirstChar { it.titlecase(Locale.getDefault()) }.orEmpty()
        val balance = balance.value?.toDoubleOrNull() ?: 0.0
        val currency = currency.value?.takeIf { it.isNotBlank() }?.uppercase(Locale.getDefault()) ?: "BYN"

        val accountEntity = AccountEntity(name, balance, currency, idAccountEntity)
        viewModelScope.launch {
            accountRepository.update(accountEntity)
            _finishEvent.call()
        }
    }
}