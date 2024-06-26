package com.d4enst.laba_1_koshelek.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.d4enst.laba_1_koshelek.db.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(Category::class)
    suspend fun add(category: Category): Long

    @Delete(Category::class)
    suspend fun delete(category: Category)

    @Update
    suspend fun update(category: Category)

    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getById(id: Long): Flow<Category?>
}
