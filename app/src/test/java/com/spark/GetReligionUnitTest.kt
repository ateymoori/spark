package com.spark

import com.spark.data.api.RestApi
import com.spark.data.models.ProfileData
import com.spark.data.models.ReligionData
import com.spark.data.repositories.MaritalListRepositoryImpl
import com.spark.data.repositories.ProfileRepositoryImpl
import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.ReligionsRepository
import com.spark.domain.usecases.GetReligions
import io.mockk.InternalPlatformDsl.toArray
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GetReligionUnitTest {


    val fakeReligionsList = mutableListOf<SingleValueEntity>()
    val networkError = "On network error"
    val religions = listOf("Islam", "Atheism", "Buddhism")

    @Mock
    private lateinit var religionRepository: ReligionsRepository

    @Mock
    private lateinit var restApi: RestApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        religions.map { SingleValueEntity(it) }
    }

    @Test
    fun `test success religions repository and use-case, true if sizes are equal`() {
        runBlocking {
            val fakeList = Resource.Success<List<SingleValueEntity>>(
                fakeReligionsList
            )

            Mockito.`when`(religionRepository.getReligions()).thenReturn(fakeList)

            val getReligions = GetReligions(religionRepository)

            getReligions.invoke().onSuccess {
                assertEquals(it?.size, fakeReligionsList.size)
            }
        }
    }

    @Test
    fun `test error religions repository and use-case, true if output is network error`() {
        runBlocking {
            val value = Resource.Failure.NetworkException(networkError)

            Mockito.`when`(religionRepository.getReligions()).thenReturn(value)

            val getReligions = GetReligions(religionRepository)

            getReligions.invoke().onLoading {
                assertTrue(false)
            }.onSuccess {
                assertTrue(false)
            }.onError {
                assertTrue(false)
            }.onNetworkError {
                assertEquals(it, networkError)
            }
        }
    }


    @Test
    fun `test getReligions repository impl`() {
        runBlocking {

//            val mockResponseBody = Mockito.mock(ProfileData::class.java)
//            val mockResponse = Response.success(mockResponseBody)
//
//            Mockito.`when`(restApi.getProfile()).thenReturn(mockResponse)
//
//            val impl = ProfileRepositoryImpl(restApi)
//            println(restApi.getProfile().isSuccessful)
//
//            impl.getProfile().onNetworkError {
//                println(it)
//            }.onSuccess {
//                println(it)
//            }.onError { println(it) }

//            val feedRepository = Mockito.spy(ProfileRepositoryImpl(restApi))
////            val getReligions = ProfileRepositoryImpl(restApi).getProfile()
//
//            feedRepository.getProfile().onSuccess {
//                assertTrue(true)
//            }.onError {
//                System.out.println(it)
//            }.onNetworkError {
//                assertTrue(false)
//            }
//            assertTrue(false)

        }
    }


}