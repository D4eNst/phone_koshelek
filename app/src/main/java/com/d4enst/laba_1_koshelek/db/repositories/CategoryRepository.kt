package com.d4enst.laba_1_koshelek.db.repositories

import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.models.Category

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun insertCategory(category: Category)
        = categoryDao.addCategory(category)
}