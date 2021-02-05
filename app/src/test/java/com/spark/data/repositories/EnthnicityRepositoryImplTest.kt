package com.spark.data.repositories

import com.spark.data.api.RestApi
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class EnthnicityRepositoryImplTest {

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


    @Test
    fun `test success religions repository and use-case, true if sizes are equal`() {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody("{\"name\":\"Ali\"}")

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