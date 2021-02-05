package com.spark.presentation.ui.showprofile

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.repositories.ProfileRepository
import java.io.File

class FakeProfileRepository : ProfileRepository {
      var profile = ProfileEntity(
        displayName = "AmirHossein",
        realName = "AmirHossein Teymoori",
        birthday = "07-03-1989",
        gender = "Male",
        ethnicity = "Iranian",
        religion = "Christianity",
        height = 176,
        maritalStatus = "Married",
        occupation = "Android Developer",
        aboutMe = "I am Amir!",
        locationTitle = "Kuala Lumpur, Malaysia",
        latitude = null,
        longitude = null
    )

    override suspend fun getProfile(): Resource<ProfileEntity> {
        return Resource.Success(profile)
    }

    override suspend fun updateProfile(profile: ProfileEntity): Resource<ProfileEntity> {
        return Resource.Success(profile)
    }

    override suspend fun uploadAvatar(avatar: File): Resource<ProfileEntity> {
        return Resource.Success(profile)
    }
}