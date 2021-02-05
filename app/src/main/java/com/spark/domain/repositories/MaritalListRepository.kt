package com.spark.domain.repositories

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity

interface MaritalListRepository {
    suspend fun getMaritalList(): Resource<List<SingleValueEntity>>
}