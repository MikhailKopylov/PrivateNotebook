package com.amk.privatenotebook.ui

import androidx.multidex.MultiDexApplication
import com.amk.privatenotebook.di.DependencyGraph
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KoinApp)
            modules(DependencyGraph.modules)
        }
    }
}