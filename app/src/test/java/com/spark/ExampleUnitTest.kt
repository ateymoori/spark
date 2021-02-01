package com.spark

import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.data.utils.Resource
import com.spark.data.utils.onSuccess
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.ReligionsRepository
import com.spark.domain.usecases.GetReligions
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun getReligions() {
        runBlocking {
            val aaa = Resource.Success<List<SingleValueEntity>>(
                mutableListOf(
                    SingleValueEntity("")
                )
            )

            val religionRepository = Mockito.mock(ReligionsRepository::class.java)

            Mockito.`when`(religionRepository.getReligions()).thenReturn(aaa)

            val getReligions = GetReligions(religionRepository)
            getReligions.getData().collect {
                it.onSuccess {
                    assertEquals(it?.size , 1)
                }
            }

        }
//           .assertValue { results -> results.size == 10 }
//            .assertComplete()

    }

//
//        val getImages = GetImages(TestTransformer(), imageRepository)
//
//        getImages.observable().test()
//            .assertValue { results -> results.size == 10 }
//            .assertComplete()


}