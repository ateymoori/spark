package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.repositories.ProfileRepository

import javax.inject.Inject

class GetProfile @Inject constructor(
    private val profileRepo: ProfileRepository
) : UseCase<Resource<ProfileEntity>, Nothing>() {

    override suspend fun invoke(data: Nothing?): Resource<ProfileEntity> {
        return profileRepo.getProfile()

    }

}