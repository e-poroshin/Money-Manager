package com.eugene_poroshin.money_manager.ui.categories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.CategoryRepository
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : ViewModel() {

    private val categoryDao = AppDatabase.getDatabase(application).categoryDao()
    private val repository = CategoryRepository(categoryDao)
    //как-то странно, у нас есть DI но он не используется, создаем сущности внутри класса - нехорошо

    val liveDataCategories: LiveData<List<CategoryEntity>> = repository.allCategories

    fun insert(category: CategoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(category)
    }

    fun update(category: CategoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(category)
    }

    fun delete(category: CategoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(category)
    }
}