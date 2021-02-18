package com.spark.domain.usecases

import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.LocationRepository
import com.spark.domain.repositories.ReligionsRepository
import javax.inject.Inject

class GetLocations @Inject constructor(
    private val locationRepo: LocationRepository
) : BaseUseCase<Resource<List<SingleValueEntity>>>() {

    override suspend fun invoke(): Resource<List<SingleValueEntity>> {
        return locationRepo.getLocations()
    }

}