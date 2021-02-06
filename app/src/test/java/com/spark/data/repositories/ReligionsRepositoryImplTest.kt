package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.MaritalData
import com.spark.data.models.ReligionData
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.GsonUtils.toListByGson
import com.spark.data.utils.Resource
import com.spark.data.utils.onError
import com.spark.data.utils.onSuccess
import com.spark.presentation.utils.components.base.FileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.net.HttpURLConnection

class ReligionsRepositoryImplTest{

    @Mock
    private lateinit var restApi: RestApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `ReligionsRepositoryImplTest GET_DATA , success`() {
        runBlockingTest {
            val list = FileReader("religions_api.json").content.toListByGson<ReligionData>()
            val response = Response.success(list)
            Mockito.`when`(restApi.getReligions()).thenReturn(response)
            val impl = ReligionsRepositoryImpl(restApi)
            assertEquals(
                impl.getReligions(),
                Resource.Success(list.map { it.mapToSingleValue() })
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `ReligionsRepositoryImplTest HTTP_BAD_METHOD, error`() {
        runBlockingTest {
            val responseBody = Mockito.mock(ResponseBody::class.java)
            val response = Response.error<List<ReligionData>>(
                HttpURLConnection.HTTP_BAD_METHOD,
                responseBody
            )
            Mockito.`when`(restApi.getReligions()).thenReturn(response)
            val impl = ReligionsRepositoryImpl(restApi)
            impl.getReligions()
                .onSuccess {
                    assert(false)
                }
                .onError {
                    assert(true)
                }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ReligionsRepositoryImplTest Null API, error`() {
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