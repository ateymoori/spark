package com.spark.domain.models


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
    val updatedAt: String? = null
)
