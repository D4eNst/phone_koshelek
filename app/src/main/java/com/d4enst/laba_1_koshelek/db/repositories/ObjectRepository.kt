package com.d4enst.laba_1_koshelek.db.repositories

import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryLabelDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryObjectDao
import com.d4enst.laba_1_koshelek.db.dao.ObjectValueDao
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import com.d4enst.laba_1_koshelek.db.models.ObjectValue

class ObjectRepository(
    private val categoryObjectDao: CategoryObjectDao,
    private val categoryLabelDao: CategoryLabelDao,
    private val categoryDao: CategoryDao,
    private val objectValueDao: ObjectValueDao,
) {
    fun getObjectsByCategoryId(categoryId: Long)
        = categoryObjectDao.getByCategoryId(categoryId)

    fun getObjectById(categoryId: Long)
        = categoryObjectDao.getById(categoryId)

    fun getCategoryById(categoryId: Long)
        = categoryDao.getById(categoryId)

    fun getAllCategoryLabelsByCategoryId(categoryId: Long)
        = categoryLabelDao.getAllByCategoryId(categoryId)

    fun getAllObjectValues(categoryObjectId: Long)
        = objectValueDao.getByCategoryAndLabelId(categoryObjectId)

    suspend fun addObject(categoryObject: CategoryObject)
        = categoryObjectDao.add(categoryObject)

    suspend fun updateObject(categoryObject: CategoryObject)
        = categoryObjectDao.update(categoryObject)

    suspend fun deleteObject(categoryObject: CategoryObject)
        = categoryObjectDao.delete(categoryObject)

    suspend fun addMultipleObjectValues(objectValues: List<ObjectValue>)
        = objectValueDao.addMultiple(objectValues)
    suspend fun deleteAllObjectValues(categoryObjectId: Long)
        = objectValueDao.deleteObjectValue(categoryObjectId)


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