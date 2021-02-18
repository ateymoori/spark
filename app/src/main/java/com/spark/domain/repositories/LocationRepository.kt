package com.spark.domain.repositories

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity

interface LocationRepository {
    suspend fun getLocations(): Resource<List<SingleValueEntity>>
}