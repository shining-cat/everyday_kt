package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.SessionRecordDao
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.Mood
import fr.shining_cat.everyday.models.MoodValue
import fr.shining_cat.everyday.models.RealDurationVsPlanned
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
import java.util.*

class SessionRecordRepositoryImplTest : AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionRecordDao: SessionRecordDao
    @Mock
    private lateinit var mockSessionRecordConverter: SessionRecordConverter

    @Mock
    private lateinit var mockSessionRecord: SessionRecord

    @Mock
    lateinit var sessionRecordEntityLive: LiveData<SessionRecordEntity?>

    @Mock
    lateinit var sessionEntitiesLive: LiveData<List<SessionRecordEntity>?>

    private lateinit var sessionRecordRepo: SessionRecordRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionRecordDao)
        assertNotNull(mockSessionRecord)
        assertNotNull(sessionRecordEntityLive)
        assertNotNull(sessionEntitiesLive)
        sessionRecordRepo =
            SessionRecordRepositoryImpl(
                mockSessionRecordDao,
                mockSessionRecordConverter
            )
        Mockito.`when`(mockSessionRecordDao.getAllSessionsStartTimeAsc())
            .thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsStartTimeDesc())
            .thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsDurationAsc())
            .thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsDurationDesc())
            .thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsWithMp3()).thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getAllSessionsWithoutMp3())
            .thenReturn(sessionEntitiesLive)
        Mockito.`when`(mockSessionRecordDao.getSessionsSearch(anyString()))
            .thenReturn(sessionEntitiesLive)
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
            sessionRecordRepo.insert(mockSessionRecord)
            Mockito.verify(mockSessionRecordDao).insert(any())
        }
    }

    @Test
    fun insertMultiple() {
        runBlocking {
            sessionRecordRepo.insertMultiple(listOf(mockSessionRecord))
            Mockito.verify(mockSessionRecordDao).insertMultiple(any())
        }
    }

    @Test
    fun updateSession() {
        runBlocking {
            sessionRecordRepo.updateSession(mockSessionRecord)
            Mockito.verify(mockSessionRecordDao).updateSession(any())
        }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionRecordRepo.deleteSession(mockSessionRecord)
            Mockito.verify(mockSessionRecordDao).deleteSession(any())
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
            Mockito.verify(mockSessionRecordDao).getAllSessionsStartTimeAsc()
        }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsStartTimeDesc()
            Mockito.verify(mockSessionRecordDao).getAllSessionsStartTimeDesc()
        }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationAsc()
            Mockito.verify(mockSessionRecordDao).getAllSessionsDurationAsc()
        }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsDurationDesc()
            Mockito.verify(mockSessionRecordDao).getAllSessionsDurationDesc()
        }
    }


    @Test
    fun `try name`() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
            Mockito.verify(mockSessionRecordDao).getAllSessionsWithMp3()
        }
    }

    @Test
    fun getAllSessionsWithMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithMp3()
            Mockito.verify(mockSessionRecordDao).getAllSessionsWithMp3()
        }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        runBlocking {
            sessionRecordRepo.getAllSessionsWithoutMp3()
            Mockito.verify(mockSessionRecordDao).getAllSessionsWithoutMp3()
        }
    }

    @Test
    fun getSessionsSearch() {
        runBlocking {
            sessionRecordRepo.getSessionsSearch("search request")
            Mockito.verify(mockSessionRecordDao).getSessionsSearch(anyString())
        }
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAsc() {
        runBlocking {
            sessionRecordRepo.getAllSessionsNotLiveStartTimeAsc()
            Mockito.verify(mockSessionRecordDao).getAllSessionsNotLiveStartTimeAsc()
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