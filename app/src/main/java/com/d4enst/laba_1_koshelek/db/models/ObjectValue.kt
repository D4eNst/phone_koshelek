package com.d4enst.laba_1_koshelek.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "object_value", foreignKeys = [
    ForeignKey(
        entity = CategoryObject::class,
        parentColumns = ["id"],
        childColumns = ["category_object_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.RESTRICT
    ),
    ForeignKey(
        entity = CategoryLabel::class,
        parentColumns = ["id"],
        childColumns = ["category_label"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.RESTRICT
    )
])
data class ObjectValue(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var value: String = "",
)