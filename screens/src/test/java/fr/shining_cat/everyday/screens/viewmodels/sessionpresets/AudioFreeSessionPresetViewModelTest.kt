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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AudioFreeSessionPresetViewModelTest {

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

    private lateinit var audioFreeSessionPresetViewModel: AudioFreeSessionPresetViewModel

    private val deviceDefaultRingtoneUriString = "device Default Ringtone Uri String"
    private val deviceDefaultRingtoneName = "device Default Ringtone Name"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery {mockLogger.d(any(), any())} answers {}
        coEvery {mockLogger.e(any(), any())} answers {}

        audioFreeSessionPresetViewModel = AudioFreeSessionPresetViewModel(
            mockCreateSessionPresetUseCase, mockUpdateSessionPresetUseCase, mockDeleteSessionPresetUseCase, mockLogger
        )
    }

    /////////////////////////
    // tests on init
    @Test
    fun `test init for creation`() {
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    @Test
    fun `test init for edition`() {
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, inputSessionPreset, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        assertEquals(inputSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on isSessionPresetValid
    @Test
    fun `test verifyPresetValidity`() {
        assertEquals(true, audioFreeSessionPresetViewModel.verifyPresetValidity())
    }

    /////////////////////////
    // tests on updatePresetStartAndEndSoundUriString
    @Test
    fun `test updatePresetStartAndEndSoundUriString`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newStartAndEndSoundUriString = "a different uri string"
        audioFreeSessionPresetViewModel.updatePresetStartAndEndSoundUriString(newStartAndEndSoundUriString)
        val expectedModifiedSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = newStartAndEndSoundUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        //checking
        assertEquals(expectedModifiedSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartAndEndSoundUriString
    @Test
    fun `test updatePresetStartAndEndSoundName`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newStartAndEndSoundName = "a different sound name"
        audioFreeSessionPresetViewModel.updatePresetStartAndEndSoundName(newStartAndEndSoundName)
        val expectedModifiedSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = newStartAndEndSoundName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        //checking
        assertEquals(expectedModifiedSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartCountdownLength
    @Test
    fun `test updatePresetStartCountdownLength`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newStartCountdownLength = 3456L
        audioFreeSessionPresetViewModel.updatePresetStartCountdownLength(newStartCountdownLength)
        val expectedModifiedSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = newStartCountdownLength,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        //checking
        assertEquals(expectedModifiedSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetVibration
    @Test
    fun `test updatePresetVibration`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioFreeSessionPresetViewModel.updatePresetVibration(true)
        val expectedModifiedSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = true,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        //checking
        assertEquals(expectedModifiedSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetSessionTypeId
    @Test
    fun `test updatePresetSessionTypeId`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        val newSessionTypeId = 1234L
        audioFreeSessionPresetViewModel.updatePresetSessionTypeId(newSessionTypeId)
        val expectedModifiedSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = newSessionTypeId,
            lastEditTime = -1L,
        )
        //checking
        assertEquals(expectedModifiedSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on saveSessionPreset
    @Test
    fun `test saveSessionPreset new sessionPreset success`() {
        coEvery {mockCreateSessionPresetUseCase.execute(any())} returns Result.Success(45L)
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockCreateSessionPresetUseCase.execute(inputSessionPreset)}
        assertEquals(true, audioFreeSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset new sessionPreset failure`() {
        coEvery {mockCreateSessionPresetUseCase.execute(any())} returns Result.Error(
            123, "error message", Exception("exception")
        )
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockCreateSessionPresetUseCase.execute(inputSessionPreset)}
        assertEquals("error message", audioFreeSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset existing sessionPreset success`() {
        coEvery {mockUpdateSessionPresetUseCase.execute(any())} returns Result.Success(45)
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockUpdateSessionPresetUseCase.execute(inputSessionPreset)}
        assertEquals(true, audioFreeSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset existing sessionPreset failure`() {
        coEvery {mockUpdateSessionPresetUseCase.execute(any())} returns Result.Error(
            123, "error message", Exception("exception")
        )
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockUpdateSessionPresetUseCase.execute(inputSessionPreset)}
        assertEquals("error message", audioFreeSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on deleteSessionPreset
    @Test
    fun `test deleteSessionPreset success`() {
        coEvery {mockDeleteSessionPresetUseCase.execute(any())} returns Result.Success(45)
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.deleteSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockDeleteSessionPresetUseCase.execute(inputSessionPreset)}
        assertEquals(true, audioFreeSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test deleteSessionPreset failure`() {
        coEvery {mockDeleteSessionPresetUseCase.execute(any())} returns Result.Error(
            123, "error message", Exception("exception")
        )
        val inputSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioFreeSessionPresetViewModel.deleteSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) {mockDeleteSessionPresetUseCase.execute(inputSessionPreset)}
        assertEquals("error message", audioFreeSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalRandom
    @Test
    fun `test updatePresetIntermediateIntervalRandom`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioFreeSessionPresetViewModel.updatePresetIntermediateIntervalRandom(true)
        //checking
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetDuration
    @Test
    fun `test updatePresetDuration`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioFreeSessionPresetViewModel.updatePresetDuration(6789L)
        //checking
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalLength
    @Test
    fun `test updatePresetIntermediateIntervalLength`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioFreeSessionPresetViewModel.updatePresetIntermediateIntervalLength(4567L)
        //checking
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalSoundUriString
    @Test
    fun `test updatePresetIntermediateIntervalSoundUriString`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioFreeSessionPresetViewModel.updatePresetIntermediateIntervalSoundUriString("another uri string")
        //checking
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalSoundName
    @Test
    fun `test updatePresetIntermediateIntervalSoundName`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioFreeSessionPresetViewModel.init(
                mockContext, null, deviceDefaultRingtoneUriString, deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioFreeSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioFreeSessionPresetViewModel.updatePresetIntermediateIntervalSoundName("another sound name")
        //checking
        assertEquals(newSessionPreset, audioFreeSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }
}