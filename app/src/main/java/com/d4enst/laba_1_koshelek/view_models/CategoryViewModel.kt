package com.d4enst.laba_1_koshelek.view_models

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    var showDialog by mutableStateOf(false)
    var currentCategoryId by mutableLongStateOf(0)
    var category by mutableStateOf(Category())
    var categoryNameInput by mutableStateOf(category.categoryName)

    private var categoryLabels by mutableStateOf<List<CategoryLabel>>(emptyList())

    val states = mutableStateListOf("", "")

    fun getAllCategories(): Flow<List<Category>>
            = categoryRepository.getAllCategories()

    private fun getCategoryById(categoryId: Long)
            = categoryRepository.getCategoryById(categoryId)

    private suspend fun addCategory(category: Category)
        = categoryRepository.addCategory(category)

    private suspend fun updateCategory(category: Category)
        = categoryRepository.updateCategory(category)

    private suspend fun addMultipleCategoryLabel(categoryLabels: List<CategoryLabel>)
        = categoryRepository.addMultipleCategoryLabels(categoryLabels)

    private suspend fun deleteAllCategoryLabelByCategoryId(categoryId: Long)
        = categoryRepository.deleteAllCategoryLabelByCategoryId(categoryId)

    private fun getAllCategoryLabelsByCategoryId(categoryId: Long)
            = categoryRepository.getAllCategoryLabelsByCategoryId(categoryId)

    fun deleteCategory(category: Category) = viewModelScope.launch {
        deleteAllCategoryLabelByCategoryId(category.id)
        categoryRepository.deleteCategory(category)
    }

    // Обновление категории из бд
    suspend fun collectCategory() {
        this.getCategoryById(currentCategoryId).collect {
            if (it != null)
            {
                category = it
                categoryNameInput = category.categoryName
                states[0] = categoryNameInput
            }
        }
    }
    suspend fun collectCategoryLabels() {
        this.getAllCategoryLabelsByCategoryId(currentCategoryId).collect { labels ->
            if (labels.isNotEmpty())
            {
                categoryLabels = labels
                states.removeRange(1, states.size)
                states.addAll(categoryLabels.map { it.categoryLabelName })
            }
        }
    }

    fun createOrChangeCategory(isEditable: Boolean): Boolean {
        if (isEditable) {
            if (categoryNameInput != "") {
                viewModelScope.launch {

                    val newCategoryId = if (category.id == 0L) {
                        // Добавление категории, если её не существует
                        addCategory(category)
                    } else {
                        updateCategory(category)
                        category.id // Используем существующий идентификатор категории
                    }
                    // Удаление всех CategoryLabel и создание заново
                    deleteAllCategoryLabelByCategoryId(newCategoryId)
                    addMultipleCategoryLabel(
                        states.subList(1, states.size).map {
                            CategoryLabel(0L, it, newCategoryId)
                        }
                    )
                    currentCategoryId = newCategoryId
                    category.id = currentCategoryId
                }
                return true
            }
            else
            {
                showDialog = true
                return false
            }
        }
        return true
    }

    fun removeLabelInUI(i: Int) {
        states.removeAt(index = i)
        // Пользователь может использовать двойное нажатие и удалить сразу несколько строк
        // Перестраховываюсь и добавляю поле, если он удалил все
        if (states.size <= 1)
            states.add("")
    }

    fun addLabelInUI() {
        states.add("")
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
