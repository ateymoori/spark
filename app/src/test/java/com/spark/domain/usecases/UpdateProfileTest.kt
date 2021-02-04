package com.spark.domain.usecases

import com.spark.UnitTestUtils
import com.spark.data.utils.*
import com.spark.domain.repositories.ProfileRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UpdateProfileTest{

    @Mock
    private lateinit var profileRepository: ProfileRepository

    private var profile = UnitTestUtils.fakeProfile.copy()

    lateinit var updateProfileUC: UpdateProfile

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        updateProfileUC = UpdateProfile(profileRepository)
    }

    //DisplayName
    @Test
    fun `test validation DisplayName EMPTY, assert onError`() {
        runBlocking {
            profile.displayName = ""
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Display-Name size should be in 2-256")
            }
        }
    }

    @Test
    fun `test validation DisplayName SHORT, assert onError`() {
        runBlocking {
            profile.displayName = "a"
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Display-Name size should be in 2-256")
            }
        }
    }

    @Test
    fun `test validation DisplayName LONG, assert onError`() {
        runBlocking {
            profile.displayName = "a".repeat(300)
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Display-Name size should be in 2-256")
            }
        }
    }

    @Test
    fun `test validation DisplayName CORRECT, assert OnSuccess`() {
        runBlocking {
            profile.displayName = "AmirHossein"
            val either = updateProfileUC.validateForm(profile)
            //left=exception, right=true
            if (either.isLeft) {
                assertTrue(false)
            } else if (either.isRight) {
                assertTrue(true)
            }

        }
    }


    //RealName
    @Test
    fun `test validation RealName EMPTY, assert onError`() {
        runBlocking {
            profile.realName = ""
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Real-Name size should be in 2-256")
            }
        }
    }

    @Test
    fun `test validation RealName SHORT, assert onError`() {
        runBlocking {
            profile.realName = "a"
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Real-Name size should be in 2-256")
            }
        }
    }

    @Test
    fun `test validation RealName LONG, assert onError`() {
        runBlocking {
            profile.realName = "a".repeat(300)
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Real-Name size should be in 2-256")
            }
        }
    }

    @Test
    fun `test validation RealName CORRECT, assert OnSuccess`() {
        runBlocking {
            profile.realName = "AmirHossein"
            val either = updateProfileUC.validateForm(profile)
            //left=exception, right=true
            if (either.isLeft) {
                assertTrue(false)
            } else if (either.isRight) {
                assertTrue(true)
            }

        }
    }

    //Gender
    @Test
    fun `test validation Gender EMPTY, assert onError`() {
        runBlocking {
            profile.gender = ""
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Gender is Mandatory")
            }
        }
    }

    @Test
    fun `test validation Gender Female, assert onSuccess`() {
        runBlocking {
            profile.gender = "Female"
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(true)
            }.onError {
                assertTrue(false)
            }
        }
    }

    @Test
    fun `test validation Height 80CM, assert onError`() {
        runBlocking {
            profile.height = 80
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, "Height should be in 90CM-230CM")
            }
        }
    }


}