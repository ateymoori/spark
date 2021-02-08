package com.spark.presentation.utils

class Constants {

    companion object {
        val DISPLAYNAME_MIN_SIZE = 2
        val DISPLAYNAME_MAX_SIZE = 256

        val REALNAME_MIN_SIZE = 2
        val REALNAME_MAX_SIZE = 256

        val HEIGHT_MIN_SIZE = 90
        val HEIGHT_MAX_SIZE = 230

        val DATA_NULL_ERROR = "Data is null"
        val DISPLAY_NAME_ERROR =
            "Display-Name size should be in $DISPLAYNAME_MIN_SIZE-$DISPLAYNAME_MAX_SIZE"
        val REAL_NAME_ERROR = "Real-Name size should be in $REALNAME_MIN_SIZE-$REALNAME_MAX_SIZE"

        val HEIGHT_ERROR = "Height should be in $HEIGHT_MIN_SIZE CM - $HEIGHT_MAX_SIZE CM"
        val BIRTHDAY_NULL_ERROR = "Birthday is Mandatory"
        val BIRTHDAY_WRONG_ERROR = "Birthday format is Wrong"

        val GENDER_NULL_ERROR = "Gender is Mandatory"
        val MARITAL_NULL_ERROR = "Marital-Status is Mandatory"
        val LOCATION_NULL_ERROR = "Location is Mandatory"

        val AVATAR_NULL = "Avatar is null"

        val MIN_AVATAR_SIZE_KB = 50
        val MAX_AVATAR_SIZE_KB = 2048
        val AVATAR_SIZE_ERROR = "File size should be between ${MIN_AVATAR_SIZE_KB}KB to ${MAX_AVATAR_SIZE_KB}KB"
    }
}