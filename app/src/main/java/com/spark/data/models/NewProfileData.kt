package com.spark.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewProfileData @JvmOverloads constructor(
    @Expose
    @SerializedName("name") val name: String?=null,
    @Expose
    @SerializedName("age") val age: Int? = null ,
    @Expose
    @SerializedName("location") val location: String? = null

)