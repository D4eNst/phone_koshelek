package com.d4enst.laba_1_koshelek.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryObjectDao {
    @Insert(CategoryObject::class)
    suspend fun add(categoryObject: CategoryObject): Long

    @Delete(CategoryObject::class)
    suspend fun delete(categoryObject: CategoryObject)

    @Update
    suspend fun update(categoryObject: CategoryObject)

    @Query("SELECT * FROM category_objects WHERE category_id = :categoryId")
    fun getByCategoryId(categoryId: Long): Flow<List<CategoryObject>>

    @Query("SELECT * FROM category_objects WHERE id = :id")
    fun getById(id: Long): Flow<CategoryObject?>
}
