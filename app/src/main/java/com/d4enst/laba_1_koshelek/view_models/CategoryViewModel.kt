package com.d4enst.laba_1_koshelek.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    // Первый элемент - название категории, остальные - labels
    val categoryStates = mutableStateListOf("", "")

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
        getCategoryById(currentCategoryId).collect {
            if (it != null)
            {
                category = it
                categoryNameInput = category.categoryName
                categoryStates[0] = categoryNameInput
            }
        }
    }
    suspend fun collectCategoryLabels() {
        this.getAllCategoryLabelsByCategoryId(currentCategoryId).collect { labels ->
            if (labels.isNotEmpty())
            {
                categoryLabels = labels
                categoryStates.removeRange(1, categoryStates.size)
                categoryStates.addAll(categoryLabels.map { it.categoryLabelName })
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
                        categoryStates.subList(1, categoryStates.size).map {
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
        categoryStates.removeAt(index = i)
        // Пользователь может использовать двойное нажатие и удалить сразу несколько строк
        // Перестраховываюсь и добавляю поле, если он удалил все
        if (categoryStates.size <= 1)
            categoryStates.add("")
    }

    fun addLabelInUI() {
        categoryStates.add("")
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
