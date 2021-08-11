package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_UPDATE_FAILED
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.repo.SessionPresetRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateSessionPresetUseCaseTest {
    @MockK
    private lateinit var mockSessionPresetRepository: SessionPresetRepository

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockOutputError: Output.Error

    @MockK
    private lateinit var mockException: Exception

    @MockK
    private lateinit var mockPreset: SessionPreset.AudioFreeSessionPreset

    @MockK
    private lateinit var mockPresetCopied: SessionPreset.AudioFreeSessionPreset

    private lateinit var updateSessionPresetUseCase: UpdateSessionPresetUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockSessionPresetRepository)

        updateSessionPresetUseCase = UpdateSessionPresetUseCase(
            mockSessionPresetRepository,
            mockLogger
        )
        coEvery { mockPreset.id } returns 123L
        coEvery { mockPreset.startCountdownLength } returns 234L
        coEvery { mockPreset.startAndEndSoundUriString } returns "startAndEndSoundUriString"
        coEvery { mockPreset.startAndEndSoundName } returns "startAndEndSoundName"
        coEvery { mockPreset.vibration } returns false
        coEvery { mockPreset.sessionTypeId } returns 345
        coEvery { mockPreset.lastEditTime } returns 456L
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
    }

    @Test
    fun `test update succeeded`() {
        coEvery { mockSessionPresetRepository.update(any()) } returns Output.Success(1)
        val result = runBlocking {
            updateSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.update(mockPresetCopied) }
        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).result)
    }

    @Test
    fun `test update failed with wrong number returned`() {
        coEvery { mockSessionPresetRepository.update(any()) } returns Output.Success(7)
        val result = runBlocking {
            updateSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.update(mockPresetCopied) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(ERROR_CODE_DATABASE_OPERATION_FAILED, result.errorCode)
        assertEquals(ERROR_MESSAGE_UPDATE_FAILED, result.errorResponse)
        assertEquals(ERROR_MESSAGE_UPDATE_FAILED, result.exception?.message)
    }

    @Test
    fun `test update failed returned 0`() {
        coEvery { mockSessionPresetRepository.update(any()) } returns Output.Success(0)
        val result = runBlocking {
            updateSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.update(mockPresetCopied) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(ERROR_CODE_DATABASE_OPERATION_FAILED, result.errorCode)
        assertEquals(ERROR_MESSAGE_UPDATE_FAILED, result.errorResponse)
        assertEquals(ERROR_MESSAGE_UPDATE_FAILED, result.exception?.message)
    }

    @Test
    fun `test update failed with exception`() {
        coEvery { mockSessionPresetRepository.update(any()) } returns mockOutputError
        coEvery { mockOutputError.errorCode } returns 123
        coEvery { mockOutputError.errorResponse } returns "mocked error response message"
        coEvery { mockOutputError.exception } returns mockException
        val result = runBlocking {
            updateSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.update(mockPresetCopied) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(123, result.errorCode)
        assertEquals("mocked error response message", result.errorResponse)
        assertEquals(mockException, result.exception)
    }
}