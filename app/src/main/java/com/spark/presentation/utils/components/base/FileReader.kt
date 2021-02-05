package com.spark.presentation.utils.components.base

import java.io.InputStreamReader

class FileReader (path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}