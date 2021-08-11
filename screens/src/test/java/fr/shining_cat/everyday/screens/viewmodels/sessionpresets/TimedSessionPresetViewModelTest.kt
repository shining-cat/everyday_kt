/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.screens.viewmodels.sessionpresets

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.testutils.MainCoroutineScopeRule
import fr.shining_cat.everyday.testutils.extensions.getValueForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TimedSessionPresetViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockCreateSessionPresetUseCase: CreateSessionPresetUseCase

    @MockK
    private lateinit var mockUpdateSessionPresetUseCase: UpdateSessionPresetUseCase

    @MockK
    private lateinit var mockDeleteSessionPresetUseCase: DeleteSessionPresetUseCase

    @MockK
    private lateinit var mockContext: Context

    private lateinit var timedSessionPresetViewModel: TimedSessionPresetViewModel

    private val deviceDefaultRingtoneUriString = "device Default Ringtone Uri String"
    private val deviceDefaultRingtoneName = "device Default Ringtone Name"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery {mockLogger.d(any(), any())} answers {}
        coEvery {mockLogger.e(any(), any())} answers {}

        timedSessionPresetViewModel = TimedSessionPresetViewModel(
            mockCreateSessionPresetUseCase, mockUpdateSessionPresetUseCase, mockDeleteSessionPresetUseCase, mockLogger
        )
    }
    
    /////////////////////////
    // tests on init
    @Test
    fun `test init for creation`() {
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    @Test
    fun `test init for edition`() {
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 456L,
        )
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, inputSessionPreset, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        Assert.assertEquals(inputSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on isSessionPresetValid
    @Test
    fun `test verifyPresetValidity TRUE`() {
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 456L
        )
        //
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        Assert.assertEquals(true, timedSessionPresetViewModel.verifyPresetValidity())
    }

    @Test
    fun `test verifyPresetValidity duration is less than or equal to 0`() {
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 0L
        )
        //
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        Assert.assertEquals(false, timedSessionPresetViewModel.verifyPresetValidity())
        Assert.assertEquals(false, timedSessionPresetViewModel.invalidDurationLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on saveSessionPreset
    @Test
    fun `test saveSessionPreset new sessionPreset success`() {
        coEvery {mockCreateSessionPresetUseCase.execute(any())} returns Result.Success(45L)
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = 456L
        )
        runBlocking {
            timedSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockCreateSessionPresetUseCase.execute(inputSessionPreset)}
        Assert.assertEquals(true, timedSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset new sessionPreset failure`() {
        coEvery {mockCreateSessionPresetUseCase.execute(any())} returns Result.Error(
            123, "error message", Exception("exception")
        )
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = 456L
        )
        runBlocking {
            timedSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockCreateSessionPresetUseCase.execute(inputSessionPreset)}
        Assert.assertEquals("error message", timedSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset existing sessionPreset success`() {
        coEvery {mockUpdateSessionPresetUseCase.execute(any())} returns Result.Success(45)
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 456L
        )
        runBlocking {
            timedSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockUpdateSessionPresetUseCase.execute(inputSessionPreset)}
        Assert.assertEquals(true, timedSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset existing sessionPreset failure`() {
        coEvery {mockUpdateSessionPresetUseCase.execute(any())} returns Result.Error(
            123, "error message", Exception("exception")
        )
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 456L
        )
        runBlocking {
            timedSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockUpdateSessionPresetUseCase.execute(inputSessionPreset)}
        Assert.assertEquals("error message", timedSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on deleteSessionPreset
    @Test
    fun `test deleteSessionPreset success`() {
        coEvery {mockDeleteSessionPresetUseCase.execute(any())} returns Result.Success(45)
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 456L
        )
        runBlocking {
            timedSessionPresetViewModel.deleteSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockDeleteSessionPresetUseCase.execute(inputSessionPreset)}
        Assert.assertEquals(true, timedSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test deleteSessionPreset failure`() {
        coEvery {mockDeleteSessionPresetUseCase.execute(any())} returns Result.Error(
            123, "error message", Exception("exception")
        )
        val inputSessionPreset = SessionPreset.TimedSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
            intermediateIntervalLength = 123L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = "non default interval sound uri string",
            intermediateIntervalSoundName = "non default interval sound name",
            duration = 456L
        )
        runBlocking {
            timedSessionPresetViewModel.deleteSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockDeleteSessionPresetUseCase.execute(inputSessionPreset)}
        Assert.assertEquals("error message", timedSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartAndEndSoundUriString
    @Test
    fun `test updatePresetStartAndEndSoundUriString`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newStartAndEndSoundUriString = "a different uri string"
        timedSessionPresetViewModel.updatePresetStartAndEndSoundUriString(newStartAndEndSoundUriString)
        val expectedModifiedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = newStartAndEndSoundUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        //checking
        Assert.assertEquals(expectedModifiedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartAndEndSoundUriString
    @Test
    fun `test updatePresetStartAndEndSoundName`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newStartAndEndSoundName = "a different sound name"
        timedSessionPresetViewModel.updatePresetStartAndEndSoundName(newStartAndEndSoundName)
        val expectedModifiedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = newStartAndEndSoundName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        //checking
        Assert.assertEquals(expectedModifiedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartCountdownLength
    @Test
    fun `test updatePresetStartCountdownLength`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newStartCountdownLength = 3456L
        timedSessionPresetViewModel.updatePresetStartCountdownLength(newStartCountdownLength)
        val expectedModifiedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = newStartCountdownLength,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        //checking
        Assert.assertEquals(expectedModifiedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetVibration
    @Test
    fun `test updatePresetVibration`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        timedSessionPresetViewModel.updatePresetVibration(true)
        val expectedModifiedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = true,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        //checking
        Assert.assertEquals(expectedModifiedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetSessionTypeId
    @Test
    fun `test updatePresetSessionTypeId`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newSessionTypeId = 1234L
        timedSessionPresetViewModel.updatePresetSessionTypeId(newSessionTypeId)
        val expectedModifiedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = newSessionTypeId,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        //checking
        Assert.assertEquals(expectedModifiedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalRandom
    @Test
    fun `test updatePresetIntermediateIntervalRandom`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        timedSessionPresetViewModel.updatePresetIntermediateIntervalRandom(true)
        //checking
        val updatedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = true,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(updatedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalLength
    @Test
    fun `test updatePresetIntermediateIntervalLength`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(startSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        timedSessionPresetViewModel.updatePresetIntermediateIntervalLength(7953L)
        //
        val updatedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 7953L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(updatedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalSoundUriString
    @Test
    fun `test updatePresetIntermediateIntervalSoundUriString`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        timedSessionPresetViewModel.updatePresetIntermediateIntervalSoundUriString("another uri string")
        //checking
        val updatedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = "another uri string",
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(updatedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalSoundName
    @Test
    fun `test updatePresetIntermediateIntervalSoundName`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        timedSessionPresetViewModel.updatePresetIntermediateIntervalSoundName("another sound name")
        //checking
        val updatedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = "another sound name",
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(updatedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetDuration
    @Test
    fun `test updatePresetDuration changes nothing`() {
        //initializing with a raw SessionPreset
        runBlocking {
            timedSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = Constants.DEFAULT_SESSION_DURATION_MILLIS
        )
        Assert.assertEquals(newSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        timedSessionPresetViewModel.updatePresetDuration(6789L)
        //checking
        val updatedSessionPreset = SessionPreset.TimedSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
            intermediateIntervalLength = 0L,
            intermediateIntervalRandom = false,
            intermediateIntervalSoundUriString = deviceDefaultRingtoneUriString,
            intermediateIntervalSoundName = deviceDefaultRingtoneName,
            duration = 6789L
        )
        Assert.assertEquals(updatedSessionPreset, timedSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }


}