package com.eugene_poroshin.money_manager.repo

import androidx.lifecycle.LiveData
import com.eugene_poroshin.money_manager.repo.database.CategoryDao
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity

class CategoryRepository(private val categoryDao: CategoryDao) {

    //todo use KotlinFlow instead LiveData (Room doc)
    val allCategories: LiveData<List<CategoryEntity>> = categoryDao.allCategories()

    suspend fun insert(category: CategoryEntity) {
        categoryDao.insert(category)
    }

    suspend fun delete(category: CategoryEntity) {
        categoryDao.delete(category)
    }

    suspend fun update(category: CategoryEntity) {
        categoryDao.update(category)
    }
}