package com.spark.data.api

import com.spark.data.models.EthnicityData
import com.spark.data.models.ProfileData
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


    @GET("maritals")
    suspend fun getMaritalList(): Response<List<EthnicityData>>


    @GET("profile")
    suspend fun getProfile(): Response<ProfileData>


}