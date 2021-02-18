package com.spark.domain.usecases


abstract class UseCase<T, R> {
    abstract suspend fun invoke(data: R? = null): T
}