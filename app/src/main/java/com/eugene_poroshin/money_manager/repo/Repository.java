package com.eugene_poroshin.money_manager.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.eugene_poroshin.money_manager.repo.database.AccountDao;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;
import com.eugene_poroshin.money_manager.repo.database.AppDatabase;
import com.eugene_poroshin.money_manager.repo.database.CategoryDao;
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity;
import com.eugene_poroshin.money_manager.repo.database.Operation;
import com.eugene_poroshin.money_manager.repo.database.OperationDao;
import com.eugene_poroshin.money_manager.repo.database.OperationEntity;

import java.util.List;

public class Repository {

    private CategoryDao categoryDao;
    private LiveData<List<CategoryEntity>> allCategoriesLiveData;
    private LiveData<List<String>> allCategoryNamesLiveData;

    private AccountDao accountDao;
    private LiveData<List<AccountEntity>> allAccountsLiveData;
    private LiveData<List<String>> allAccountNamesLiveData;

    private OperationDao operationDao;
    private LiveData<List<Operation>> allOperationsLiveData;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        categoryDao = db.categoryDao();
        allCategoriesLiveData = categoryDao.getAllCategories();
        allCategoryNamesLiveData = categoryDao.getCategoryNames();

        accountDao = db.accountDao();
        allAccountsLiveData = accountDao.getAllAccounts();
        allAccountNamesLiveData = accountDao.getAccountNames();

        operationDao = db.operationDao();
        allOperationsLiveData = operationDao.getAllOperations();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return allCategoriesLiveData;
    }

    public LiveData<List<String>> getCategoryNames() {
        return allCategoryNamesLiveData;
    }

    public void insert(final CategoryEntity category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.insert(category);
            }
        });
    }

    public void delete(final CategoryEntity category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.delete(category);
            }
        });
    }

    public void update(final CategoryEntity category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.update(category);
            }
        });
    }


    public LiveData<List<AccountEntity>> getAllAccounts() {
        return allAccountsLiveData;
    }

    public LiveData<List<String>> getAccountNames() {
        return allAccountNamesLiveData;
    }

    public void insert(final AccountEntity accounts) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                accountDao.insert(accounts);
            }
        });
    }

    public void delete(final AccountEntity accounts) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                accountDao.delete(accounts);
            }
        });
    }

    public void update(final AccountEntity accounts) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                accountDao.update(accounts);
            }
        });
    }


    public LiveData<List<Operation>> getAllOperations() {
        return allOperationsLiveData;
    }

    public void insert(final OperationEntity operations) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                operationDao.insert(operations);
            }
        });
    }

    public void delete(final OperationEntity operations) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                operationDao.delete(operations);
            }
        });
    }

    public void update(final OperationEntity operations) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                operationDao.update(operations);
            }
        });
    }
}
