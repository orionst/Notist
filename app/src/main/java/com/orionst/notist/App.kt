package com.orionst.notist

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.orionst.notist.di.appModule
import com.orionst.notist.di.noteListModule
import com.orionst.notist.di.noteModule
import com.orionst.notist.di.splashModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber.log.Timber.DebugTree())

        startKoin(this, listOf(appModule, splashModule, noteListModule, noteModule))
    }
}