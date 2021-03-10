package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_DELETE_FAILED
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

class DeleteSessionPresetUseCaseTest {
    @MockK
    private lateinit var mockSessionPresetRepository: SessionPresetRepository

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockOutputError: Output.Error

    @MockK
    private lateinit var mockException: Exception

    @MockK
    private lateinit var mockSessionPreset: SessionPreset

    private lateinit var deleteSessionPresetUseCase: DeleteSessionPresetUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockSessionPresetRepository)

        deleteSessionPresetUseCase = DeleteSessionPresetUseCase(
            mockSessionPresetRepository,
            mockLogger
        )
    }

    @Test
    fun `test delete succeeded`() {
        coEvery { mockSessionPresetRepository.delete(any()) } returns Output.Success(1)
        val result = runBlocking {
            deleteSessionPresetUseCase.execute(mockSessionPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.delete(mockSessionPreset) }
        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).result)
    }

    @Test
    fun `test delete failed with wrong number returned`() {
        coEvery { mockSessionPresetRepository.delete(any()) } returns Output.Success(7)
        val result = runBlocking {
            deleteSessionPresetUseCase.execute(mockSessionPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.delete(mockSessionPreset) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(ERROR_CODE_DATABASE_OPERATION_FAILED, result.errorCode)
        assertEquals(ERROR_MESSAGE_DELETE_FAILED, result.errorResponse)
        assertEquals(ERROR_MESSAGE_DELETE_FAILED, result.exception?.message)
    }

    @Test
    fun `test delete failed returned 0`() {
        coEvery { mockSessionPresetRepository.delete(any()) } returns Output.Success(0)
        val result = runBlocking {
            deleteSessionPresetUseCase.execute(mockSessionPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.delete(mockSessionPreset) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(ERROR_CODE_DATABASE_OPERATION_FAILED, result.errorCode)
        assertEquals(ERROR_MESSAGE_DELETE_FAILED, result.errorResponse)
        assertEquals(ERROR_MESSAGE_DELETE_FAILED, result.exception?.message)
    }

    @Test
    fun `test delete failed with exception`() {
        coEvery { mockSessionPresetRepository.delete(any()) } returns mockOutputError
        coEvery { mockOutputError.errorCode } returns 123
        coEvery { mockOutputError.errorResponse } returns "mocked error response message"
        coEvery { mockOutputError.exception } returns mockException
        val result = runBlocking {
            deleteSessionPresetUseCase.execute(mockSessionPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.delete(mockSessionPreset) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(123, result.errorCode)
        assertEquals("mocked error response message", result.errorResponse)
        assertEquals(mockException, result.exception)
    }
}