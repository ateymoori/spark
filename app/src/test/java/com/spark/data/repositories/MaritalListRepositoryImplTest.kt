package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.MaritalData
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

class MaritalListRepositoryImplTest {

    @Mock
    private lateinit var restApi: RestApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `MaritalListRepositoryImpl GET_DATA , success`() {
        runBlockingTest {
            val list = FileReader("maritals_api.json").content.toListByGson<MaritalData>()
            val response = Response.success(list)
            Mockito.`when`(restApi.getMaritalList()).thenReturn(response)
            val impl = MaritalListRepositoryImpl(restApi)
            assertEquals(
                impl.getMaritalList(),
                Resource.Success(list.map { it.mapToSingleValue() })
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `MaritalListRepositoryImpl HTTP_BAD_METHOD, error`() {
        runBlockingTest {
            val responseBody = Mockito.mock(ResponseBody::class.java)
            val response = Response.error<List<MaritalData>>(
                HttpURLConnection.HTTP_BAD_METHOD,
                responseBody
            )
            Mockito.`when`(restApi.getMaritalList()).thenReturn(response)
            val impl = MaritalListRepositoryImpl(restApi)
            impl.getMaritalList()
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
    fun `MaritalListRepositoryImpl Null API, error`() {
        runBlockingTest {
            when (MaritalListRepositoryImpl(restApi).getMaritalList()) {
                is Resource.Success -> assert(false)
                is Resource.Loading -> assert(false)
                is Resource.Failure.Generic -> assert(true)
                is Resource.Failure.NetworkException -> assert(false)
                is Resource.Failure.UnAuthorized -> assert(false)
            }
        }
    }
}