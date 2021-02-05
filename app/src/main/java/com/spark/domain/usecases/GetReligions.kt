package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.ReligionsRepository

import javax.inject.Inject

class GetReligions @Inject constructor(
    private val religionRepo: ReligionsRepository
) : UseCase<Resource<List<SingleValueEntity>>, Nothing>() {

    override suspend fun invoke(data: Nothing?): Resource<List<SingleValueEntity>> {
        return religionRepo.getReligions()
    }

}