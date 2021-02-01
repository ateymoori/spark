package com.spark.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.models.TestEntity


open class ReligionData @JvmOverloads constructor(
    @Expose
    @SerializedName("title") val title: String?=null,
    @Expose
    @SerializedName("id") val id: Int? = null
)

fun ReligionData?.mapToSingleValue(): SingleValueEntity =
    SingleValueEntity(
        title = this?.title
    )