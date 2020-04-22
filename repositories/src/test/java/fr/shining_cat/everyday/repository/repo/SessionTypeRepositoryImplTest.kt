package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.SessionTypeDao
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import fr.shining_cat.everyday.repository.converter.SessionTypeConverter
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionTypeRepositoryImplTest : AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionTypeDao: SessionTypeDao

    @Mock
    private lateinit var mockSessionTypeConverter: SessionTypeConverter

    @Mock
    private lateinit var mockSessionType: SessionType

    @Mock
    lateinit var mockSessionTypeEntity: SessionTypeEntity

    private lateinit var sessionTypeRepo: SessionTypeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionTypeDao)
        assertNotNull(mockSessionType)
        assertNotNull(mockSessionTypeEntity)
        sessionTypeRepo = SessionTypeRepositoryImpl(
            mockSessionTypeDao,
            mockSessionTypeConverter
        )
        runBlocking {
            Mockito.`when`(mockSessionTypeDao.insert(any())).thenReturn(arrayOf(1, 2, 3))
            Mockito.`when`(mockSessionTypeDao.update(any())).thenReturn(3)
            Mockito.`when`(mockSessionTypeDao.delete(any())).thenReturn(3)
            Mockito.`when`(mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc())
                .thenReturn(listOf(mockSessionTypeEntity))
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
            sessionTypeRepo.insert(listOf(mockSessionType))
            Mockito.verify(mockSessionTypeConverter).convertModelsToEntities(any())
            Mockito.verify(mockSessionTypeDao).insert(any())
        }
    }

    @Test
    fun update() {
        runBlocking {
            sessionTypeRepo.update(mockSessionType)
            Mockito.verify(mockSessionTypeConverter).convertModelToEntity(any())
            Mockito.verify(mockSessionTypeDao).update(any())
        }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionTypeRepo.delete(mockSessionType)
            Mockito.verify(mockSessionTypeConverter).convertModelToEntity(any())
            Mockito.verify(mockSessionTypeDao).delete(any())
        }
    }
    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionTypeRepo.getAllSessionTypesLastEditTimeDesc()
            Mockito.verify(mockSessionTypeConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionTypeDao).getAllSessionTypesLastEditTimeDesc()
        }
    }
}