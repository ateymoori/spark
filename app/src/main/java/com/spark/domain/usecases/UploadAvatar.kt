package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.repositories.ProfileRepository
import com.spark.presentation.utils.Constants
import com.spark.presentation.utils.Constants.Companion.MAX_AVATAR_SIZE_KB
import com.spark.presentation.utils.Constants.Companion.MIN_AVATAR_SIZE_KB
import com.spark.presentation.utils.components.base.Either
import java.io.Console
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
            return Either.Left(Exception(Constants.AVATAR_NULL))

        if ((avatar.length() / 1024) !in MIN_AVATAR_SIZE_KB..MAX_AVATAR_SIZE_KB)
            return Either.Left(Exception(Constants.AVATAR_SIZE_ERROR))

        return Either.Right(avatar)
    }


}