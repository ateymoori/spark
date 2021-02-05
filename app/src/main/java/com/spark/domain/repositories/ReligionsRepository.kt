package com.spark.domain.repositories

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity

interface ReligionsRepository {
    suspend fun getReligions(): Resource<List<SingleValueEntity>>
}