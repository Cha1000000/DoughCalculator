package com.example.doughcalculator

import android.app.Application
import com.example.doughcalculator.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@BaseApplication)
            // Load modules
            modules(listOf(appModule))
        }
    }
}