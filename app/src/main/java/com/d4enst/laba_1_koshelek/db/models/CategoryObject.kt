package com.d4enst.laba_1_koshelek.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "category_object", foreignKeys = [
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.RESTRICT
    )
])
data class CategoryObject(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var categoryObjectName: String = "",

    @ColumnInfo(name = "category_id")
    var categoryId: Long,
)