package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.MaritalListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class GetMaritalList @Inject constructor(
    private val maritalRepo: MaritalListRepository
) : UseCase<Flow<Resource<List<SingleValueEntity>>>, Nothing>() {

    override fun getData(data: Nothing?): Flow<Resource<List<SingleValueEntity>>> {
        return flow {
            emit(
                maritalRepo.getMaritalList()
            )
        }.retry(2) { e ->
            (e is Exception).also { if (it) delay(1000) }

        }.flowOn(Dispatchers.IO)
    }

}