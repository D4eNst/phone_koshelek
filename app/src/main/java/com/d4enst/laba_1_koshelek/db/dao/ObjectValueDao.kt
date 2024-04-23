package com.d4enst.laba_1_koshelek.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.d4enst.laba_1_koshelek.db.models.ObjectValue
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectValueDao {
    @Insert(ObjectValue::class)
    suspend fun addObjectValue(objectValue: ObjectValue): Long

    @Delete(ObjectValue::class)
    suspend fun deleteObjectValue(objectValue: ObjectValue)
}
