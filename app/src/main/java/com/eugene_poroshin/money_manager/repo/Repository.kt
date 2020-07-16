package com.eugene_poroshin.money_manager.repo

import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.database.*

abstract class Repository {

    class CategoryRepository(private val categoryDao: CategoryDao) {

        val allCategories: LiveData<List<CategoryEntity>> = categoryDao.allCategories()
        val categoryNames: LiveData<List<String>> = categoryDao.categoryNames()

        suspend fun insert(category: CategoryEntity) {
            categoryDao.insert(category)
        }

        suspend fun delete(category: CategoryEntity) {
            categoryDao.delete(category)
        }

        suspend fun update(category: CategoryEntity) {
            categoryDao.update(category)
        }
    }

    class AccountRepository(private val accountDao: AccountDao) {

        val allAccounts: LiveData<List<AccountEntity>> = accountDao.allAccounts()
        val accountNames: LiveData<List<String>> = accountDao.accountNames()

        suspend fun insert(accounts: AccountEntity) {
            accountDao.insert(accounts)
        }

        suspend fun delete(accounts: AccountEntity) {
            accountDao.delete(accounts)
        }

        suspend fun update(accounts: AccountEntity) {
            accountDao.update(accounts)
        }
    }

    class OperationRepository(private val operationDao: OperationDao) {

        val allOperations: LiveData<List<Operation>> = operationDao.getAllOperations()

        suspend fun insert(operations: OperationEntity) {
            operationDao.insert(operations)
        }

        suspend fun delete(operations: OperationEntity) {
            operationDao.delete(operations)
        }

        suspend fun update(operations: OperationEntity) {
            operationDao.update(operations)
        }
    }
}