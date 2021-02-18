package com.spark.domain.usecases

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.LocationRepository
import com.spark.domain.repositories.ReligionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetLocationsTest {

    @Mock
    private lateinit var locationRepository: LocationRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Repository onInvoke`() {
        runBlockingTest {

            //var locationRepository = mock(LocationRepository::class.java)

            val useCase = GetLocations(locationRepository)
            //            val cities = listOf<SingleValueEntity>(SingleValueEntity("test"))
            //     Mockito.`when`(locationRepository.getLocations()).thenReturn(listOf<>())
            useCase.invoke()

            verify(locationRepository, times(1)).getLocations()

        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Check results of UseCase`() {
        runBlockingTest {
            val items = listOf(SingleValueEntity("Berlin"))
            val results = Resource.Success<List<SingleValueEntity>>(
                items
            )
            Mockito.`when`(locationRepository.getLocations()).thenReturn(results)

            var actualItems: List<SingleValueEntity>? = listOf<SingleValueEntity>()

            val useCase = GetLocations(locationRepository)

            useCase.invoke()
                .onSuccess {
                    actualItems = it
                }

            assertEquals(actualItems, items)

        }

    }


}