package com.eugene_poroshin.money_manager.repo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "accounts")
class AccountEntity(
    @field:ColumnInfo(name = "account_name") var name: String,
    @field:ColumnInfo(name = "account_balance") var balance: Double,
    @field:ColumnInfo(name = "account_currency") var currency: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "account_id") var id: Int = 0
) : Serializable