package com.eugene_poroshin.money_manager.ui.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.OperationRepository
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import com.eugene_poroshin.money_manager.ui.SingleLiveEvent
import kotlinx.coroutines.launch

class OperationsViewModel(private val operationRepository: OperationRepository) : ViewModel() {

    val categoryPosition = MutableLiveData<Int>()
    val accountPosition = MutableLiveData<Int>()
    val sum = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val isIncome = MutableLiveData<Boolean>()
    val categories = MutableLiveData<List<CategoryEntity>>()
    val accounts = MutableLiveData<List<AccountEntity>>()

    private var _isIncome = isIncome.value ?: false
    private var _categoryPosition = categoryPosition.value ?: 0
    private var _accountPosition = accountPosition.value ?: 0

    private val _finishEvent = SingleLiveEvent<Void>()
    val finishEvent: LiveData<Void> = _finishEvent

    val liveDataOperations = operationRepository.allOperations

    fun insert(operations: OperationEntity) = viewModelScope.launch {
        operationRepository.insert(operations)
    }

    fun update(operations: OperationEntity) = viewModelScope.launch {
        operationRepository.update(operations)
    }

    fun delete(operations: OperationEntity) = viewModelScope.launch {
        operationRepository.delete(operations)
    }

    fun saveOperation() {
        val categoryId = categories.value?.get(_categoryPosition)?.id ?: 0
        val accountId = accounts.value?.get(_accountPosition)?.id ?: 0
        val date = System.currentTimeMillis()
        val sum: Double = sum.value?.toDoubleOrNull() ?: 0.0
        val description: String = description.value.orEmpty()

        val newSumAccount = accounts.value?.get(_accountPosition)
        val currentBalance = accounts.value?.get(_accountPosition)?.balance
        val type: OperationType
        if (!_isIncome) {
            if (currentBalance != null) {
                newSumAccount?.balance = currentBalance - sum
            }
            type = OperationType.EXPENSE
        } else {
            if (currentBalance != null) {
                newSumAccount?.balance = currentBalance + sum
            }
            type = OperationType.INCOME
        }

        val operationEntity = OperationEntity(categoryId, accountId, date, sum, description, type)
        insert(operationEntity)

//        accountsViewModel.update(newSumAccount) //TODO
    }
}