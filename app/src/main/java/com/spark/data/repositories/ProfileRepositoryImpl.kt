package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.mapToEntity
import com.spark.data.utils.*
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.mapToData
import com.spark.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : ProfileRepository, BaseDataSource() {

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
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic("Error in Repo")
    }

}



