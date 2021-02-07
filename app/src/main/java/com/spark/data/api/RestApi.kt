package com.spark.data.api

import com.spark.data.models.EthnicityData
import com.spark.data.models.MaritalData
import com.spark.data.models.ProfileData
import com.spark.data.models.ReligionData
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