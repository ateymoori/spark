package com.spark.domain.usecases


abstract class BaseUseCase<T> {
    abstract suspend fun invoke(): T
}