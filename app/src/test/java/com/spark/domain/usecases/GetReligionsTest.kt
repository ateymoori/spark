package com.spark.domain.usecases

import com.spark.UnitTestUtils
import com.spark.data.api.RestApi
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.ReligionsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetReligionsTest {


    val fakeReligionsList = mutableListOf<SingleValueEntity>()

    @Mock
    private lateinit var religionRepository: ReligionsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test success religions repository and use-case, true if sizes are equal`() {
        runBlocking {
            val fakeList = Resource.Success<List<SingleValueEntity>>(
                fakeReligionsList
            )

            Mockito.`when`(religionRepository.getReligions()).thenReturn(fakeList)

            val getReligions = GetReligions(religionRepository)

            getReligions.invoke().onSuccess {
                assertEquals(it?.size, fakeReligionsList.size)
            }
        }
    }

    @Test
    fun `test error religions repository and use-case, true if output is network error`() {
        runBlocking {
            val value = Resource.Failure.NetworkException(UnitTestUtils.fakeNetworkError)

            Mockito.`when`(religionRepository.getReligions()).thenReturn(value)

            val getReligions = GetReligions(religionRepository)

            getReligions.invoke().onLoading {
                assertTrue(false)
            }.onSuccess {
                assertTrue(false)
            }.onError {
                assertTrue(false)
            }.onNetworkError {
                assertEquals(it, UnitTestUtils.fakeNetworkError)
            }
        }
    }

}