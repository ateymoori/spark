package com.spark.data.repositories

import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.spark.data.api.RestApi
import com.spark.data.models.NewProfileData
import com.spark.data.models.ReligionData
import com.spark.data.models.mapToSingleValue
import com.spark.data.utils.GsonUtils.toListByGson
import com.spark.data.utils.GsonUtils.toObjectByGson
import com.spark.data.utils.Resource
import com.spark.presentation.utils.components.base.FileReader
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response
import org.junit.Assert.*



class TDDRepo {


    @Mock
    private lateinit var api: RestApi

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Test TDD repository are exist or not`(){
        runBlockingTest {
            val list = FileReader("religions_api.json").content.toListByGson<ReligionData>()
            val response = Response.success(list)
            Mockito.`when`(api.getReligions()).thenReturn(response)

            api.getReligions()

            verify(api).getReligions()

            verifyNoMoreInteractions(api)
        }
    }



    //https://run.mocky.io/v3/1d80db20-a0f9-4caa-adba-078edc67a5a9
    @Test
    fun `tdd`(){
        runBlockingTest {
            var data = FileReader("profile.json").content.toObjectByGson<NewProfileData>()
            val response = Response.success(data)
            Mockito.`when`(api.getNewProfile()).thenReturn(response)

            val result = api.getNewProfile()

            verify(api).getNewProfile()

            assertNotNull(data)


        }
    }


}