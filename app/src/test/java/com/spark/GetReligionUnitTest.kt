package com.spark

import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.repositories.ReligionsRepository
import com.spark.domain.usecases.GetReligions
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetReligionUnitTest {


//    val fakeReligionsList = mutableListOf<SingleValueEntity>()
//
//
//    @Mock
//    private lateinit var religionRepository: ReligionsRepository
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//
//        fakeReligionsList.add(SingleValueEntity("Islam"))
//        fakeReligionsList.add(SingleValueEntity("Atheism"))
//        fakeReligionsList.add(SingleValueEntity("Buddhism"))
//    }
//
//    @Test
//    fun getReligions() {
//        runBlocking {
//            val fakeList = Resource.Success<List<SingleValueEntity>>(
//                fakeReligionsList
//            )
//
//            Mockito.`when`(religionRepository.getReligions()).thenReturn(fakeList)
//
//            val flow = flow {
//                emit(Resource.Loading<SingleValueEntity>("loading"))
//                delay(100)
//                emit(fakeList)
//            }
//
//
//            flow.collect { data ->
//                data.onSuccess {
//                    assertEquals(data, fakeList)
//                }
//                data.onLoading {
//                    assertEquals(data, 9)
//                }
//            }
//
//            val getReligions = GetReligions(religionRepository)
//            getReligions.getData().collect { data ->
//                data.onSuccess {
//                    assertEquals(data, religionRepository)
//                }
//                data.onError {
//                    assertEquals(data, 88)
//                }
//            }
//
//        }
//    }

//    @Test
//    fun testFlow() {
//        runBlocking {
//
//            val flow = flow {
//                emit(Resource.Loading<SingleValueEntity>("loading"))
//                delay(10)
//                emit(Resource.Success<List<SingleValueEntity>>())
//            }
//
//
//            val aaa = Resource.Success<List<SingleValueEntity>>(
//                mutableListOf(
//                    SingleValueEntity("")
//                )
//            )
//            val religionRepository = Mockito.mock(ReligionsRepository::class.java)
//
//            Mockito.`when`(religionRepository.getReligions()).thenReturn(aaa)
//
//            val getReligions = GetReligions(religionRepository)
//            getReligions.getData().collect {
//                it.onSuccess {
//                    assertEquals(it?.size, 1)
//                }
//            }
//        }
//    }

}