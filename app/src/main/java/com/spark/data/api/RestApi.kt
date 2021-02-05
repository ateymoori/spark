package com.spark.data.api

import com.spark.data.models.EthnicityData
import com.spark.data.models.MaritalData
import com.spark.data.models.ProfileData
import com.spark.data.models.ReligionData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @GET("ethnicities")
    suspend fun getEthnicity(): Response<List<EthnicityData>>


    @GET("religions")
    suspend fun getReligions(): Response<List<ReligionData>>


    @GET("maritals")
    suspend fun getMaritalList(): Response<List<MaritalData>>


    @GET("profile")
    suspend fun getProfile(): Response<ProfileData>

    @POST("profile")
    suspend fun updateProfile(
        @Body profile: ProfileData
    ): Response<ProfileData>


    @Multipart
    @POST("avatar")
    suspend fun uploadAvatar(
        @Part avatar: MultipartBody.Part? = null
    ): Response<ProfileData>


}