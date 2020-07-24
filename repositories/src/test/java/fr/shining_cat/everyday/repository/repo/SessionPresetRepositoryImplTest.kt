package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.converter.SessionPresetConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
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
        coEvery { mockSessionPresetDao.insert(any()) } returns arrayOf(1, 2, 3)
        coEvery { mockSessionPresetDao.update(any()) } returns 3
        coEvery { mockSessionPresetDao.delete(any()) } returns 3
        coEvery { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() } returns listOf(
            mockSessionPresetEntity
        )
    }

    @Test
    fun insert() {
        runBlocking {
            sessionPresetRepo.insert(listOf(mockSessionPreset))
        }
        coVerify { mockSessionPresetConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionPresetDao.insert(any()) }
    }

    @Test
    fun update() {
        runBlocking {
            sessionPresetRepo.update(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.update(any()) }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionPresetRepo.delete(mockSessionPreset)
        }
        coVerify { mockSessionPresetConverter.convertModelToEntity(any()) }
        coVerify { mockSessionPresetDao.delete(any()) }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionPresetRepo.getAllSessionPresetsLastEditTimeDesc()
        }
        coVerify { mockSessionPresetConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc() }
    }
}