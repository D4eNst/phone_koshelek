package com.d4enst.laba_1_koshelek.db.repositories

import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryLabelDao
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel

class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val categoryLabelDao: CategoryLabelDao
) {
    fun getAllCategories()
        = categoryDao.getAll()

    fun getCategoryById(categoryId: Long)
        = categoryDao.getById(categoryId)

    fun getAllCategoryLabelsByCategoryId(categoryId: Long)
        = categoryLabelDao.getAllByCategoryId(categoryId)

    suspend fun addCategory(category: Category)
        = categoryDao.add(category)

    suspend fun updateCategory(category: Category)
        = categoryDao.update(category)

    suspend fun addMultipleCategoryLabels(categoryLabels: List<CategoryLabel>)
        = categoryLabelDao.addMultiple(categoryLabels)

    suspend fun deleteCategory(category: Category)
            = categoryDao.delete(category)

    suspend fun deleteAllCategoryLabelByCategoryId(categoryId: Long)
            = categoryLabelDao.deleteAllByCategoryId(categoryId)
}