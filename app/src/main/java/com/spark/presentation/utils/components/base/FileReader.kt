package com.spark.presentation.utils.components.base

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.spark.presentation.utils.MyApplication
import java.io.IOException
import java.io.InputStreamReader

class FileReader (path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}