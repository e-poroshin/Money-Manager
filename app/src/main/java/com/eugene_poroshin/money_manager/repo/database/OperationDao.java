package com.eugene_poroshin.money_manager.repo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OperationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OperationEntity operation);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(OperationEntity operation);

    @Delete
    void delete(OperationEntity operation);

    @Transaction
    @Query("SELECT * FROM operations")
    LiveData<List<Operation>> getAllOperations();
}
