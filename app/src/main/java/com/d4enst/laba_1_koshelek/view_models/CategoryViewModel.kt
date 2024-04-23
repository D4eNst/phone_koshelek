package com.d4enst.laba_1_koshelek.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.d4enst.laba_1_koshelek.MainApplication
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.db.repositories.CategoryRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    fun getAllCategories(): Flow<List<Category>>
            = categoryRepository.getAllCategories()

    fun addCategory(category: Category): Deferred<Long> = viewModelScope.async {
        categoryRepository.addCategory(category)
    }

    fun addMultipleCategoryLabel(categoryLabels: List<CategoryLabel>): Deferred<List<Long>> = viewModelScope.async {
        categoryRepository.addMultipleCategoryLabels(categoryLabels)
    }


    fun deleteAllCategoryLabelByCategoryId(categoryId: Long) = viewModelScope.launch {
        categoryRepository.deleteAllCategoryLabelByCategoryId(categoryId)
    }

    fun getCategoryById(categoryId: Long)
            = categoryRepository.getCategoryById(categoryId)

    fun getAllCategoryLabelsByCategoryId(categoryId: Long)
            = categoryRepository.getAllCategoryLabelsByCategoryId(categoryId)

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
