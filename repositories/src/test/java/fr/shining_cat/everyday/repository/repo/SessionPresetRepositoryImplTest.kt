package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converter.SessionPresetConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SessionPresetRepositoryImplTest {

    @MockK
    private lateinit var mockSessionPresetDao: SessionPresetDao

    @MockK
    private lateinit var mockSessionPresetConverter: SessionPresetConverter

    @MockK
    private lateinit var mockSessionPreset: SessionPreset

    @MockK
    lateinit var mockSessionPresetEntity: SessionPresetEntity

    private lateinit var sessionPresetRepo: SessionPresetRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockSessionPresetDao)
        assertNotNull(mockSessionPreset)
        assertNotNull(mockSessionPresetEntity)
        sessionPresetRepo = SessionPresetRepositoryImpl(
            mockSessionPresetDao,
            mockSessionPresetConverter
        )
    }

    @Test
    fun insert() {
        coEvery { mockSessionPresetConverter.convertModelsToEntities(any()) } returns listOf(
            mockSessionPresetEntity
        )
        coEvery { mockSessionPresetDao.insert(any()) } returns arrayOf(1, 2, 3)
        val output = runBlocking {
            sessionPresetRepo.insert(
                listOf(
                    mockSessionPreset,
                    mockSessionPreset,
                    mockSessionPreset
                )
            )
        }
        coVerify { mockSessionPresetConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionPresetDao.insert(any()) }
        assertTrue(output is Output.Success)
        assertEquals(3, (output as Output.Success).result.size)
    }

    @Test
    fun update() {
        coEvery { mockSessionPresetConverter.convertModelToEntity(any()) } returns mockSessionPresetEntity
        coEvery { mockSessionPresetDao.update(any()) } returns 1
        val output = runBlocking {
            sessionPresetRepo.update(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.update(any()) }
        assertTrue(output is Output.Success)
        assertEquals(1, (output as Output.Success).result)
    }

    @Test
    fun deleteSession() {
        coEvery { mockSessionPresetConverter.convertModelToEntity(any()) } returns mockSessionPresetEntity
        coEvery { mockSessionPresetDao.delete(any()) } returns 1
        val output = runBlocking {
            sessionPresetRepo.delete(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.delete(any()) }
        assertTrue(output is Output.Success)
        assertEquals(1, (output as Output.Success).result)
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        coEvery { mockSessionPresetConverter.convertEntitiesToModels(any()) } returns listOf(
            mockSessionPreset
        )
        coEvery { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() } returns listOf(
            mockSessionPresetEntity
        )
        val output = runBlocking {
            sessionPresetRepo.getAllSessionPresetsLastEditTimeDesc()
        }
        coVerify { mockSessionPresetConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockSessionPreset), (output as Output.Success).result)
    }
}