package com.spark.data.api

import com.spark.data.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    companion object {
        private const val API_VERSION = "v1"
    }

    @GET("$API_VERSION/ethnicities")
    suspend fun getEthnicity(): Response<List<EthnicityData>>

    @GET("$API_VERSION/religions")
    suspend fun getReligions(): Response<List<ReligionData>>

    @GET("$API_VERSION/maritals")
    suspend fun getMaritalList(): Response<List<MaritalData>>

    @GET("$API_VERSION/profile")
    suspend fun getProfile(): Response<ProfileData>

    @GET("https://run.mocky.io/v3/1d80db20-a0f9-4caa-adba-078edc67a5a9")
    suspend fun getNewProfile(): Response<NewProfileData>

    @POST("$API_VERSION/profile")
    suspend fun updateProfile(
        @Body profile: ProfileData
    ): Response<ProfileData>

    @Multipart
    @POST("$API_VERSION/avatar")
    suspend fun uploadAvatar(
        @Part avatar: MultipartBody.Part? = null
    ): Response<ProfileData>

}