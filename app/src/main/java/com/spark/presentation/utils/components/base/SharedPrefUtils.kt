package com.spark.presentation.utils.components.base

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

open class SharedPrefUtils @Inject constructor(val context: Context) {
    companion object {
        val app_key = "app_key"
    }

    fun putBoolean(key: String, value: Boolean) {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(app_key, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defValue: Boolean):Boolean{
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(app_key, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, defValue)
    }

}