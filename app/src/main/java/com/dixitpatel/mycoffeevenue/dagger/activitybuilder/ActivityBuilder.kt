package com.dixitpatel.mycoffeevenue.dagger.activitybuilder

import com.dixitpatel.mycoffeevenue.ui.main.MainActivity
import com.dixitpatel.mycoffeevenue.ui.main.MainActivityModule
import com.dixitpatel.mycoffeevenue.ui.splash.SplashScreenActivity
import com.dixitpatel.mycoffeevenue.ui.splash.SplashScreenActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This is Dagger Activity Builder Which activities will be used in app must be add
 * in Dagger Module.
 *
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector(modules = [SplashScreenActivityModule::class])
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity?

}