package com.spark

import android.app.LauncherActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spark.presentation.ui.container.ContainerActivity
import com.spark.presentation.ui.showprofile.ShowProfileFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HiltShowProfileTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.spark", appContext.packageName)
    }


    @Test
    fun launchActivity() {
//        val navController = mock(NavController::class.java) // like object of class navController but without function implementation
        launchFragmentInHiltContainer<ShowProfileFragment> {
//            Navigation.setViewNavController(requireView(), navController)
  ///          viewModel = ShowProfileFragment( )
//            subscribeUi()
        }

//        val scenario = launchFragmentInHiltContainer<ShowProfileFragment> { }
    }

}

