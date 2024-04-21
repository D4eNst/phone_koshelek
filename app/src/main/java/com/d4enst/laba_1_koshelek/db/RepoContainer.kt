package com.d4enst.laba_1_koshelek.db

import android.content.Context
import com.d4enst.laba_1_koshelek.db.repositories.CategoryRepository

class RepoContainer(private val context: Context) {
    val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(Database.getDatabase(context).getCategoryDao())
    }
}