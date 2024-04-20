package com.d4enst.laba_1_koshelek.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryObjectDao {
    @Insert(CategoryObject::class)
    suspend fun addCategoryObject(categoryObject: CategoryObject)

    @Delete(CategoryObject::class)
    suspend fun deleteCategoryObject(categoryObject: CategoryObject)
}
