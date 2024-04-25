package com.d4enst.laba_1_koshelek.view_models

import android.util.Log
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
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import com.d4enst.laba_1_koshelek.db.models.ObjectValue
import com.d4enst.laba_1_koshelek.db.repositories.ObjectRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ObjectViewModel (
    private val objectRepository: ObjectRepository,
) : ViewModel() {

    var showDialog by mutableStateOf(false)

    var currentObjectId by mutableLongStateOf(0)
    var categoryObject by mutableStateOf(CategoryObject(categoryId = 0L))

    var categoryObjectNameInput by mutableStateOf(categoryObject.categoryObjectName)

    // Название + значения
    val categoryObjectsStates = mutableStateListOf("")
    val categoryLabelsStates = mutableStateListOf<CategoryLabel>()

    fun getObjectsByCategoryId(categoryId: Long)
        = objectRepository.getObjectsByCategoryId(categoryId)

    private fun getObjectById(categoryId: Long)
        = objectRepository.getObjectById(categoryId)

    fun getCategoryById(categoryId: Long)
        = objectRepository.getCategoryById(categoryId)

    private fun getAllObjectValues(categoryObjectId: Long)
        = objectRepository.getAllObjectValues(categoryObjectId)

    private fun getAllCategoryLabelsByCategoryId(categoryId: Long)
        = objectRepository.getAllCategoryLabelsByCategoryId(categoryId)

    suspend fun addMultipleObjectValues(objectValues: List<ObjectValue>)
            = objectRepository.addMultipleObjectValues(objectValues)

    private suspend fun deleteAllObjectValues(categoryObjectId: Long)
            = objectRepository.deleteAllObjectValues(categoryObjectId)

    fun deleteObject(categoryObject: CategoryObject) = viewModelScope.launch {
        deleteAllObjectValues(currentObjectId)
        objectRepository.deleteObject(categoryObject)
    }

    private suspend fun updateObject(categoryObject: CategoryObject)
        = objectRepository.updateObject(categoryObject)

    private suspend fun addCategoryObject(categoryObject: CategoryObject): Long
        = objectRepository.addObject(categoryObject)

    suspend fun collectObject() {
        Log.w("qwe", "1")
        delay(50)
        getObjectById(currentObjectId).collect {
            if (it != null)
            {
                categoryObject = it
                categoryObjectNameInput = categoryObject.categoryObjectName
                categoryObjectsStates[0] = categoryObjectNameInput
            }
        }
    }

    suspend fun collectCategoryLabels() {
        Log.w("qwe", "2")
        delay(100)
        getAllCategoryLabelsByCategoryId(categoryObject.categoryId).collect {categoryLabels ->
            categoryLabelsStates.clear()
            categoryLabelsStates.addAll(categoryLabels)
        }
    }

    suspend fun collectObjectsValues() {
        Log.w("qwe", "3")
        delay(150)
        getAllObjectValues(currentObjectId).collect {objectValues ->
            if (categoryObjectsStates.size <= 1)
            {
                categoryObjectsStates.removeRange(1, categoryObjectsStates.size)
                if (objectValues.isNotEmpty()){
                    categoryObjectsStates.addAll(objectValues.map { it.value })
                }
                else {
                    categoryObjectsStates.addAll(categoryLabelsStates.map { "" })
                }
            }
        }
    }

//    fun collectAll() = viewModelScope.launch {
//        collectObject()
//        collectCategoryLabels()
//        collectObjectsValues()
//    }

    fun createOrChangeCategoryObject(isEditable: Boolean): Boolean {
        if (isEditable) {
            if (categoryObjectNameInput != "") {
                viewModelScope.launch {
                    val newObjectId = if (categoryObject.id == 0L) {
                        addCategoryObject(categoryObject)
                    } else {
                        updateObject(categoryObject)
                        categoryObject.id // Используем существующий идентификатор категории
                    }
                    deleteAllObjectValues(newObjectId)
                    addMultipleObjectValues(
                        categoryObjectsStates.subList(1, categoryObjectsStates.size)
                            .mapIndexed { index, value ->
                                ObjectValue(0L, value, newObjectId, categoryLabelsStates[index].id)
                            }
                    )

                    currentObjectId = newObjectId
                    categoryObject.id = currentObjectId
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