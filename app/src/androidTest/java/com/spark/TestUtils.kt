package com.spark

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.SingleValueEntity
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class FileReader(path: String) {
    val content: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}


//extension for unit-test, wait until get liveData result
fun <T> LiveData<T>.getOrAwaitValueAndroidTest(
    time: Long = 4,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValueAndroidTest.removeObserver(this)
        }
    }
    this.observeForever(observer)
    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}