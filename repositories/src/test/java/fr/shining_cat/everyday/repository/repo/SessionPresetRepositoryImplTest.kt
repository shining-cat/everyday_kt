package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.SessionPresetDao
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.converter.SessionPresetConverter
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionPresetRepositoryImplTest : AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionPresetDao: SessionPresetDao

    @Mock
    private lateinit var mockSessionPresetConverter: SessionPresetConverter

    @Mock
    private lateinit var mockSessionPreset: SessionPreset

    @Mock
    lateinit var mockSessionPresetEntity: SessionPresetEntity

    private lateinit var sessionPresetRepo: SessionPresetRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionPresetDao)
        assertNotNull(mockSessionPreset)
        assertNotNull(mockSessionPresetEntity)
        sessionPresetRepo = SessionPresetRepositoryImpl(
            mockSessionPresetDao,
            mockSessionPresetConverter
        )
        runBlocking {
            Mockito.`when`(mockSessionPresetDao.insert(any())).thenReturn(arrayOf(1, 2, 3))
            Mockito.`when`(mockSessionPresetDao.update(any())).thenReturn(3)
            Mockito.`when`(mockSessionPresetDao.delete(any())).thenReturn(3)
            Mockito.`when`(mockSessionPresetDao.getAllSessionPresetsLastEditTimeDesc())
                .thenReturn(listOf(mockSessionPresetEntity))
        }

    }

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun insert() {
        runBlocking {
            sessionPresetRepo.insert(listOf(mockSessionPreset))
            Mockito.verify(mockSessionPresetConverter).convertModelsToEntities(any())
            Mockito.verify(mockSessionPresetDao).insert(any())
        }
    }

    @Test
    fun update() {
        runBlocking {
            sessionPresetRepo.update(mockSessionPreset)
            Mockito.verify(mockSessionPresetConverter).convertModelToEntity(any())
            Mockito.verify(mockSessionPresetDao).update(any())
        }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionPresetRepo.delete(mockSessionPreset)
            Mockito.verify(mockSessionPresetConverter).convertModelToEntity(any())
            Mockito.verify(mockSessionPresetDao).delete(any())
        }
    }
    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionPresetRepo.getAllSessionPresetsLastEditTimeDesc()
            Mockito.verify(mockSessionPresetConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionPresetDao).getAllSessionPresetsLastEditTimeDesc()
        }
    }
}