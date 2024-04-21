package com.d4enst.laba_1_koshelek.db.repositories

import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.models.Category

class CategoryRepository(private val categoryDao: CategoryDao) {
    fun getAll() = categoryDao.getAll()

    suspend fun addCategory(category: Category)
        = categoryDao.addCategory(category)

    suspend fun deleteCategory(category: Category)
            = categoryDao.deleteCategory(category)
}