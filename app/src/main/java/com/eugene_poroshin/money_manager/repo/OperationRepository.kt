package com.eugene_poroshin.money_manager.repo

import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.repo.database.OperationDao
import com.eugene_poroshin.money_manager.repo.database.OperationEntity

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