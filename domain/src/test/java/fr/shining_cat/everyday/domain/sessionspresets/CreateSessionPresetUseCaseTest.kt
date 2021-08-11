package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_INSERT_FAILED
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

class CreateSessionPresetUseCaseTest {
    @MockK
    private lateinit var mockSessionPresetRepository: SessionPresetRepository

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockOutputSuccess: Output.Success<Array<Long>>

    @MockK
    private lateinit var mockOutputError: Output.Error

    @MockK
    private lateinit var mockException: Exception

    @MockK
    private lateinit var mockPreset: SessionPreset.AudioFreeSessionPreset

    @MockK
    private lateinit var mockPresetCopied: SessionPreset.AudioFreeSessionPreset

    private lateinit var createSessionPresetUseCase: CreateSessionPresetUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockSessionPresetRepository)

        createSessionPresetUseCase = CreateSessionPresetUseCase(
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
    fun `test insert succeeded`() {
        coEvery { mockSessionPresetRepository.insert(any()) } returns mockOutputSuccess
        val success = arrayOf(3L)
        coEvery { mockOutputSuccess.result } returns success
        val output = runBlocking {
            createSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.insert(listOf(mockPresetCopied)) }
        assertTrue(output is Result.Success)
        output as Result.Success
        assertEquals(3L, output.result)
    }

    @Test
    fun `test insert failed returned wrong number of ids`() {
        coEvery { mockSessionPresetRepository.insert(any()) } returns mockOutputSuccess
        val success = arrayOf(3L, 7L, 9L)
        coEvery { mockOutputSuccess.result } returns success
        val result = runBlocking {
            createSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.insert(listOf(mockPresetCopied)) }
        assertTrue(result is Result.Error)
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(ERROR_CODE_DATABASE_OPERATION_FAILED, result.errorCode)
        assertEquals(ERROR_MESSAGE_INSERT_FAILED, result.errorResponse)
        assertEquals(ERROR_MESSAGE_INSERT_FAILED, result.exception?.message)
    }

    @Test
    fun `test insert failed with exception`() {
        coEvery { mockSessionPresetRepository.insert(any()) } returns mockOutputError
        coEvery { mockOutputError.errorCode } returns 123
        coEvery { mockOutputError.errorResponse } returns "mocked error response message"
        coEvery { mockOutputError.exception } returns mockException
        val result = runBlocking {
            createSessionPresetUseCase.execute(mockPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.insert(listOf(mockPresetCopied)) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(123, result.errorCode)
        assertEquals("mocked error response message", result.errorResponse)
        assertEquals(mockException, result.exception)
    }
}