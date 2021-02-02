package com.spark.domain.repositories

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity

interface ProfileRepository {
    suspend fun getProfile(): Resource<ProfileEntity>
}