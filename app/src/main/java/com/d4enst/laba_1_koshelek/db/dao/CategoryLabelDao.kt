package com.d4enst.laba_1_koshelek.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryLabelDao {
    @Insert(CategoryLabel::class)
    suspend fun addCategoryLabel(categoryLabel: CategoryLabel): Long

    @Insert(CategoryLabel::class)
    suspend fun addMultiple(categoryLabels: List<CategoryLabel>): List<Long>

    @Delete(CategoryLabel::class)
    suspend fun deleteCategoryLabel(categoryLabel: CategoryLabel)

    @Query("DELETE FROM category_label WHERE category_id = :categoryId")
    suspend fun deleteAllByCategoryId(categoryId: Long)

    @Query("SELECT * FROM category_label WHERE category_id = :categoryId")
    fun getAllByCategoryId(categoryId: Long): Flow<List<CategoryLabel>>
}
