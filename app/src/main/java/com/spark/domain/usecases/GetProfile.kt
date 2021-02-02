package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class GetProfile @Inject constructor(
    private val profileRepo: ProfileRepository
 ) : UseCase<Flow<Resource<ProfileEntity>>>() {


    override fun getData(data: Map<String, Any>?): Flow<Resource<ProfileEntity>> {
        return flow {
            emit(
                profileRepo.getProfile()
            )
       }.retry(2) { e ->
            (e is Exception).also { if (it) delay(1000) }

        }.flowOn(Dispatchers.IO)
    }

}