package com.eugene_poroshin.money_manager.repo.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("SELECT * FROM accounts")
    fun allAccounts(): LiveData<List<AccountEntity>>

    @Query("SELECT account_name FROM accounts")
    fun accountNames(): LiveData<List<String>>
}