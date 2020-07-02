package com.eugene_poroshin.money_manager.repo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eugene_poroshin.money_manager.operations.OperationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CategoryEntity::class, AccountEntity::class, OperationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao
    abstract fun operationDao(): OperationDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.categoryDao(), database.accountDao())
                }
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao, accountDao: AccountDao) {
            var category = CategoryEntity("Продукты")
            categoryDao.insert(category)
            category = CategoryEntity("Здоровье")
            categoryDao.insert(category)
            category = CategoryEntity("Кафе")
            categoryDao.insert(category)
            category = CategoryEntity("Досуг")
            categoryDao.insert(category)
            category = CategoryEntity("Транспорт")
            categoryDao.insert(category)
            category = CategoryEntity("Подарки")
            categoryDao.insert(category)
            category = CategoryEntity("Покупки")
            categoryDao.insert(category)
            category = CategoryEntity("Семья")
            categoryDao.insert(category)
            category = CategoryEntity("Зарплата")
            categoryDao.insert(category)
            var account = AccountEntity("Наличные", 0.0, "BYN")
            accountDao.insert(account)
            account = AccountEntity("Карта", 0.0, "BYN")
            accountDao.insert(account)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
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
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}