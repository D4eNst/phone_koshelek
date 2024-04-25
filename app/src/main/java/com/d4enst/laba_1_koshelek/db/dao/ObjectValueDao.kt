package com.d4enst.laba_1_koshelek.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import com.d4enst.laba_1_koshelek.db.models.ObjectValue
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectValueDao {
    @Insert(ObjectValue::class)
    suspend fun addMultiple(objectValues: List<ObjectValue>)

    @Query("DELETE FROM object_value WHERE category_object_id = :categoryObjectId")
    suspend fun deleteObjectValue(categoryObjectId: Long)

    @Query("SELECT * FROM object_value WHERE category_object_id = :categoryObjectId")
    fun getByCategoryAndLabelId(categoryObjectId: Long): Flow<List<ObjectValue>>
}
