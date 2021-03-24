package com.spark.domain.repositories

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import java.io.File

interface ProfileRepository {
    suspend fun getProfile(): Resource<ProfileEntity>
    suspend fun updateProfile(profile: ProfileEntity): Resource<ProfileEntity>
    suspend fun uploadAvatar(avatar: File): Resource<ProfileEntity>
    fun genderIsSelected():Boolean
}