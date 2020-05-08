package com.eugene_poroshin.money_manager.repo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AccountEntity account);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(AccountEntity account);

    @Delete
    void delete(AccountEntity account);

    @Query("SELECT * FROM accounts")
    LiveData<List<AccountEntity>> getAllAccounts();

    @Query("SELECT account_name FROM accounts")
    LiveData<List<String>> getAccountNames();
}
