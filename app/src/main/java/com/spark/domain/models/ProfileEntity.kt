package com.spark.domain.models

import com.spark.data.models.ProfileData
import java.io.File


data class ProfileEntity(
    val displayName: String? = null,
    val realName: String? = null,
    val picture: String? = null,
    val birthday: String? = null,
    val gender: String? = null,
    val ethnicity: String? = null,
    val religion: String? = null,
    val figure: String? = null,
    val height: Int? = null,
    val maritalStatus: String? = null,
    val occupation: String? = null,
    val aboutMe: String? = null,
    val locationTitle: String? = null,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val updatedAt: String? = null,
    val pictureUpload: File? = null,
)


fun ProfileEntity?.mapToData(): ProfileData =
    ProfileData(
        displayName = this?.displayName,
        realName = this?.realName,
        pictureUpload = this?.pictureUpload,
        birthday = this?.birthday,
        gender = this?.gender,
        ethnicity = this?.ethnicity,
        religion = this?.religion,
        figure = this?.figure,
        height = this?.height,
        maritalStatus = this?.maritalStatus,
        occupation = this?.occupation,
        aboutMe = this?.aboutMe,
        locationTitle = this?.locationTitle,
        latitude = this?.latitude,
        longitude = this?.longitude
    )

