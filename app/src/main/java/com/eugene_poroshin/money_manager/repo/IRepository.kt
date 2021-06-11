package com.eugene_poroshin.money_manager.repo

import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.repo.database.OperationEntity

interface IRepository {
    fun getAccounts() : List<AccountEntity>
    fun getCategories() : List<CategoryEntity>
    fun getOperations() : List<Operation>
}