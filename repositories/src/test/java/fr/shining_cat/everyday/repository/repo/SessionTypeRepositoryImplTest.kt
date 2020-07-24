package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.SessionTypeDao
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType
import fr.shining_cat.everyday.repository.converter.SessionTypeConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SessionTypeRepositoryImplTest {

    @MockK
    private lateinit var mockSessionTypeDao: SessionTypeDao

    @MockK
    private lateinit var mockSessionTypeConverter: SessionTypeConverter

    @MockK
    private lateinit var mockSessionType: SessionType

    @MockK
    lateinit var mockSessionTypeEntity: SessionTypeEntity

    private lateinit var sessionTypeRepo: SessionTypeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockSessionTypeDao)
        assertNotNull(mockSessionType)
        assertNotNull(mockSessionTypeEntity)
        sessionTypeRepo = SessionTypeRepositoryImpl(
            mockSessionTypeDao,
            mockSessionTypeConverter
        )
        coEvery { mockSessionTypeDao.insert(any()) } returns arrayOf(1, 2, 3)
        coEvery { mockSessionTypeDao.update(any()) } returns 3
        coEvery { mockSessionTypeDao.delete(any()) } returns 3
        coEvery { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() } returns listOf(
            mockSessionTypeEntity
        )

    }

    @Test
    fun insert() {
        runBlocking {
            sessionTypeRepo.insert(listOf(mockSessionType))
        }
        coVerify { mockSessionTypeConverter.convertModelsToEntities(any()) }
        coVerify { mockSessionTypeDao.insert(any()) }
    }

    @Test
    fun update() {
        runBlocking {
            sessionTypeRepo.update(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.update(any()) }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionTypeRepo.delete(mockSessionType)
        }
        coVerify { mockSessionTypeConverter.convertModelToEntity(any()) }
        coVerify { mockSessionTypeDao.delete(any()) }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionTypeRepo.getAllSessionTypesLastEditTimeDesc()
        }
        coVerify { mockSessionTypeConverter.convertEntitiesToModels(any()) }
        coVerify { mockSessionTypeDao.getAllSessionTypesLastEditTimeDesc() }
    }
}