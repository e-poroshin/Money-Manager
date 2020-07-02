package com.eugene_poroshin.money_manager.repo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
class CategoryEntity(
    @field:ColumnInfo(name = "category_name") var name: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") var id: Int = 0
)