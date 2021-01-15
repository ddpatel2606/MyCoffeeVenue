package com.dixitpatel.mycoffeevenue.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dixitpatel.mycoffeevenue.R
import com.dixitpatel.mycoffeevenue.ui.adapter.PlacesAdapter
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun a_test_isRecyclerViewVisible_onAppLaunch() {

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {

        onView(withId(R.id.recyclerView))
            .perform(actionOnItemAtPosition<PlacesAdapter.PlacesViewHolder>(0, click()))

    }

}