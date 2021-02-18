package com.spark.presentation.ui.editprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.spark.CustomAssertion.Companion.setTextInTextView
import com.spark.FakeProfileRepository
import com.spark.R
import com.spark.launchFragmentInHiltContainer
import com.spark.presentation.utils.Constants
import com.spark.presentation.utils.components.base.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class EditProfileFragmentTest {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val fakeProfile = FakeProfileRepository().profile

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

    @ExperimentalCoroutinesApi
    @Test
    fun testFragmentTitleIsShowing() {
        launchFragmentInHiltContainer<EditProfileFragment>()

        onView(withId(R.id.pageTitle)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testForm() {
        launchFragmentInHiltContainer<EditProfileFragment>()

        onView(withId(R.id.pageTitle)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        onView(withId(R.id.displayNameEdt))
            .perform(setTextInTextView(fakeProfile.displayName))

        onView(withId(R.id.realNameEdt))
            .perform(setTextInTextView(fakeProfile.realName))


        //WRONG Format to test validation
        onView(withId(R.id.birthdayEdt))
            .perform(setTextInTextView("112233"))

        onView(withId(R.id.genderSpinner))
            .perform(setTextInTextView(fakeProfile.gender))

        onView(withId(R.id.ethnicitySpinner))
            .perform(setTextInTextView(fakeProfile.ethnicity))

        onView(withId(R.id.religionSpinner))
            .perform(setTextInTextView(fakeProfile.religion))


        onView(withId(R.id.heightEdt))
            .perform(scrollTo())
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.heightEdt))
            .perform(setTextInTextView(fakeProfile.height.toString()))

//Espresso cant test the items which are in out of visibility frame
        onView(withId(R.id.updateProfileBtn))
            .perform(scrollTo())
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.maritalSpinner))
            .perform(setTextInTextView(fakeProfile.maritalStatus))

        onView(withId(R.id.occupationEdt))
            .perform(setTextInTextView(fakeProfile.occupation))

        onView(withId(R.id.aboutMeEdt))
            .perform(setTextInTextView(fakeProfile.aboutMe))

        onView(withId(R.id.locationSpinner))
            .perform(setTextInTextView(fakeProfile.locationTitle))

        onView(withId(R.id.updateProfileBtn)).perform(click())


        onView(withId(R.id.errorMsgTv)).check(matches(withText(Constants.BIRTHDAY_WRONG_ERROR)))

    }
}