package com.eugene_poroshin.money_manager.ui.operations

import android.app.Application
import androidx.lifecycle.*
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import com.eugene_poroshin.money_manager.ui.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OperationsViewModel(application: Application) : ViewModel() {

    val categorySelectedPosition = MutableLiveData<Int>()
    val categoryId = MutableLiveData<Int>()
    val accountId = MutableLiveData<Int>()
    val date = MutableLiveData<Long>()
    val sum = MutableLiveData<Double>()
    val description = MutableLiveData<String>()
    val operationType = MutableLiveData<OperationType>()

    private val _finishEvent = SingleLiveEvent<Void>()
    val finishEvent: LiveData<Void> = _finishEvent

    private val operationDao = AppDatabase.getDatabase(application).operationDao()
    private val operationRepository = Repository.OperationRepository(operationDao)
    //как-то странно, у нас есть DI но он не используется, создаем сущности внутри класса - нехорошо

    val liveDataOperations = operationRepository.allOperations

    fun insert(operations: OperationEntity) = viewModelScope.launch(Dispatchers.IO) {
        operationRepository.insert(operations)
    }

    fun update(operations: OperationEntity) = viewModelScope.launch(Dispatchers.IO) {
        operationRepository.update(operations)
    }

    fun delete(operations: OperationEntity) = viewModelScope.launch(Dispatchers.IO) {
        operationRepository.delete(operations)
    }

    fun saveOperation() {
        val categoryId = categories[binding?.spinnerCategories?.selectedItemPosition!!].id
        val accountId = accounts[binding?.spinnerAccounts?.selectedItemPosition!!].id
        val date = System.currentTimeMillis()
        val sum: Double = binding?.editTextBalance?.text.toString().toDoubleOrNull() ?: 0.0
        val description: String = binding?.editTextDescription?.text?.toString().orEmpty()
        val operationEntity = OperationEntity(categoryId, accountId, date, sum, description, type)
        insert(operationEntity)

        val newSumAccount = accounts[binding?.spinnerAccounts?.selectedItemPosition!!]
        val currentBalance = accounts[binding?.spinnerAccounts?.selectedItemPosition!!].balance
        when (type) {
            OperationType.EXPENSE -> newSumAccount.balance = currentBalance - sum
            OperationType.INCOME -> newSumAccount.balance = currentBalance + sum
        }
        accountsViewModel.update(newSumAccount)
    }
}