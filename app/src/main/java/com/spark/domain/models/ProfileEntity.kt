package com.spark.domain.models

import com.spark.data.models.ProfileData
import java.io.File


data class ProfileEntity(
    var displayName: String? = null,
    var realName: String? = null,
    var picture: String? = null,
    var birthday: String? = null,
    var gender: String? = null,
    var ethnicity: String? = null,
    var religion: String? = null,
    var height: Int? = null,
    var maritalStatus: String? = null,
    var occupation: String? = null,
    var aboutMe: String? = null,
    var locationTitle: String? = null,
    var latitude: Float? = null,
    var longitude: Float? = null,
    var updatedAt: String? = null
)


fun ProfileEntity?.mapToData(): ProfileData =
    ProfileData(
        displayName = this?.displayName,
        realName = this?.realName,
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
        longitude = this?.longitude
    )

