package com.eugene_poroshin.money_manager.accounts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eugene_poroshin.money_manager.repo.Repository;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;

import java.util.List;

public class AccountsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<AccountEntity>> allAccountsLiveData;
    private LiveData<List<String>> allAccountNamesLiveData;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allAccountsLiveData = repository.getAllAccounts();
        allAccountNamesLiveData = repository.getAccountNames();
    }

    public LiveData<List<AccountEntity>> getLiveDataAccounts() {
        return allAccountsLiveData;
    }

    public LiveData<List<String>> getLiveDataAccountNames() {
        return allAccountNamesLiveData;
    }

    public void insert(AccountEntity accounts) {
        repository.insert(accounts);
    }

    public void update(AccountEntity accounts) {
        repository.update(accounts);
    }

    public void delete(AccountEntity accounts) {
        repository.delete(accounts);
    }
}
