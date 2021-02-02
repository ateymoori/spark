package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.MaritalListRepository
import javax.inject.Inject

class MaritalListRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : MaritalListRepository, BaseDataSource() {


    override suspend fun getMaritalList(): Resource<List<SingleValueEntity>> {
        getResult { restApi.getMaritalList() }.onSuccess {
            return Resource.Success(it?.map {it.mapToSingleValue()  } )
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic("Error")
    }

}



