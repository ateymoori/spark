package com.spark

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.spark.domain.models.ProfileEntity
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class UnitTestUtils {
    companion object {
        val fakeProfile = ProfileEntity(
            displayName = "AmirHossein",
            realName = "AmirHossein Teymoori",
            birthday = "07-03-1989",
            gender = "Male",
            ethnicity = "Iranian",
            religion = "Christianity",
            height = 176,
            maritalStatus = "Married",
            occupation = "Android Developer",
            aboutMe = "I am Amir!",
            locationTitle = "Kuala Lumpur, Malaysia",
            latitude = null,
            longitude = null
        )

        val fakeNetworkError = "Network connection error."
    }
}

//extension for unit-test, wait until get liveData result
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
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