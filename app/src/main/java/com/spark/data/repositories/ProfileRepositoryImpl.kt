package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.mapToEntity
import com.spark.data.utils.*
import com.spark.data.utils.GsonUtils.toStringByGson
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.mapToData
import com.spark.domain.repositories.ProfileRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : ProfileRepository, BaseDataSource() {

    private lateinit var multipartBody: MultipartBody.Part

    override suspend fun getProfile(): Resource<ProfileEntity> {
        getResult { restApi.getProfile() }.onSuccess {
            return Resource.Success(it.mapToEntity())
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic("Error in Repo")
    }

    override suspend fun updateProfile(profile: ProfileEntity): Resource<ProfileEntity> {
        getResult { restApi.updateProfile(profile.mapToData()) }.onSuccess {
            return Resource.Success(it.mapToEntity())
        }.onError {
            return Resource.Failure.NetworkException(it)
        }
        return Resource.Failure.Generic("Error in Repo")
    }

    override suspend fun uploadAvatar(avatar: File): Resource<ProfileEntity> {
        val requestFile =
            avatar.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        multipartBody =
            MultipartBody.Part.createFormData("avatar", avatar.name, requestFile)

        getResult { restApi.uploadAvatar(multipartBody) }.onSuccess {
            return Resource.Success(it.mapToEntity())
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic("Error in Repo")
    }

}



