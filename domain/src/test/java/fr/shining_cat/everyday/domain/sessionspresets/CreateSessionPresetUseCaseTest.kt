package fr.shining_cat.everyday.domain.sessionspresets

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
import org.junit.Assert.assertArrayEquals
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
    private lateinit var mockSessionPreset: SessionPreset

    private lateinit var createSessionPresetUseCase: CreateSessionPresetUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockSessionPresetRepository)

        createSessionPresetUseCase = CreateSessionPresetUseCase(
            mockSessionPresetRepository,
            mockLogger
        )
    }

    @Test
    fun `test insert succeeded`() {
        coEvery { mockSessionPresetRepository.insert(any()) } returns mockOutputSuccess
        val success = arrayOf(3L)
        coEvery { mockOutputSuccess.result } returns success
        val result = runBlocking {
            createSessionPresetUseCase.execute(mockSessionPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.insert(listOf(mockSessionPreset)) }
        assertTrue(result is Result.Success)
        assertArrayEquals(arrayOf(3L), (result as Result.Success).result)
    }

    @Test
    fun `test insert failed`() {
        coEvery { mockSessionPresetRepository.insert(any()) } returns mockOutputError
        coEvery { mockOutputError.errorCode } returns 123
        coEvery { mockOutputError.errorResponse } returns "mocked error response message"
        coEvery { mockOutputError.exception } returns mockException
        val result = runBlocking {
            createSessionPresetUseCase.execute(mockSessionPreset)
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.insert(listOf(mockSessionPreset)) }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(123, result.errorCode)
        assertEquals("mocked error response message", result.errorResponse)
        assertEquals(mockException, result.exception)
    }
}