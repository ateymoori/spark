package com.spark.domain.repositories

import com.spark.data.models.TestData
import com.spark.data.utils.Resource
import com.spark.domain.models.TestEntity
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    suspend fun getAppSettings(): Resource<TestEntity>
}