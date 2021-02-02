package com.spark.domain.usecases


abstract class UseCase<T,R> {
    abstract fun getData(data: R? = null): T
}