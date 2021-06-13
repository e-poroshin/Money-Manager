package com.eugene_poroshin.money_manager.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.CategoryRepository
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    val liveDataCategories: LiveData<List<CategoryEntity>> = repository.allCategories

    fun insert(category: CategoryEntity) = viewModelScope.launch {
        repository.insert(category)
    }

    fun update(category: CategoryEntity) = viewModelScope.launch {
        repository.update(category)
    }

    fun delete(category: CategoryEntity) = viewModelScope.launch {
        repository.delete(category)
    }
}