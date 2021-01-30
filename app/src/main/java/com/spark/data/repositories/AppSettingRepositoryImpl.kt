package com.spark.data.repositories

import com.spark.data.api.RestApi
import com.spark.data.models.mapToEntity
import com.spark.data.utils.*
import com.spark.domain.models.TestEntity
import com.spark.domain.repositories.AppSettingsRepository
import javax.inject.Inject

class AppSettingRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : AppSettingsRepository, BaseDataSource() {

    override suspend fun getAppSettings(): Resource<TestEntity> {
        getResult { restApi.getSetting() }.onSuccess {
            return Resource.Success(it.mapToEntity())
        }.onError {
            return Resource.Failure.Generic(it)
        }
        return Resource.Failure.Generic("Error")
    }

}



