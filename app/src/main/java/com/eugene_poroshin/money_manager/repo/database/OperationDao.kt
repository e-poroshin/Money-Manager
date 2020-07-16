package com.eugene_poroshin.money_manager.repo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OperationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operation: OperationEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(operation: OperationEntity)

    @Delete
    suspend fun delete(operation: OperationEntity)

    @Query("SELECT * FROM operations")
    @Transaction
    fun getAllOperations(): LiveData<List<Operation>>
}