package com.spark.domain.usecases

import com.spark.data.repositories.AppSettingRepositoryImpl
import com.spark.data.repositories.EnthnicityRepositoryImpl
import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.data.utils.Resource
import com.spark.data.utils.onError
import com.spark.data.utils.onSuccess
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.models.TestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class GetReligions @Inject constructor(
    private val religionRepo: ReligionsRepositoryImpl
 ) : UseCase<Flow<Resource<List<SingleValueEntity>>>>() {


    override fun getData(data: Map<String, Any>?): Flow<Resource<List<SingleValueEntity>>> {
        return flow {
            emit(
                religionRepo.getReligions()
            )
       }.flowOn(Dispatchers.IO)
    }

}