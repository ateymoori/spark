package com.spark.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.spark.domain.models.ProfileEntity
import java.io.File

open class ProfileData @JvmOverloads constructor(
    @Expose
    @SerializedName("display_name") val displayName: String? = null,
    @Expose
    @SerializedName("real_name") val realName: String? = null,
    @Expose
    @SerializedName("picture") val picture: String? = null,
    @Expose
    @SerializedName("birthday") val birthday: String? = null,
    @Expose
    @SerializedName("gender") val gender: String? = null,
    @Expose
    @SerializedName("ethnicity") val ethnicity: String? = null,
    @Expose
    @SerializedName("religion") val religion: String? = null,
    @Expose
    @SerializedName("height") val height: Int? = null,
    @Expose
    @SerializedName("marital_status") val maritalStatus: String? = null,
    @Expose
    @SerializedName("occupation") val occupation: String? = null,
    @Expose
    @SerializedName("about_me") val aboutMe: String? = null,
    @Expose
    @SerializedName("location_title") val locationTitle: String? = null,
    @Expose
    @SerializedName("latitude") val latitude: Float? = null,
    @Expose
    @SerializedName("longitude") val longitude: Float? = null,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @Expose
    @SerializedName("picture_upload")
    val pictureUpload: File? = null
)

fun ProfileData?.mapToEntity(): ProfileEntity =
    ProfileEntity(
        displayName = this?.displayName,
        realName = this?.realName,
        picture = this?.picture,
        birthday = this?.birthday,
        gender = this?.gender,
        ethnicity = this?.ethnicity,
        religion = this?.religion,
        height = this?.height,
        maritalStatus = this?.maritalStatus,
        occupation = this?.occupation,
        aboutMe = this?.aboutMe,
        locationTitle = this?.locationTitle,
        latitude = this?.latitude,
        longitude = this?.longitude,
        updatedAt = this?.updatedAt
    )
