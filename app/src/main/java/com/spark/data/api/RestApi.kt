package com.spark.data.api

import com.spark.data.models.EthnicityData
import com.spark.data.models.TestData
import retrofit2.Response
import retrofit2.http.GET

interface RestApi {


    @GET("settings")
    suspend fun getSetting(): Response<TestData>


    @GET("ethnicities")
    suspend fun getEthnicity(): Response<List<EthnicityData>>



    @GET("religions")
    suspend fun getReligions(): Response<List<EthnicityData>>



    @GET("martial")
    suspend fun getMartials(): Response<List<EthnicityData>>


}