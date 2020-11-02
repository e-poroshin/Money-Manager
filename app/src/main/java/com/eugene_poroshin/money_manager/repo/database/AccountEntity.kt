package com.eugene_poroshin.money_manager.repo.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "accounts")
data class AccountEntity(
    @field:ColumnInfo(name = "account_name") var name: String,
    @field:ColumnInfo(name = "account_balance") var balance: Double,
    @field:ColumnInfo(name = "account_currency") var currency: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "account_id") var id: Int = 0
) : Parcelable