package com.eugene_poroshin.money_manager.repo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.eugene_poroshin.money_manager.repo.Repository
import com.eugene_poroshin.money_manager.repo.database.AppDatabase
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    //Действительно ли нам нужено наследоваться от AndroidViewModel?

    private val categoryDao = AppDatabase.getDatabase(application, viewModelScope).categoryDao()
    private val repository = Repository.CategoryRepository(categoryDao)
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