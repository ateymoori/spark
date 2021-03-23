package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.repositories.ProfileRepository
import com.spark.presentation.utils.Constants.Companion.AGE_ERROR
import com.spark.presentation.utils.Constants.Companion.BIRTHDAY_NULL_ERROR
import com.spark.presentation.utils.Constants.Companion.BIRTHDAY_WRONG_ERROR
import com.spark.presentation.utils.Constants.Companion.DATA_NULL_ERROR
import com.spark.presentation.utils.Constants.Companion.DISPLAYNAME_MAX_SIZE
import com.spark.presentation.utils.Constants.Companion.DISPLAYNAME_MIN_SIZE
import com.spark.presentation.utils.Constants.Companion.DISPLAY_NAME_ERROR
import com.spark.presentation.utils.Constants.Companion.GENDER_NULL_ERROR
import com.spark.presentation.utils.Constants.Companion.HEIGHT_ERROR
import com.spark.presentation.utils.Constants.Companion.HEIGHT_MAX_SIZE
import com.spark.presentation.utils.Constants.Companion.HEIGHT_MIN_SIZE
import com.spark.presentation.utils.Constants.Companion.LOCATION_NULL_ERROR
import com.spark.presentation.utils.Constants.Companion.MARITAL_NULL_ERROR
import com.spark.presentation.utils.Constants.Companion.MAX_USER_AGE
import com.spark.presentation.utils.Constants.Companion.MIN_USER_AGE
import com.spark.presentation.utils.Constants.Companion.REALNAME_MAX_SIZE
import com.spark.presentation.utils.Constants.Companion.REALNAME_MIN_SIZE
import com.spark.presentation.utils.Constants.Companion.REAL_NAME_ERROR
import com.spark.presentation.utils.components.base.Either
import com.spark.presentation.utils.components.bottomSheetList.base.TimeHelper
import com.spark.presentation.utils.ext.isValidDateTime

import javax.inject.Inject

class UpdateProfile @Inject constructor(
    private val profileRepo: ProfileRepository
) : UseCase<Resource<ProfileEntity>, ProfileEntity>() {


    override suspend fun invoke(data: ProfileEntity?): Resource<ProfileEntity> {
        return when (val result = validateForm(data)) {
            is Either.Left -> Resource.Failure.Generic(result.a.message)
            is Either.Right -> {
                profileRepo.updateProfile(result.b)
            }
        }
    }


    fun checkGenderIsEditable():Boolean {
        return !profileRepo.genderIsSelected()
    }

    fun validateForm(data: ProfileEntity?): Either<Exception, ProfileEntity> {
        if (data == null)
            return Either.Left(Exception(DATA_NULL_ERROR))

        if (data.displayName?.length !in DISPLAYNAME_MIN_SIZE..DISPLAYNAME_MAX_SIZE)
            return Either.Left(Exception(DISPLAY_NAME_ERROR))

        if (data.realName?.length !in REALNAME_MIN_SIZE..REALNAME_MAX_SIZE)
            return Either.Left(Exception(REAL_NAME_ERROR))

        if (data.height !in HEIGHT_MIN_SIZE..HEIGHT_MAX_SIZE)
            return Either.Left(Exception(HEIGHT_ERROR))

        if (data.gender.isNullOrEmpty())
            return Either.Left(Exception(GENDER_NULL_ERROR))

        if (data.maritalStatus.isNullOrEmpty())
            return Either.Left(Exception(MARITAL_NULL_ERROR))

        if (data.locationTitle.isNullOrEmpty())
            return Either.Left(Exception(LOCATION_NULL_ERROR))

        if (data.birthday.isNullOrEmpty())
            return Either.Left(Exception(BIRTHDAY_NULL_ERROR))

        if (!data.birthday.isValidDateTime())
            return Either.Left(Exception(BIRTHDAY_WRONG_ERROR))

        if (!TimeHelper.ageValidation(data.birthday.toString(), MIN_USER_AGE, MAX_USER_AGE))
            return Either.Left(Exception(AGE_ERROR))

        return Either.Right(data)
    }

}