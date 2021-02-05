package com.spark.domain.usecases

import com.spark.UnitTestUtils
import com.spark.data.utils.*
import com.spark.domain.repositories.ProfileRepository
import com.spark.presentation.utils.Constants
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UpdateProfileTest {

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
                assertEquals(it, Constants.DISPLAY_NAME_ERROR)
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
                assertEquals(it, Constants.DISPLAY_NAME_ERROR)
            }
        }
    }

    @Test
    fun `test validation DisplayName LONG, assert onError`() {
        runBlocking {
            profile.displayName = "a".repeat(Constants.DISPLAYNAME_MAX_SIZE + 1)
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, Constants.DISPLAY_NAME_ERROR)
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
                assertEquals(it, Constants.REAL_NAME_ERROR)
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
                assertEquals(it, Constants.REAL_NAME_ERROR)
            }
        }
    }

    @Test
    fun `test validation RealName LONG, assert onError`() {
        runBlocking {
            profile.realName = "a".repeat(Constants.REALNAME_MAX_SIZE + 1)
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, Constants.REAL_NAME_ERROR)
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
                assertEquals(it, Constants.GENDER_NULL_ERROR)
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
            profile.height = Constants.HEIGHT_MIN_SIZE - 10
            updateProfileUC.invoke(profile).onSuccess {
                assertTrue(false)
            }.onError {
                assertEquals(it, Constants.HEIGHT_ERROR)
            }
        }
    }



}