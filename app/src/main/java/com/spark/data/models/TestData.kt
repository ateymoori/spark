package com.spark.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.spark.domain.models.TestEntity


open class TestData @JvmOverloads constructor(
    @Expose
    @SerializedName("facebook") val facebook: String? = null,
    @Expose
    @SerializedName("id") val id: Int? = null,
    @Expose
    @SerializedName("instagram") val instagram: String? = null,
)
fun TestData?.mapToEntity(): TestEntity =
    TestEntity(
        id = this?.id,
        facebook = this?.facebook,
        instagram = this?.instagram

    )
