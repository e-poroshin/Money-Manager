package com.eugene_poroshin.money_manager.repo.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.eugene_poroshin.money_manager.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CategoryEntity.class, AccountEntity.class, OperationEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract AccountDao accountDao();
    public abstract OperationDao operationDao();

    private static volatile AppDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static AppDatabase getInstance(final Application application) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application,
                            AppDatabase.class,
                            "db_save_money.db")
                            .addCallback(roomDataBaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDataBaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    CategoryDao categoryDao = INSTANCE.categoryDao();

                    CategoryEntity category = new CategoryEntity("Продукты");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Здоровье");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Кафе");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Досуг");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Транспорт");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Подарки");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Покупки");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Семья");
                    categoryDao.insert(category);
                    category = new CategoryEntity("Зарплата");
                    categoryDao.insert(category);

                    AccountDao accountDao = INSTANCE.accountDao();

                    AccountEntity account = new AccountEntity("Наличные",0.0, "BYN");
                    accountDao.insert(account);
                    account = new AccountEntity("Карта",0.0, "BYN");
                    accountDao.insert(account);
                }
            });
        }
    };

}
