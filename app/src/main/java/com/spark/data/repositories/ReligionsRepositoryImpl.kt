package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.ReligionsRepository
import javax.inject.Inject

class ReligionsRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : ReligionsRepository, BaseDataSource() {


    override suspend fun getReligions(): Resource<List<SingleValueEntity>> {
        getResult { restApi.getReligions() }.onSuccess {
            return Resource.Success(it?.map { it.mapToSingleValue() })
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic(null)
    }

}



