package com.eugene_poroshin.money_manager.repo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eugene_poroshin.money_manager.ui.operations.OperationTypeConverter

@TypeConverters(OperationTypeConverter::class)
@Database(
    entities = [CategoryEntity::class, AccountEntity::class, OperationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao
    abstract fun operationDao(): OperationDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db_save_money.db"
                )
                    .createFromAsset("database/save_money.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}