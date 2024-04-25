package com.d4enst.laba_1_koshelek.db

import android.content.Context
import com.d4enst.laba_1_koshelek.db.repositories.CategoryRepository
import com.d4enst.laba_1_koshelek.db.repositories.ObjectRepository

class RepoContainer(private val context: Context) {
    val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(
            Database.getDatabase(context).getCategoryDao(),
            Database.getDatabase(context).getCategoryLabelDao(),
        )
    }

    val objectRepository: ObjectRepository by lazy {
        val db = Database.getDatabase(context)
        ObjectRepository (
            db.getCategoryObjectDao(),
            db.getObjectValueDao(),
            db.getCategoryDao(),
        )
    }
}