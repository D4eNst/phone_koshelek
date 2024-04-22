package com.d4enst.laba_1_koshelek.db.repositories

import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryLabelDao
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel

class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val categoryLabelDao: CategoryLabelDao
) {
    fun getAll() = categoryDao.getAll()

    suspend fun addCategory(category: Category)
        = categoryDao.addCategory(category)

    suspend fun addMultipleCategoryLabels(categoryLabels: List<CategoryLabel>)
        = categoryLabelDao.addMultipleCategoryLabel(categoryLabels)

    suspend fun deleteCategory(category: Category)
            = categoryDao.deleteCategory(category)
}