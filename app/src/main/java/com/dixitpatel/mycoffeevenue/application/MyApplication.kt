package com.dixitpatel.mycoffeevenue.application

import androidx.appcompat.app.AppCompatDelegate
import com.dixitpatel.mycoffeevenue.BuildConfig
import com.dixitpatel.mycoffeevenue.dagger.components.DaggerMainAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

/**
 * MyApplication class that provides functionality of Dagger injectors.
 * */
open class MyApplication : DaggerApplication()
{
    companion object {
        @JvmStatic
        @get:Synchronized
        lateinit var instance: MyApplication

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    /**
     * Bind Android Injectors with DaggerComponent
     */
    override fun applicationInjector(): AndroidInjector<MyApplication?>? {
        return DaggerMainAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // If the build is on debug mode from Android studio then Logcat printed otherwise not to display.
        if (BuildConfig.DEBUG)
        {
            Timber.plant(Timber.DebugTree())
        }

        // App is Compatible with Night and Day mode.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}