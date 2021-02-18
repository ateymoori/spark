package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.EthnicityRepository

import javax.inject.Inject

class GetEthnicities @Inject constructor(
    private val ethnicityRepo: EthnicityRepository
) : UseCase<Resource<List<SingleValueEntity>> , Nothing >() {

    override suspend fun invoke(data: Nothing?): Resource<List<SingleValueEntity>> {
        return ethnicityRepo.getEthnicities()
    }

}