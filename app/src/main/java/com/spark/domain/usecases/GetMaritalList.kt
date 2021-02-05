package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.MaritalListRepository

import javax.inject.Inject

class GetMaritalList @Inject constructor(
    private val maritalRepo: MaritalListRepository
) : UseCase<Resource<List<SingleValueEntity>>, Nothing>() {

    override suspend fun invoke(data: Nothing?): Resource<List<SingleValueEntity>> {
        return maritalRepo.getMaritalList()

    }

}