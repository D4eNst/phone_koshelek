package com.d4enst.laba_1_koshelek.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.d4enst.laba_1_koshelek.MainApplication
import com.d4enst.laba_1_koshelek.db.repositories.CategoryRepository


class PatternsListViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    companion object {
        // Статическое поле Factory, представляющее фабрику ViewModelProvider
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Получаем контекст приложения из аргумента
                val application = (this[APPLICATION_KEY] as MainApplication)
                // Создаем экземпляр HomeViewModel, используя зависимость MyFriendsRepository
                PatternsListViewModel(application.container.categoryRepository)
            }
        }
    }
}
