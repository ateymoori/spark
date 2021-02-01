package com.spark.domain.usecases

import com.spark.data.repositories.AppSettingRepositoryImpl
import com.spark.data.repositories.EnthnicityRepositoryImpl
import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.data.utils.Resource
import com.spark.data.utils.onError
import com.spark.data.utils.onSuccess
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.models.TestEntity
import com.spark.domain.repositories.ReligionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class GetReligions @Inject constructor(
    private val religionRepo: ReligionsRepository
 ) : UseCase<Flow<Resource<List<SingleValueEntity>>>>() {


    override fun getData(data: Map<String, Any>?): Flow<Resource<List<SingleValueEntity>>> {
        return flow {
            emit(
                religionRepo.getReligions()
            )
       }.retry(2) { e ->
            (e is Exception).also { if (it) delay(1000) }

        }.flowOn(Dispatchers.IO)
    }

}