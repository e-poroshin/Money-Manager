package com.eugene_poroshin.money_manager.operations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eugene_poroshin.money_manager.repo.Repository;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;
import com.eugene_poroshin.money_manager.repo.database.Operation;
import com.eugene_poroshin.money_manager.repo.database.OperationEntity;

import java.util.List;

public class OperationsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Operation>> allOperationsLiveData;

    public OperationsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allOperationsLiveData = repository.getAllOperations();
    }

    public LiveData<List<Operation>> getLiveData() {
        return allOperationsLiveData;
    }

    public void insert(OperationEntity operations) {
        repository.insert(operations);
    }

    public void update(OperationEntity operations) {
        repository.update(operations);
    }

    public void delete(OperationEntity operations) {
        repository.delete(operations);
    }
}
