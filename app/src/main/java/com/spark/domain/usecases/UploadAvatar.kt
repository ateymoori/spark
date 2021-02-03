package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.repositories.ProfileRepository
import com.spark.presentation.utils.components.base.Either
import java.io.File
import javax.inject.Inject

class UploadAvatar @Inject constructor(
    private val profileRepo: ProfileRepository
) : UseCase<Resource<ProfileEntity>, File>() {

    override suspend fun invoke(data: File?): Resource<ProfileEntity> {
        return when (val result = validateForm(data)) {
            is Either.Left -> Resource.Failure.Generic(result.a.message)
            is Either.Right -> profileRepo.uploadAvatar(result.b)
        }
    }

    private fun validateForm(avatar: File?): Either<Exception, File> {
        if (avatar == null)
            return Either.Left(Exception("File is empty"))

        if ((avatar.length()/1024) !in 50..1024)
            return Either.Left(Exception("File size should be between 50KB to 1MB"))

        return Either.Right(avatar)
    }


}