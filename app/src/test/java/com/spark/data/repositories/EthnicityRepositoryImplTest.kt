package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.EthnicityData
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.*
import com.spark.data.utils.GsonUtils.toListByGson
import com.spark.presentation.utils.components.base.FileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.net.HttpURLConnection


class EthnicityRepositoryImplTest {

    lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var restApi: RestApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `EthnicityRepositoryImpl GET_DATA , true`() {
        runBlockingTest {
            val list = FileReader("ethnics_api.json").content.toListByGson<EthnicityData>()
            val response = Response.success(list)
            Mockito.`when`(restApi.getEthnicity()).thenReturn(response)
            val impl = EthnicityRepositoryImpl(restApi)
            assertEquals(
                impl.getEthnicities(),
                Resource.Success(list.map { it.mapToSingleValue() })
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `EthnicityRepositoryImpl HTTP_BAD_REQUEST, error`() {
        runBlockingTest {
            val responseBody = mock(ResponseBody::class.java)
            val response = Response.error<List<EthnicityData>>(
                HttpURLConnection.HTTP_BAD_REQUEST,
                responseBody
            )
            Mockito.`when`(restApi.getEthnicity()).thenReturn(response)
            val impl = EthnicityRepositoryImpl(restApi)
            impl.getEthnicities()
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
    fun `EthnicityRepositoryImpl Null API, error`() {
        runBlockingTest {
            when (EthnicityRepositoryImpl(restApi).getEthnicities()) {
                is Resource.Success -> assert(false)
                is Resource.Loading -> assert(false)
                is Resource.Failure.Generic -> assert(true)
                is Resource.Failure.NetworkException -> assert(false)
                is Resource.Failure.UnAuthorized -> assert(false)
            }
        }
    }

    @Test
    fun `EthnicityRepositoryImpl HTTP_METHOD , true`() {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody("{\"name\":\"James\"}")
        mockWebServer.enqueue(mockedResponse)
        val client: OkHttpClient = OkHttpClient.Builder()
            .build()
        val request = Request.Builder()
            .get()
            .url(mockWebServer.url("/"))
            .build()
        client.newCall(request).execute()
        val finalRequest: RecordedRequest? = mockWebServer.takeRequest(
            1,
            java.util.concurrent.TimeUnit.SECONDS
        )
        assertEquals(finalRequest?.method, "GET")
    }
}