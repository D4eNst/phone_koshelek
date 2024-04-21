package com.d4enst.laba_1_koshelek

import android.app.Application
import com.d4enst.laba_1_koshelek.db.RepoContainer

class MainApplication : Application() {
    lateinit var container: RepoContainer

    override  fun onCreate() {
        super.onCreate()
        container = RepoContainer(this)
    }
}