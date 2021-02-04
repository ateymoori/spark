package com.spark.presentation.ui.editprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.spark.UnitTestUtils
import com.spark.UnitTestUtils.Companion.fakeNetworkError
import com.spark.data.utils.*
import com.spark.domain.repositories.EthnicityRepository
import com.spark.domain.repositories.MaritalListRepository
import com.spark.domain.repositories.ProfileRepository
import com.spark.domain.repositories.ReligionsRepository
import com.spark.domain.usecases.*
import com.spark.getOrAwaitValue
import com.spark.presentation.ui.showprofile.ShowProfileViewModel
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

class EditProfileViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var profileRepository: ProfileRepository

    @Mock
    private lateinit var ethnicityRepository: EthnicityRepository

    @Mock
    private lateinit var religionsRepository: ReligionsRepository

    @Mock
    private lateinit var maritalListRepository: MaritalListRepository


    lateinit var viewModel: EditProfileViewModel

    lateinit var getProfile: GetProfile
    lateinit var getEthnicities: GetEthnicities
    lateinit var getReligions: GetReligions
    lateinit var getMaritalList: GetMaritalList
    lateinit var updateProfile: UpdateProfile
    lateinit var uploadAvatar: UploadAvatar

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getProfile = GetProfile(profileRepository)
        getEthnicities = GetEthnicities(ethnicityRepository)
        getReligions = GetReligions(religionsRepository)
        getMaritalList = GetMaritalList(maritalListRepository)
        updateProfile = UpdateProfile(profileRepository)
        uploadAvatar = UploadAvatar(profileRepository)

        viewModel = EditProfileViewModel(
            getEthnicities = getEthnicities,
            getReligions = getReligions,
            getMaritalList = getMaritalList,
            getProfile = getProfile,
            updateProfile = updateProfile,
            uploadAvatar = uploadAvatar
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Integration test GetEthnics in UpdateProfileVM(Repo,UseCase,VM), return Success`() {
        runBlockingTest {

            val fakeEthnics = Resource.Success(UnitTestUtils.fakeEthnics)

            Mockito.`when`(ethnicityRepository.getEthnicities()).thenReturn(fakeEthnics)

            viewModel.getEnthnics()

            val result = viewModel.ethnicitiesState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertEquals(it, UnitTestUtils.fakeEthnics)
            }
            result.onError {
                assertTrue(false)
            }

        }
    }



}

