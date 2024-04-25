package com.d4enst.laba_1_koshelek.view_models

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