package fr.shining_cat.everyday.screens.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.domain.sessionspresets.LoadSessionPresetsUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.testutils.MainCoroutineScopeRule
import fr.shining_cat.everyday.testutils.extensions.getValueForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var mocklogger: Logger

    @MockK
    private lateinit var mockLoadSessionPresetsUseCase: LoadSessionPresetsUseCase

    @MockK
    private lateinit var mockUpdateSessionPresetUseCase: UpdateSessionPresetUseCase

    @MockK
    private lateinit var mockSessionPreset: SessionPreset

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { mocklogger.d(any(), any()) } answers {}
        coEvery { mocklogger.e(any(), any()) } answers {}

        homeViewModel = HomeViewModel(
            mockLoadSessionPresetsUseCase,
            mockUpdateSessionPresetUseCase,
            mocklogger
        )
    }

    /////////////////////////
    // tests on fetchSessionPresets
    @Test
    fun `test fetchSessionPresets success`() {
        coEvery { mockLoadSessionPresetsUseCase.execute() } returns Result.Success(listOf(mockSessionPreset))

        runBlocking {
            homeViewModel.fetchSessionPresets("nothing found!")
        }

        coVerify(exactly = 1) { mockLoadSessionPresetsUseCase.execute() }
        assertEquals(listOf(mockSessionPreset), homeViewModel.sessionPresetsLiveData.getValueForTest())
    }

    @Test
    fun `test fetchSessionPresets failure`() {
        coEvery { mockLoadSessionPresetsUseCase.execute() } returns Result.Error(
            123,
            "an error occurred",
            Exception()
        )

        runBlocking {
            homeViewModel.fetchSessionPresets("nothing found!")
        }

        coVerify(exactly = 1) { mockLoadSessionPresetsUseCase.execute() }
        assertEquals(listOf<SessionPreset>(), homeViewModel.sessionPresetsLiveData.getValueForTest())
        assertEquals("an error occurred", homeViewModel.errorLiveData.getValueForTest())
    }

    @Test
    fun `test fetchSessionPresets nothing found`() {
        coEvery { mockLoadSessionPresetsUseCase.execute() } returns Result.Error(
            Constants.ERROR_CODE_NO_RESULT,
            Constants.ERROR_MESSAGE_NO_RESULT,
            NullPointerException(Constants.ERROR_MESSAGE_NO_RESULT)
        )

        runBlocking {
            homeViewModel.fetchSessionPresets("nothing found!")
        }

        coVerify(exactly = 1) { mockLoadSessionPresetsUseCase.execute() }
        assertEquals(listOf<SessionPreset>(), homeViewModel.sessionPresetsLiveData.getValueForTest())
        assertEquals("nothing found!", homeViewModel.errorLiveData.getValueForTest())
    }


    /////////////////////////
    // tests on moveSessionPresetToTop
    @Test
    fun `test moveSessionPresetToTop`() {
        val mockPreset = mockk<SessionPreset.AudioFreeSessionPreset>()
        coEvery { mockPreset.id } returns 123L
        coEvery { mockPreset.startCountdownLength } returns 234L
        coEvery { mockPreset.startAndEndSoundUriString } returns "startAndEndSoundUriString"
        coEvery { mockPreset.startAndEndSoundName } returns "startAndEndSoundName"
        coEvery { mockPreset.vibration } returns false
        coEvery { mockPreset.sessionTypeId } returns 345
        coEvery { mockPreset.lastEditTime } returns 456L
        val mockPresetCopied = mockk<SessionPreset.AudioFreeSessionPreset>()
        coEvery {
            mockPreset.copy(
                id = any(),
                startCountdownLength = any(),
                startAndEndSoundUriString = any(),
                startAndEndSoundName = any(),
                vibration = any(),
                sessionTypeId = any(),
                lastEditTime = any()
            )
        } returns mockPresetCopied
        coEvery { mockUpdateSessionPresetUseCase.execute(any()) } returns Result.Success(1)
        coEvery { mockLoadSessionPresetsUseCase.execute() } returns Result.Success(listOf(mockSessionPreset))

        runBlocking {
            homeViewModel.moveSessionPresetToTop(mockPreset, "nothing found!")
        }

        coVerify(exactly = 1) { mockUpdateSessionPresetUseCase.execute(mockPresetCopied) }
        coVerify(exactly = 1) { mockLoadSessionPresetsUseCase.execute() }
        assertEquals(listOf(mockSessionPreset), homeViewModel.sessionPresetsLiveData.getValueForTest())
    }

    @Test
    fun `test moveSessionPresetToTop update failed`() {
        val mockPreset = mockk<SessionPreset.AudioFreeSessionPreset>()
        coEvery { mockPreset.id } returns 123L
        coEvery { mockPreset.startCountdownLength } returns 234L
        coEvery { mockPreset.startAndEndSoundUriString } returns "startAndEndSoundUriString"
        coEvery { mockPreset.startAndEndSoundName } returns "startAndEndSoundName"
        coEvery { mockPreset.vibration } returns false
        coEvery { mockPreset.sessionTypeId } returns 345
        coEvery { mockPreset.lastEditTime } returns 456L
        val mockPresetCopied = mockk<SessionPreset.AudioFreeSessionPreset>()
        coEvery {
            mockPreset.copy(
                id = any(),
                startCountdownLength = any(),
                startAndEndSoundUriString = any(),
                startAndEndSoundName = any(),
                vibration = any(),
                sessionTypeId = any(),
                lastEditTime = any()
            )
        } returns mockPresetCopied
        coEvery { mockUpdateSessionPresetUseCase.execute(any()) } returns Result.Error(
            123,
            "an update error occurred",
            Exception()
        )
        coEvery { mockLoadSessionPresetsUseCase.execute() } returns Result.Success(listOf(mockSessionPreset))

        runBlocking {
            homeViewModel.moveSessionPresetToTop(mockPreset, "nothing found!")
        }

        coVerify(exactly = 1) { mockUpdateSessionPresetUseCase.execute(mockPresetCopied) }
        coVerify(exactly = 0) { mockLoadSessionPresetsUseCase.execute() }
        assertEquals("an update error occurred", homeViewModel.errorLiveData.getValueForTest())
    }

}