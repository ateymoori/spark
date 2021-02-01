package com.spark.domain.repositories

import com.spark.data.models.TestData
import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.models.TestEntity
import kotlinx.coroutines.flow.Flow

interface ReligionsRepository {
    suspend fun getReligions(): Resource<List<SingleValueEntity>>
}