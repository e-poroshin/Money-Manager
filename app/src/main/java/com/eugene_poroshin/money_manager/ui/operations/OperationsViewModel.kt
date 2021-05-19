package com.eugene_poroshin.money_manager.ui.operations

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OperationsViewModel(application: Application) : ViewModel() {

    private val operationDao = AppDatabase.getDatabase(application).operationDao()
    private val repository = Repository.OperationRepository(operationDao)
    //как-то странно, у нас есть DI но он не используется, создаем сущности внутри класса - нехорошо

    val liveDataOperations = repository.allOperations

    fun insert(operations: OperationEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(operations)
    }

    fun update(operations: OperationEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(operations)
    }

    fun delete(operations: OperationEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(operations)
    }
}