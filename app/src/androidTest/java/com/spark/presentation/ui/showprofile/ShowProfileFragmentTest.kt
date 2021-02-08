package com.spark.presentation.ui.showprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.spark.CustomAssertion.Companion.hasItemCount
import com.spark.R
import com.spark.data.utils.Resource
import com.spark.domain.usecases.GetProfile
import com.spark.getOrAwaitValueAndroidTest
import com.spark.launchFragmentInHiltContainer
import com.spark.FakeProfileRepository
import com.spark.presentation.ui.editprofile.EditProfileFragment
import com.spark.presentation.utils.components.base.EspressoIdlingResource
import dagger.hilt.android.testing.*
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ShowProfileFragmentTest {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun useAppContext() {

        Assert.assertEquals("com.spark", appContext.packageName)
    }

    @Test
    fun testShowProfile() = runBlocking {

        val fakeRepository = FakeProfileRepository()
        val viewModel = ShowProfileViewModel(GetProfile(fakeRepository))
        viewModel.getProfile()

        val result = viewModel.profileState.getOrAwaitValueAndroidTest()

        assertNotSame(result, Resource.Failure.Generic(null))
        assertNotSame(result, Resource.Failure.NetworkException(null))

    }

    @ExperimentalCoroutinesApi
    @Test
    fun avatarIsVisible() {

        launchFragmentInHiltContainer<ShowProfileFragment>()

        onView(withId(R.id.avatar)).check(matches(isDisplayed()))

        onView(withId(R.id.loading)).check(matches(not(isDisplayed())))

        onView(withId(R.id.lastUpdate))
            .check(matches(withText(startsWith(appContext.getString(R.string.last_update)))))

    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkProfileListSize() {

        launchFragmentInHiltContainer<ShowProfileFragment>()

        onView(withId(R.id.detailsLv))
            .check(hasItemCount(10))

    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkShowingItems() {

        launchFragmentInHiltContainer<ShowProfileFragment>(){
            //do whatever we want in the Fragment(with variables and methods)
        }

        onView(
            allOf(
                withId(R.id.title),
                withText(startsWith(appContext.getString(R.string.display_name)))
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        onView(
            allOf(
                withId(R.id.title),
                withText(startsWith(appContext.getString(R.string.religion)))
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        onView(
            allOf(
                withId(R.id.title),
                withText(startsWith(appContext.getString(R.string.occupation)))
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        onView(
            allOf(
                withId(R.id.title),
                withText(startsWith(appContext.getString(R.string.location)))
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        onView(
            allOf(
                withId(R.id.title),
                withText(startsWith(appContext.getString(R.string.marital_status)))
            )
        ).check(
            matches(
                isDisplayed()
            )
        )



    }




}