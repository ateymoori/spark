package com.spark.data.utils

import com.google.gson.Gson
import com.spark.data.models.ProfileData
import com.spark.data.models.ReligionData
import com.spark.data.utils.GsonUtils.toMutableListByGson
import com.spark.data.utils.GsonUtils.toObjectByGson
import com.spark.presentation.utils.components.base.FileReader
import org.junit.Before
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.junit.Test


class GsonUtilsTest {

    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = GsonUtils.gson
    }

    @Test
    fun `Convert String to Json`() {
        val profileString = FileReader("profile_api.json").content

        val profileDataByGsonUtils = profileString.toObjectByGson<ProfileData>()

        assertEquals(
            profileDataByGsonUtils.displayName,
            "AmirHossein"
        )
    }

    @Test
    fun `Convert ToMutableList by Gson`() {
        val listStr = FileReader("religions_api.json").content
        val dataList = listStr.toMutableListByGson<ReligionData>()

        assertNotEquals(
            dataList.size,
            0
        )
    }

}