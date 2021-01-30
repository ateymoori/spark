package com.spark.data.api

import com.spark.data.models.TestData
import retrofit2.Response
import retrofit2.http.GET

interface RestApi {


    @GET("settings")
    suspend fun getSetting(): Response<TestData>


}