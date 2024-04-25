package com.d4enst.laba_1_koshelek.db.repositories

import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryLabelDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryObjectDao
import com.d4enst.laba_1_koshelek.db.dao.ObjectValueDao
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.db.models.CategoryObject

class ObjectRepository(
    private val categoryObjectDao: CategoryObjectDao,
    private val categoryLabelDao: ObjectValueDao,
    private val categoryDao: CategoryDao,
) {
    fun getObjectsByCategoryId(categoryId: Long)
        = categoryObjectDao.getByCategoryId(categoryId)

    fun getObjectById(categoryId: Long)
        = categoryObjectDao.getById(categoryId)

    fun getCategoryById(categoryId: Long)
        = categoryDao.getById(categoryId)

    suspend fun deleteObject(categoryObject: CategoryObject)
        = categoryObjectDao.delete(categoryObject)

//    fun getAllCategoryLabelsByCategoryId(categoryId: Long)
//        = categoryLabelDao.getAllByCategoryId(categoryId)
//
//    suspend fun addCategory(category: Category)
//        = categoryDao.add(category)
//
//    suspend fun updateCategory(category: Category)
//        = categoryDao.update(category)
//
//    suspend fun addMultipleCategoryLabels(categoryLabels: List<CategoryLabel>)
//        = categoryLabelDao.addMultiple(categoryLabels)
//
//    suspend fun deleteCategory(category: Category)
//        = categoryDao.delete(category)
//
//    suspend fun deleteAllCategoryLabelByCategoryId(categoryId: Long)
//        = categoryLabelDao.deleteAllByCategoryId(categoryId)
}