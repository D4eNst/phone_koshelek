package com.d4enst.laba_1_koshelek.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.d4enst.laba_1_koshelek.MainApplication
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    fun getAllCategories(): Flow<List<Category>>
            = categoryRepository.getAll()

    fun addCategory(categoryName: String) = viewModelScope.launch {
        categoryRepository.addCategory(Category(categoryName=categoryName))
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // контекст приложения из аргумента
                val application = (this[APPLICATION_KEY] as MainApplication)
                // экземпляр PatternsListViewModel, используя зависимость CategoryRepository
                CategoryViewModel(application.container.categoryRepository)
            }
        }
    }
}
