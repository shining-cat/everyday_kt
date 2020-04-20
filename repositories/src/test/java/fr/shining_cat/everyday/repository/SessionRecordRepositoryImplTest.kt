package fr.shining_cat.everyday.repository

import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.SessionRecord
import fr.shining_cat.everyday.repository.converter.SessionRecordConverter
import fr.shining_cat.everyday.repository.repo.SessionRecordRepository
import fr.shining_cat.everyday.repository.repo.SessionRecordRepositoryImpl
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionRecordRepositoryImplTest : AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionRecordDao: SessionRecordDao

    @Mock
    private lateinit var mockSessionRecordConverter: SessionRecordConverter

    @Mock
    private lateinit var mockSessionRecord: SessionRecord

    @Mock
    lateinit var mockSessionRecordEntity: SessionRecordEntity

    private lateinit var sessionRecordRepo: SessionRecordRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionRecordDao)
        assertNotNull(mockSessionRecord)
        assertNotNull(mockSessionRecordEntity)
        sessionRecordRepo =
            SessionRecordRepositoryImpl(
                mockSessionRecordDao,
                mockSessionRecordConverter
            )
        runBlocking {
            Mockito.`when`(mockSessionRecordDao.insert(any())).thenReturn(arrayOf(1, 2, 3))
            Mockito.`when`(mockSessionRecordDao.update(any())).thenReturn(3)
            Mockito.`when`(mockSessionRecordDao.delete(any())).thenReturn(3)
            Mockito.`when`(mockSessionRecordDao.asyncGetAllSessionsStartTimeAsc())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getAllSessionsStartTimeAsc())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getAllSessionsStartTimeDesc())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getAllSessionsDurationAsc())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getAllSessionsDurationDesc())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getAllSessionsWithMp3())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getAllSessionsWithoutMp3())
                .thenReturn(listOf(mockSessionRecordEntity))
            Mockito.`when`(mockSessionRecordDao.getSessionsSearch(anyString()))
                .thenReturn(listOf(mockSessionRecordEntity))
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
            sessionRecordRepo.insert(listOf(mockSessionRecord))
            Mockito.verify(mockSessionRecordConverter).convertModelsToEntities(any())
            Mockito.verify(mockSessionRecordDao).insert(any())
        }
    }

    @Test
    fun update() {
        runBlocking {
            sessionRecordRepo.update(mockSessionRecord)
            Mockito.verify(mockSessionRecordConverter).convertModelToEntity(any())
            Mockito.verify(mockSessionRecordDao).update(any())
        }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionRecordRepo.delete(mockSessionRecord)
            Mockito.verify(mockSessionRecordConverter).convertModelToEntity(any())
            Mockito.verify(mockSessionRecordDao).delete(any())
        }
    }

    @Test
    fun deleteAllSessions() {
        runBlocking {
            sessionRecordRepo.deleteAllSessions()
            Mockito.verify(mockSessionRecordDao).deleteAllSessions()
        }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeAsc()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsStartTimeAsc()
        }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsStartTimeDesc()
        }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsDurationAsc()
        }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsDurationDesc()
        }
    }


    @Test
    fun `try name`() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsWithMp3()
        }
    }

    @Test
    fun getAllSessionsWithMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsWithMp3()
        }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getAllSessionsWithoutMp3()
        }
    }

    @Test
    fun getSessionsSearch() {
        runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).getSessionsSearch(anyString())
        }
    }

    @Test
    fun asyncGetAllSessionsStartTimeAsc() {
        runBlocking {
            sessionRecordRepo.asyncGetAllSessionsStartTimeAsc()
            Mockito.verify(mockSessionRecordConverter).convertEntitiesToModels(any())
            Mockito.verify(mockSessionRecordDao).asyncGetAllSessionsStartTimeAsc()
        }
    }

    @Test
    fun getLatestRecordedSessionDate() {
        runBlocking {
            sessionRecordRepo.getMostRecentSessionRecordDate()
            Mockito.verify(mockSessionRecordDao).getMostRecentSessionRecordDate()
        }
    }
}