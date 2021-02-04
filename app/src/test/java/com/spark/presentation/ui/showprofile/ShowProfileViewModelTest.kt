package com.spark.presentation.ui.showprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.spark.UnitTestUtils
import com.spark.UnitTestUtils.Companion.fakeNetworkError
import com.spark.data.utils.*
import com.spark.domain.repositories.ProfileRepository
import com.spark.domain.usecases.GetProfile
import com.spark.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ShowProfileViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var profileRepository: ProfileRepository

    lateinit var viewModel: ShowProfileViewModel

    lateinit var getProfile: GetProfile

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getProfile = GetProfile(profileRepository)
        viewModel = ShowProfileViewModel(getProfile)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Integration test GetProfile(Repo,UseCase,VM), return Success`() {
        runBlockingTest {

            val fakeProfile = Resource.Success(UnitTestUtils.fakeProfile)

            Mockito.`when`(profileRepository.getProfile()).thenReturn(fakeProfile)

            viewModel.getProfile()

            val result = viewModel.profileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertEquals(it, UnitTestUtils.fakeProfile)
            }
            result.onError {
                assertTrue(false)
            }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Integration test GetProfile(Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            val fakeProfile = Resource.Failure.NetworkException(fakeNetworkError)

            Mockito.`when`(profileRepository.getProfile()).thenReturn(fakeProfile)

            viewModel.getProfile()

            val result = viewModel.profileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertTrue(false)
            }
            result.onNetworkError {
                assertEquals(it, fakeNetworkError)
            }
        }
    }


}

