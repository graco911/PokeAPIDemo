package com.gracodev.pokeapidemo;

import android.app.Application
import com.gracodev.pokeapidemo.modules.createAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules = createAppModules()

        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}
