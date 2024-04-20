package com.d4enst.laba_1_koshelek.db

import android.content.Context
import androidx.room.Room
import androidx.room.Database as DatabaseAnnotation
import androidx.room.RoomDatabase
import com.d4enst.laba_1_koshelek.db.dao.CategoryDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryLabelDao
import com.d4enst.laba_1_koshelek.db.dao.CategoryObjectDao
import com.d4enst.laba_1_koshelek.db.dao.ObjectValueDao
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import com.d4enst.laba_1_koshelek.db.models.ObjectValue

@DatabaseAnnotation(
    [Category::class, CategoryLabel::class, CategoryObject::class, ObjectValue::class],
    version = 1,
    exportSchema = false,
)
abstract class Database : RoomDatabase(){
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getCategoryLabelDao(): CategoryLabelDao
    abstract fun getCategoryObjectDao(): CategoryObjectDao
    abstract fun getObjectValueDao(): ObjectValueDao

    companion object {
        @Volatile
        private var Instance: Database? = null

        fun getDatabase(context: Context): Database {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = Database::class.java,
                    name = "my_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
