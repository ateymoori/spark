package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.mapToData
import com.spark.domain.repositories.ProfileRepository
import com.spark.presentation.utils.components.base.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class UpdateProfile @Inject constructor(
    private val profileRepo: ProfileRepository
) : UseCase<Flow<Resource<ProfileEntity>>, ProfileEntity>() {

    override fun getData(data: ProfileEntity?): Flow<Resource<ProfileEntity>> {

        return flow {
            emit(
                when (val result = validateForm(data)) {
                    is Either.Left -> Resource.Failure.Generic(result.a.message)
                    is Either.Right -> profileRepo.updateProfile(result.b)
                }
            )
        }.flowOn(Dispatchers.IO)
    }


    private fun validateForm(data: ProfileEntity?): Either<Exception, ProfileEntity> {
        if (data == null)
            return Either.Left(Exception("Data is null"))

        if (data.displayName?.length !in 2..256)
            return Either.Left(Exception("Display-Name size should be in 2-256"))

        if (data.realName?.length !in 2..256)
            return Either.Left(Exception("Real-Name size should be in 2-256"))

        if (data.birthday.isNullOrEmpty())
            return Either.Left(Exception("Birthday is Mandatory"))

        if (data.gender.isNullOrEmpty())
            return Either.Left(Exception("Gender is Mandatory"))

        if (data.maritalStatus.isNullOrEmpty())
            return Either.Left(Exception("Marital-Status is Mandatory"))

        if (data.locationTitle.isNullOrEmpty())
            return Either.Left(Exception("Location is Mandatory"))

        return Either.Right(data)
    }


}