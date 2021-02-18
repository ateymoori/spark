package com.spark.presentation.utils.components.base

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

open class AssetsUtils @Inject constructor(val context: Context) {

    fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.getAssets().open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


}