package com.dixitpatel.mycoffeevenue.ui.splash

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dixitpatel.mycoffeevenue.ui.main.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class SplashScreenActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(SplashScreenActivity::class.java)

    @Test
    fun a_test_onAppLaunch() {

    }

}