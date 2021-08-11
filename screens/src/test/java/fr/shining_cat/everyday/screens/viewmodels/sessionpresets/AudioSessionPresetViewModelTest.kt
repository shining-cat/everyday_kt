package fr.shining_cat.everyday.screens.viewmodels.sessionpresets

import android.content.Context
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.FileMetadataRetrieveUseCase
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.AudioFileMetadata
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.testutils.MainCoroutineScopeRule
import fr.shining_cat.everyday.testutils.extensions.getValueForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
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
class AudioSessionPresetViewModelTest {

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
    private lateinit var mockMetadataRetrieveUseCase: FileMetadataRetrieveUseCase

    @MockK
    private lateinit var mockContext: Context

    @MockK
    private lateinit var mockUri: Uri

    @MockK
    private lateinit var mockAudioFileMetadata: AudioFileMetadata

    private lateinit var audioSessionPresetViewModel: AudioSessionPresetViewModel

    private val deviceDefaultRingtoneUriString = "device Default Ringtone Uri String"
    private val deviceDefaultRingtoneName = "device Default Ringtone Name"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { mockLogger.d(any(), any()) } answers {}
        coEvery { mockLogger.e(any(), any()) } answers {}
        mockkStatic(Uri::class)

        audioSessionPresetViewModel = AudioSessionPresetViewModel(
            mockCreateSessionPresetUseCase,
            mockUpdateSessionPresetUseCase,
            mockDeleteSessionPresetUseCase,
            mockMetadataRetrieveUseCase,
            mockLogger
        )
    }

    /////////////////////////
    // tests on init
    @Test
    fun `test init for creation`() {
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val newSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(newSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    @Test
    fun `test init for edition with blank sound guide uri`() {
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 8976L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "specific artist name",
            audioGuideSoundAlbumName = "specific album name",
            audioGuideSoundTitle = "specific sound title",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        //
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        //
        coVerify(exactly = 0) { Uri.parse(any()) }
        coVerify(exactly = 0) { mockMetadataRetrieveUseCase.execute(mockContext, mockUri) }
        coVerify(exactly = 0) { mockAudioFileMetadata.albumName }
        coVerify(exactly = 0) { mockAudioFileMetadata.artistName }
        coVerify(exactly = 0) { mockAudioFileMetadata.fileName }
        coVerify(exactly = 0) { mockAudioFileMetadata.durationMs }
        val expectedOutputPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        assertEquals(expectedOutputPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    @Test
    fun `test init for edition with blank secondary fields`() {
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = -1L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        coEvery { Uri.parse(any()) } returns mockUri
        coEvery { mockMetadataRetrieveUseCase.execute(any(), any()) } returns mockAudioFileMetadata
        coEvery { mockAudioFileMetadata.albumName } returns "metadata album name"
        coEvery { mockAudioFileMetadata.artistName } returns "metadata artist name"
        coEvery { mockAudioFileMetadata.fileName } returns "metadata filename"
        coEvery { mockAudioFileMetadata.durationMs } returns 6789L
        //
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        //
        coVerify(exactly = 1) { Uri.parse(any()) }
        coVerify(exactly = 1) { mockMetadataRetrieveUseCase.execute(mockContext, mockUri) }
        coVerify(exactly = 1) { mockAudioFileMetadata.albumName }
        coVerify(exactly = 1) { mockAudioFileMetadata.artistName }
        coVerify(exactly = 1) { mockAudioFileMetadata.fileName }
        coVerify(exactly = 1) { mockAudioFileMetadata.durationMs }
        val expectedOutputPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 6789L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "metadata artist name",
            audioGuideSoundAlbumName = "metadata album name",
            audioGuideSoundTitle = "metadata filename",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        assertEquals(expectedOutputPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    @Test
    fun `test init for edition with NON blank fields`() {
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 3478L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist string",
            audioGuideSoundAlbumName = "specific album string",
            audioGuideSoundTitle = "specific title string",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        coEvery { Uri.parse(any()) } returns mockUri
        coEvery { mockMetadataRetrieveUseCase.execute(any(), any()) } returns mockAudioFileMetadata
        coEvery { mockAudioFileMetadata.albumName } returns "metadata album name"
        coEvery { mockAudioFileMetadata.artistName } returns "metadata artist name"
        coEvery { mockAudioFileMetadata.fileName } returns "metadata filename"
        coEvery { mockAudioFileMetadata.durationMs } returns 6789L
        //
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        //
        coVerify(exactly = 1) { Uri.parse(any()) }
        coVerify(exactly = 1) { mockMetadataRetrieveUseCase.execute(mockContext, mockUri) }
        coVerify(exactly = 0) { mockAudioFileMetadata.albumName }
        coVerify(exactly = 0) { mockAudioFileMetadata.artistName }
        coVerify(exactly = 0) { mockAudioFileMetadata.fileName }
        coVerify(exactly = 0) { mockAudioFileMetadata.durationMs }
        val expectedOutputPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 3478L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist string",
            audioGuideSoundAlbumName = "specific album string",
            audioGuideSoundTitle = "specific title string",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        assertEquals(expectedOutputPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on verifyPresetValidity
    @Test
    fun `test verifyPresetValidity TRUE`() {
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        coEvery { Uri.parse(any()) } returns mockUri
        coEvery { mockMetadataRetrieveUseCase.execute(any(), any()) } returns mockAudioFileMetadata
        coEvery { mockAudioFileMetadata.albumName } returns "metadata album name"
        coEvery { mockAudioFileMetadata.artistName } returns "metadata artist name"
        coEvery { mockAudioFileMetadata.fileName } returns "metadata filename"
        coEvery { mockAudioFileMetadata.durationMs } returns 6789L
        //
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        assertEquals(true, audioSessionPresetViewModel.verifyPresetValidity())
    }

    @Test
    fun `test verifyPresetValidity audioGuideSoundUriString is blank`() {
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 234L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        //
        assertEquals(false, audioSessionPresetViewModel.verifyPresetValidity())
        assertEquals(false, audioSessionPresetViewModel.invalidAudioGuideLiveData.getValueForTest())
    }

    @Test
    fun `test verifyPresetValidity duration is less than or equal to 0`() {
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 34L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "a different uri string",
            startAndEndSoundName = "a different sound name",
            duration = 0L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 23,
            lastEditTime = 123456L,
        )
        coEvery { Uri.parse(any()) } returns mockUri
        coEvery { mockMetadataRetrieveUseCase.execute(any(), any()) } returns mockAudioFileMetadata
        coEvery { mockAudioFileMetadata.albumName } returns "metadata album name"
        coEvery { mockAudioFileMetadata.artistName } returns "metadata artist name"
        coEvery { mockAudioFileMetadata.fileName } returns "metadata filename"
        coEvery { mockAudioFileMetadata.durationMs } returns 0L
        //
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                inputSessionPreset,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        assertEquals(false, audioSessionPresetViewModel.verifyPresetValidity())
        assertEquals(false, audioSessionPresetViewModel.invalidDurationLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on saveSessionPreset
    @Test
    fun `test saveSessionPreset new sessionPreset success`() {
        coEvery { mockCreateSessionPresetUseCase.execute(any()) } returns Result.Success(45L)
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) { mockCreateSessionPresetUseCase.execute(inputSessionPreset) }
        assertEquals(true, audioSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset new sessionPreset failure`() {
        coEvery { mockCreateSessionPresetUseCase.execute(any()) } returns Result.Error(
            123,
            "error message",
            Exception("exception")
        )
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) { mockCreateSessionPresetUseCase.execute(inputSessionPreset) }
        assertEquals("error message", audioSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset existing sessionPreset success`() {
        coEvery { mockUpdateSessionPresetUseCase.execute(any()) } returns Result.Success(45)
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) { mockUpdateSessionPresetUseCase.execute(inputSessionPreset) }
        assertEquals(true, audioSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test saveSessionPreset existing sessionPreset failure`() {
        coEvery { mockUpdateSessionPresetUseCase.execute(any()) } returns Result.Error(
            123,
            "error message",
            Exception("exception")
        )
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioSessionPresetViewModel.saveSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) { mockUpdateSessionPresetUseCase.execute(inputSessionPreset) }
        assertEquals("error message", audioSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on deleteSessionPreset
    @Test
    fun `test deleteSessionPreset success`() {
        coEvery { mockDeleteSessionPresetUseCase.execute(any()) } returns Result.Success(45)
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioSessionPresetViewModel.deleteSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) { mockDeleteSessionPresetUseCase.execute(inputSessionPreset) }
        assertEquals(true, audioSessionPresetViewModel.successLiveData.getValueForTest())
    }

    @Test
    fun `test deleteSessionPreset failure`() {
        coEvery { mockDeleteSessionPresetUseCase.execute(any()) } returns Result.Error(
            123,
            "error message",
            Exception("exception")
        )
        val inputSessionPreset = SessionPreset.AudioSessionPreset(
            id = 1234L,
            startCountdownLength = 345L,
            startAndEndSoundUriString = "specific uri string",
            startAndEndSoundName = "specific sound name",
            duration = 234L,
            audioGuideSoundUriString = "specific audio guide uri string",
            audioGuideSoundArtistName = "specific artist name string",
            audioGuideSoundAlbumName = "specific album name string",
            audioGuideSoundTitle = "specific audio guide title string",
            vibration = true,
            sessionTypeId = 345,
            lastEditTime = 4567L,
        )
        runBlocking {
            audioSessionPresetViewModel.deleteSessionPreset(inputSessionPreset)
        }

        //checking
        coVerify(exactly = 1) { mockDeleteSessionPresetUseCase.execute(inputSessionPreset) }
        assertEquals("error message", audioSessionPresetViewModel.errorLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartAndEndSoundUriString
    @Test
    fun `test updatePresetStartAndEndSoundUriString`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetStartAndEndSoundUriString("modified StartAndEndSoundUriString")
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = "modified StartAndEndSoundUriString",
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartAndEndSoundName
    @Test
    fun `test updatePresetStartAndEndSoundName`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetStartAndEndSoundName("modified StartAndEndSoundName")
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = "modified StartAndEndSoundName",
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetStartCountdownLength
    @Test
    fun `test updatePresetStartCountdownLength`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetStartCountdownLength(987L)
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = 987L,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetVibration
    @Test
    fun `test updatePresetVibration`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetVibration(true)
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = true,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetSessionTypeId
    @Test
    fun `test updatePresetSessionTypeId`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetSessionTypeId(234L)
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = 234,
            lastEditTime = -1L,
        )
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalRandom
    @Test
    fun `test updatePresetIntermediateIntervalRandom changes nothing`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetIntermediateIntervalRandom(true)
        //
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetIntermediateIntervalLength
    @Test
    fun `test updatePresetIntermediateIntervalLength changes nothing`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetIntermediateIntervalLength(7953L)
        //
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    // tests on updatePresetIntermediateIntervalSoundUriString
    @Test
    fun `test updatePresetIntermediateIntervalSoundUriString changes nothing`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetIntermediateIntervalSoundUriString("new interval sound uri")
        //
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    // tests on updatePresetIntermediateIntervalSoundName
    @Test
    fun `test updatePresetIntermediateIntervalSoundName changes nothing`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetIntermediateIntervalSoundName("new interval sound name")
        //
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    /////////////////////////
    // tests on updatePresetDuration
    @Test
    fun `test updatePresetDuration valid value`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetDuration(234L)
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = 234L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

    @Test
    fun `test updatePresetDuration invalid value`() {
        //initializing with a raw SessionPreset
        runBlocking {
            audioSessionPresetViewModel.init(
                mockContext,
                null,
                deviceDefaultRingtoneUriString,
                deviceDefaultRingtoneName
            )
        }
        val startSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = -1L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(startSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
        //modifying
        audioSessionPresetViewModel.updatePresetDuration(0L)
        //checking
        val updatedSessionPreset = SessionPreset.AudioSessionPreset(
            id = -1L,
            startCountdownLength = Constants.DEFAULT_SESSION_COUNTDOWN_MILLIS,
            startAndEndSoundUriString = deviceDefaultRingtoneUriString,
            startAndEndSoundName = deviceDefaultRingtoneName,
            duration = 0L,
            audioGuideSoundUriString = "",
            audioGuideSoundArtistName = "",
            audioGuideSoundAlbumName = "",
            audioGuideSoundTitle = "",
            vibration = false,
            sessionTypeId = -1,
            lastEditTime = -1L,
        )
        assertEquals(false, audioSessionPresetViewModel.verifyPresetValidity())
        assertEquals(false, audioSessionPresetViewModel.invalidDurationLiveData.getValueForTest())
        assertEquals(updatedSessionPreset, audioSessionPresetViewModel.sessionPresetUpdatedLiveData.getValueForTest())
    }

}