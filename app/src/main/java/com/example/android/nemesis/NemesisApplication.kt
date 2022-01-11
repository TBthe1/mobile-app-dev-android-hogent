package com.example.android.nemesis

import android.app.Application
import timber.log.Timber

class NemesisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}