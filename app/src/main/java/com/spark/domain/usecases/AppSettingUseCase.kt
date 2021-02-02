package com.spark.domain.usecases

import com.spark.data.repositories.AppSettingRepositoryImpl
import com.spark.data.utils.Resource
import com.spark.data.utils.onError
import com.spark.data.utils.onSuccess
import com.spark.domain.models.TestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.mockito.internal.matchers.Null

import javax.inject.Inject

@Suppress("DeferredResultUnused")
class AppSettingUseCase @Inject constructor(
    private val appSettingRepo: AppSettingRepositoryImpl
) : UseCase<Flow<Resource<TestEntity>>, Nothing>() {


    override fun getData(data: Nothing?): Flow<Resource<TestEntity>> {
        return flow {
            emit(
                appSettingRepo.getAppSettings()
            )
        }.flowOn(Dispatchers.IO)
    }

}