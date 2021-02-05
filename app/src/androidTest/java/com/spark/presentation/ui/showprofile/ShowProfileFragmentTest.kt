package com.spark.presentation.ui.showprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.usecases.GetProfile
import com.spark.getOrAwaitValueAndroidTest
import dagger.hilt.android.testing.*
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ShowProfileFragmentTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testShowProfile() = runBlocking {

        val fakeRepository = FakeProfileRepository()
        val viewModel = ShowProfileViewModel(GetProfile(fakeRepository))

        val result = viewModel.profileState.getOrAwaitValueAndroidTest()

        assertEquals(result, Resource.Loading<ProfileEntity>(null))

    }


}