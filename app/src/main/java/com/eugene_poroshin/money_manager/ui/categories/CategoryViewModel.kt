package com.eugene_poroshin.money_manager.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.CategoryRepository
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val liveDataCategories: LiveData<List<CategoryEntity>> = categoryRepository.allCategories

    fun insert(category: CategoryEntity) = viewModelScope.launch {
        categoryRepository.insert(category)
    }

    fun update(category: CategoryEntity) = viewModelScope.launch {
        categoryRepository.update(category)
    }

    fun delete(category: CategoryEntity) = viewModelScope.launch {
        categoryRepository.delete(category)
    }
}