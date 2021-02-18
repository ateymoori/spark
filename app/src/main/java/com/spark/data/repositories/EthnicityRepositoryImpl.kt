package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.EthnicityRepository
import javax.inject.Inject

class EthnicityRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : EthnicityRepository, BaseDataSource() {

    override suspend fun getEthnicities(): Resource<List<SingleValueEntity>> {
       getResult { restApi.getEthnicity() }.onSuccess {
            return Resource.Success(it?.map { it.mapToSingleValue() })
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic(null)
    }

}



