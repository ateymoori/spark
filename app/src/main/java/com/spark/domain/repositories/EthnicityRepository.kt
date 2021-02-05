package com.spark.domain.repositories

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity

interface EthnicityRepository {
    suspend fun getEthnicities(): Resource<List<SingleValueEntity>>
}