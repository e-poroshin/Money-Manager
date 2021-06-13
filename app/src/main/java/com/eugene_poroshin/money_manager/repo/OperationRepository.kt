package com.eugene_poroshin.money_manager.repo

import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.repo.database.OperationDao
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OperationRepository(private val operationDao: OperationDao) {

    val allOperations: LiveData<List<Operation>> = operationDao.getAllOperations()

    suspend fun insert(operations: OperationEntity) {
        withContext(Dispatchers.IO) {
            operationDao.insert(operations)
        }
    }

    suspend fun delete(operations: OperationEntity) {
        withContext(Dispatchers.IO) {
            operationDao.delete(operations)
        }
    }

    suspend fun update(operations: OperationEntity) {
        withContext(Dispatchers.IO) {
            operationDao.update(operations)
        }
    }
}