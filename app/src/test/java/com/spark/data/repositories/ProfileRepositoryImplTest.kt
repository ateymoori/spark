package com.spark.data.repositories

import com.nhaarman.mockitokotlin2.mock
import com.spark.UnitTestUtils.Companion.fakeProfile
import com.spark.data.api.RestApi
import com.spark.data.models.ProfileData
import com.spark.data.models.ReligionData
import com.spark.data.models.mapToEntity
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.*
import com.spark.data.utils.GsonUtils.toListByGson
import com.spark.data.utils.GsonUtils.toObjectByGson
import com.spark.data.utils.GsonUtils.toStringByGson
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.mapToData
import com.spark.presentation.utils.Constants
import com.spark.presentation.utils.components.base.FileReader
import com.spark.presentation.utils.components.base.SharedPrefUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.File
import java.net.HttpURLConnection

class ProfileRepositoryImplTest {

    @Mock
    private lateinit var restApi: RestApi
    @Mock
    private lateinit var sharedUtils: SharedPrefUtils

    @Mock
    private lateinit var avatarFile: File

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `ProfileRepositoryImpl GET_DATA Profile, success`() {
        runBlockingTest {
            val profile = FileReader("profile_api.json").content.toObjectByGson<ProfileData>()
            val response = Response.success(profile)
            Mockito.`when`(restApi.getProfile()).thenReturn(response)
            val impl = ProfileRepositoryImpl(restApi,sharedUtils)
            assertEquals(
                impl.getProfile(),
                Resource.Success(profile.mapToEntity())
            )
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ProfileRepositoryImpl UPDATE Profile BAD_REQUEST, error`() {
        runBlockingTest {
            val responseBody = Mockito.mock(ResponseBody::class.java)
            val responseError = Response.error<ProfileData>(
                HttpURLConnection.HTTP_BAD_METHOD,
                responseBody
            )

            val profile = FileReader("profile_api.json").content.toObjectByGson<ProfileData>()
            val responseSuccess = Response.success(profile)

            Mockito.`when`(restApi.updateProfile(profile)).thenReturn(responseError)

            val impl = ProfileRepositoryImpl(restApi,sharedUtils)

            impl.updateProfile(profile.mapToEntity())
                .onSuccess {
                    assert(false)
                }.onNetworkError {
                    assert(true)
                }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `ProfileRepositoryImpl UPLOAD AVATAR, success`() {
        runBlocking {
            val requestBody = mock(RequestBody::class.java)
            val file = MultipartBody.Part.create(requestBody)
            val responseSuccess = Response.success(fakeProfile.mapToData())
            Mockito.`when`(restApi.uploadAvatar(file)).thenReturn(responseSuccess)

            val profileRepositoryImpl = ProfileRepositoryImpl(restApi,sharedUtils)
            Mockito.`when`(profileRepositoryImpl.uploadAvatar(avatarFile)).thenReturn(
                Resource.Success(fakeProfile)
            )
            profileRepositoryImpl.uploadAvatar(avatarFile)
                .onSuccess {
                    assert(false)
                }.onNetworkError {
                    assert(false)
                }.onError {
                    assert(true)
                }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ProfileRepositoryImpl Null API, error`() {
        runBlockingTest {
            when (ReligionsRepositoryImpl(restApi).getReligions()) {
                is Resource.Success -> assert(false)
                is Resource.Loading -> assert(false)
                is Resource.Failure.Generic -> assert(true)
                is Resource.Failure.NetworkException -> assert(false)
                is Resource.Failure.UnAuthorized -> assert(false)
            }
        }
    }
}