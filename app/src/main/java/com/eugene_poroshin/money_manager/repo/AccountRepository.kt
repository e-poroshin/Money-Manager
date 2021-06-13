package com.eugene_poroshin.money_manager.repo

import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.database.AccountDao
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository(private val accountDao: AccountDao) {

    val allAccounts: LiveData<List<AccountEntity>> = accountDao.allAccounts()

    suspend fun insert(accounts: AccountEntity) {
        withContext(Dispatchers.IO) {
            accountDao.insert(accounts)
        }
    }

    suspend fun delete(accounts: AccountEntity) {
        withContext(Dispatchers.IO) {
            accountDao.delete(accounts)
        }
    }

    suspend fun update(accounts: AccountEntity) {
        withContext(Dispatchers.IO) {
            accountDao.update(accounts)
        }
    }
}