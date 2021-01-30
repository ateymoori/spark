package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.TestEntity
import kotlinx.coroutines.flow.Flow


abstract class UseCase<T>() {
    abstract fun getData(data: Map<String, Any>? = null): T

}