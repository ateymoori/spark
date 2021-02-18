package com.spark.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.spark.domain.models.SingleValueEntity

open class LocationData @JvmOverloads constructor(
    @Expose
    @SerializedName("lat") val lat: String?=null,
    @Expose
    @SerializedName("lon") val lon: String?=null,
    @Expose
    @SerializedName("city") val city: String?=null
)

fun LocationData?.mapToSingleValue(): SingleValueEntity =
    SingleValueEntity(
        title = this?.city
    )

