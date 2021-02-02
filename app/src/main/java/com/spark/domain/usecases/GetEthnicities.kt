package com.spark.domain.usecases

import com.spark.data.repositories.AppSettingRepositoryImpl
import com.spark.data.repositories.EnthnicityRepositoryImpl
import com.spark.data.utils.Resource
import com.spark.data.utils.onError
import com.spark.data.utils.onSuccess
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.models.TestEntity
import com.spark.domain.repositories.EthnicityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class GetEthnicities @Inject constructor(
    private val ethnicityRepo: EthnicityRepository
) : UseCase<Flow<Resource<List<SingleValueEntity>>> , Nothing >() {


    override fun getData(data: Nothing?): Flow<Resource<List<SingleValueEntity>>> {
        return flow {
            emit(
                ethnicityRepo.getEthnicities()
            )
        }.retry(2) { e ->
            (e is Exception).also { if (it) delay(1000) }

        }.flowOn(Dispatchers.IO)
    }

}