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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoadSessionPresetsUseCaseTest {
    @MockK
    private lateinit var mockSessionPresetRepository: SessionPresetRepository

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockOutputSuccess: Output.Success<List<SessionPreset>>

    @MockK
    private lateinit var mockOutputError: Output.Error

    @MockK
    private lateinit var mockException: Exception

    @MockK
    private lateinit var mockSessionPreset: SessionPreset

    private lateinit var loadSessionPresetsUseCase: LoadSessionPresetsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockSessionPresetRepository)

        loadSessionPresetsUseCase = LoadSessionPresetsUseCase(
            mockSessionPresetRepository,
            mockLogger
        )
    }

    @Test
    fun `test load succeeded`() {
        coEvery { mockSessionPresetRepository.getAllSessionPresetsLastEditTimeDesc() } returns mockOutputSuccess
        val success = listOf(mockSessionPreset, mockSessionPreset, mockSessionPreset)
        coEvery { mockOutputSuccess.result } returns success
        val output = runBlocking {
            loadSessionPresetsUseCase.execute()
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.getAllSessionPresetsLastEditTimeDesc() }
        assertTrue(output is Result.Success)
        output as Result.Success
        assertEquals(listOf(mockSessionPreset, mockSessionPreset, mockSessionPreset), output.result)
    }

    @Test
    fun `test load failed`() {
        coEvery { mockSessionPresetRepository.getAllSessionPresetsLastEditTimeDesc() } returns mockOutputError
        coEvery { mockOutputError.errorCode } returns 123
        coEvery { mockOutputError.errorResponse } returns "mocked error response message"
        coEvery { mockOutputError.exception } returns mockException
        val result = runBlocking {
            loadSessionPresetsUseCase.execute()
        }
        coVerify(exactly = 1) { mockSessionPresetRepository.getAllSessionPresetsLastEditTimeDesc() }
        assertTrue(result is Result.Error)
        result as Result.Error
        assertEquals(123, result.errorCode)
        assertEquals("mocked error response message", result.errorResponse)
        assertEquals(mockException, result.exception)
    }
}