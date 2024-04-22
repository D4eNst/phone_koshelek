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
    suspend fun addCategoryLabel(categoryLabel: CategoryLabel)

    @Insert(CategoryLabel::class)
    suspend fun addMultipleCategoryLabel(categoryLabels: List<CategoryLabel>)

    @Delete(CategoryLabel::class)
    suspend fun deleteCategoryLabel(categoryLabel: CategoryLabel)
}
