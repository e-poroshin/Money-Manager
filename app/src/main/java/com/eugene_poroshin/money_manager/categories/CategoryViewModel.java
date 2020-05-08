package com.eugene_poroshin.money_manager.categories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.eugene_poroshin.money_manager.repo.Repository;
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<CategoryEntity>> allCategoriesLiveData;
    private LiveData<List<String>> allCategoryNamesLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCategoriesLiveData = repository.getAllCategories();
        allCategoryNamesLiveData = repository.getCategoryNames();
    }

    public LiveData<List<CategoryEntity>> getLiveDataCategories() {
        return allCategoriesLiveData;
    }

    public LiveData<List<String>> getLiveDataCategoryNames() {
        return allCategoryNamesLiveData;
    }

    public void insert(CategoryEntity category) {
        repository.insert(category);
    }

    public void update(CategoryEntity category) {
        repository.update(category);
    }

    public void delete(CategoryEntity category) {
        repository.delete(category);
    }
}
