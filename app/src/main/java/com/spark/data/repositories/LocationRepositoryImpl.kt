package com.spark.data.repositories

import com.google.android.gms.common.util.JsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spark.data.api.RestApi
import com.spark.data.models.LocationData
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.BaseDataSource
import com.spark.data.utils.GsonUtils.toListByGson
import com.spark.data.utils.Resource
import com.spark.data.utils.log
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.LocationRepository
import com.spark.presentation.utils.components.base.AssetsUtils
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val assetsUtil: AssetsUtils
) : LocationRepository, BaseDataSource() {

    override suspend fun getLocations(): Resource<List<SingleValueEntity>> {
        val locationsJS = assetsUtil.loadJSONFromAsset("locations.json")

        val locations = locationsJS?.toListByGson<LocationData>()?.map {  it.mapToSingleValue()}

        return Resource.Success<List<SingleValueEntity>>(locations)


    }


}