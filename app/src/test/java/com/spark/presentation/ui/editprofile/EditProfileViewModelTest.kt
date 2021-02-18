package com.spark.presentation.ui.editprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.spark.UnitTestUtils
import com.spark.data.utils.*
import com.spark.domain.repositories.*
import com.spark.domain.usecases.*
import com.spark.getOrAwaitValue
import com.spark.presentation.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.File


class EditProfileViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var profileRepository: ProfileRepository

    @Mock
    private lateinit var ethnicityRepository: EthnicityRepository

    @Mock
    private lateinit var locationsRepository: LocationRepository

    @Mock
    private lateinit var religionsRepository: ReligionsRepository

    @Mock
    private lateinit var maritalListRepository: MaritalListRepository

    @Mock
    private lateinit var avatarFile: File

    lateinit var viewModel: EditProfileViewModel

    lateinit var getProfile: GetProfile
    lateinit var getEthnicities: GetEthnicities
    lateinit var getReligions: GetReligions
    lateinit var getMaritalList: GetMaritalList
    lateinit var updateProfile: UpdateProfile
    lateinit var uploadAvatar: UploadAvatar
    lateinit var getLocations: GetLocations

    private val fakeProfile = Resource.Success(UnitTestUtils.fakeProfile)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getProfile = GetProfile(profileRepository)
        getEthnicities = GetEthnicities(ethnicityRepository)
        getReligions = GetReligions(religionsRepository)
        getMaritalList = GetMaritalList(maritalListRepository)
        updateProfile = UpdateProfile(profileRepository)
        uploadAvatar = UploadAvatar(profileRepository)
        getLocations = GetLocations(locationsRepository)

        viewModel = EditProfileViewModel(
            getEthnicities = getEthnicities,
            getReligions = getReligions,
            getMaritalList = getMaritalList,
            getProfile = getProfile,
            updateProfile = updateProfile,
            uploadAvatar = uploadAvatar,
            getLocations = getLocations
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GetEthnics in UpdateProfileVM(Repo,UseCase,VM), return Success`() {
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

    @ExperimentalCoroutinesApi
    @Test
    fun `GetReligions in UpdateProfileVM(Repo,UseCase,VM), return Success`() {
        runBlockingTest {
            val fakeReligions = Resource.Success(UnitTestUtils.fakeReligions)

            Mockito.`when`(religionsRepository.getReligions()).thenReturn(fakeReligions)

            viewModel.getReligions()

            val result = viewModel.religionsState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertEquals(it, UnitTestUtils.fakeReligions)
            }
            result.onError {
                assertTrue(false)
            }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `GetMarital in UpdateProfileVM(Repo,UseCase,VM), return Success`() {
        runBlockingTest {
            val fakeMarital = Resource.Success(UnitTestUtils.fakeMarital)

            Mockito.`when`(maritalListRepository.getMaritalList()).thenReturn(fakeMarital)

            viewModel.getMaritalList()

            val result = viewModel.maritalListState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertEquals(it, UnitTestUtils.fakeMarital)
            }
            result.onError {
                assertTrue(false)
            }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `GetProfile(Repo,UseCase,VM), return Success`() {
        runBlockingTest {

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
    fun `SaveProfile(Repo,UseCase,VM), return Success`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(UnitTestUtils.fakeProfile)
            val result = viewModel.updateProfileState.getOrAwaitValue()
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
    fun `SaveProfile with Null Data (Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(null)

            val result = viewModel.updateProfileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.DATA_NULL_ERROR)
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `SaveProfile with Corrupted Data (Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(UnitTestUtils.fakeProfile.copy(realName = ""))

            val result = viewModel.updateProfileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.REAL_NAME_ERROR)
            }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `SaveProfile with Null Birthday (Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(UnitTestUtils.fakeProfile.copy(birthday = ""))

            val result = viewModel.updateProfileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.BIRTHDAY_NULL_ERROR)
            }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `SaveProfile with Wrong Birthday (Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(UnitTestUtils.fakeProfile.copy(birthday = "11-13-335"))

            val result = viewModel.updateProfileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.BIRTHDAY_WRONG_ERROR)
            }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `SaveProfile with Null Marital (Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(UnitTestUtils.fakeProfile.copy(maritalStatus = ""))

            val result = viewModel.updateProfileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.MARITAL_NULL_ERROR)
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `SaveProfile with Null Location (Repo,UseCase,VM), return Error`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.updateProfile(UnitTestUtils.fakeProfile))
                .thenReturn(fakeProfile)

            viewModel.updateProfile(UnitTestUtils.fakeProfile.copy(locationTitle = ""))

            val result = viewModel.updateProfileState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.LOCATION_NULL_ERROR)
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `OnViewResumed(Repo,UseCase,VM), return Success`() {
        runBlockingTest {

            Mockito.`when`(profileRepository.getProfile()).thenReturn(fakeProfile)

            viewModel.getData()

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
    fun `Avatar upload with null file, Assert onError`() {
        runBlockingTest {

            viewModel.uploadAvatar(null)

            val result = viewModel.uploadAvatarState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.AVATAR_NULL)
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Avatar upload with low size, Assert onError`() {
        runBlockingTest {

            `when`(avatarFile.length()).thenReturn((Constants.MIN_AVATAR_SIZE_KB - 10).toLong())

            `when`(profileRepository.uploadAvatar(avatarFile)).thenReturn(fakeProfile)

            viewModel.uploadAvatar(avatarFile)

            val result = viewModel.uploadAvatarState.getOrAwaitValue()
            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(false)
            }
            result.onError {
                assertEquals(it, Constants.AVATAR_SIZE_ERROR)
            }

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Avatar upload with correct size, Assert onSuccess`() {
        runBlockingTest {

            `when`(avatarFile.length()).thenReturn(((Constants.MIN_AVATAR_SIZE_KB + 10) * 1024).toLong())

            `when`(profileRepository.uploadAvatar(avatarFile)).thenReturn(fakeProfile)

            viewModel.uploadAvatar(avatarFile)

            val result = viewModel.uploadAvatarState.getOrAwaitValue()

            result.onLoading {
                assertTrue(false)
            }
            result.onSuccess {
                assertTrue(true)
            }
            result.onError {
                assertTrue(false)
            }

        }
    }


}

