package com.d4enst.laba_1_koshelek.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.d4enst.laba_1_koshelek.MainApplication
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import com.d4enst.laba_1_koshelek.db.repositories.ObjectRepository
import kotlinx.coroutines.launch

class ObjectViewModel (
    private val objectRepository: ObjectRepository,
) : ViewModel() {

    var showDialog by mutableStateOf(false)

    var categoryId = 0L

    var currentObjectId by mutableLongStateOf(0)
    var categoryObject by mutableStateOf(CategoryObject(categoryId = categoryId))

    var categoryObjectNameInput by mutableStateOf(categoryObject.categoryObjectName)

    val categoryObjectsSates = mutableStateListOf("", "")
    var categoryLabelsStates = mutableStateListOf("", "")

    fun getObjectsByCategoryId(categoryId: Long)
        = objectRepository.getObjectsByCategoryId(categoryId)

    fun getObjectById(categoryId: Long)
        = objectRepository.getObjectById(categoryId)

    fun getCategoryById(categoryId: Long)
        = objectRepository.getCategoryById(categoryId)

    fun deleteObject(categoryObject: CategoryObject) = viewModelScope.launch {
        // TODO delete all object values
        objectRepository.deleteObject(categoryObject)
    }

    fun collectObject() {
        // TODO("Not yet implemented")
    }

    fun collectCategoryLabels() {
        // TODO("Not yet implemented")
    }

    fun collectObjectsValues() {
        // TODO("Not yet implemented")
    }

    fun createOrChangeCategoryObject(editable: Boolean): Boolean {
        return false
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // контекст приложения из аргумента
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                // экземпляр PatternsListViewModel, используя зависимость CategoryRepository
                ObjectViewModel(application.container.objectRepository)
            }
        }
    }
}