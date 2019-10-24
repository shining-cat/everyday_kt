package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.localdata.dao.SessionDao
import fr.shining_cat.everyday.localdata.dto.SessionDTO
import fr.shining_cat.everyday.testutils.model.SessionModelTestUtils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionRepositoryTest: AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionDao: SessionDao

    @Mock
    lateinit var sessionDTOLive: LiveData<SessionDTO?>

    @Mock
    lateinit var sessionDTOsLive: LiveData<List<SessionDTO>?>

    private lateinit var sessionRepo: SessionRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionDao)
        assertNotNull(sessionDTOLive)
        assertNotNull(sessionDTOsLive)
        sessionRepo = SessionRepository(mockSessionDao)
        Mockito.`when`(mockSessionDao.getAllSessionsStartTimeAsc()).thenReturn(sessionDTOsLive)
        Mockito.`when`(mockSessionDao.getAllSessionsStartTimeDesc()).thenReturn(sessionDTOsLive)
        Mockito.`when`(mockSessionDao.getAllSessionsDurationAsc()).thenReturn(sessionDTOsLive)
        Mockito.`when`(mockSessionDao.getAllSessionsDurationDesc()).thenReturn(sessionDTOsLive)
        Mockito.`when`(mockSessionDao.getAllSessionsWithMp3()).thenReturn(sessionDTOsLive)
        Mockito.`when`(mockSessionDao.getAllSessionsWithoutMp3()).thenReturn(sessionDTOsLive)
        Mockito.`when`(mockSessionDao.getSessionsSearch(anyString())).thenReturn(sessionDTOsLive)
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
            sessionRepo.insert(SessionModelTestUtils.generateSession())
            Mockito.verify(mockSessionDao).insert(any())
        }
    }

    @Test
    fun insertMultiple() {
        runBlocking {
            sessionRepo.insertMultiple(SessionModelTestUtils.generateSessions(23))
            Mockito.verify(mockSessionDao).insertMultiple(any())
        }
    }

    @Test
    fun updateSession() {
        runBlocking {
            sessionRepo.updateSession(SessionModelTestUtils.generateSession())
            Mockito.verify(mockSessionDao).updateSession(any())
        }
    }

    @Test
    fun deleteSession() {
        runBlocking {
            sessionRepo.deleteSession(SessionModelTestUtils.generateSession())
            Mockito.verify(mockSessionDao).deleteSession(any())
        }
    }

    @Test
    fun deleteAllSessions() {
        runBlocking {
            sessionRepo.deleteAllSessions()
            Mockito.verify(mockSessionDao).deleteAllSessions()
        }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        runBlocking {
            sessionRepo.getAllSessionsStartTimeAsc()
            Mockito.verify(mockSessionDao).getAllSessionsStartTimeAsc()
        }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        runBlocking {
            sessionRepo.getAllSessionsStartTimeDesc()
            Mockito.verify(mockSessionDao).getAllSessionsStartTimeDesc()
        }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        runBlocking {
            sessionRepo.getAllSessionsDurationAsc()
            Mockito.verify(mockSessionDao).getAllSessionsDurationAsc()
        }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        runBlocking {
            sessionRepo.getAllSessionsDurationDesc()
            Mockito.verify(mockSessionDao).getAllSessionsDurationDesc()
        }
    }


    @Test
    fun getAllSessionsWithMp3() {
        runBlocking {
            sessionRepo.getAllSessionsWithMp3()
            Mockito.verify(mockSessionDao).getAllSessionsWithMp3()
        }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        runBlocking {
            sessionRepo.getAllSessionsWithoutMp3()
            Mockito.verify(mockSessionDao).getAllSessionsWithoutMp3()
        }
    }

    @Test
    fun getSessionsSearch() {
        runBlocking {
            sessionRepo.getSessionsSearch("search request")
            Mockito.verify(mockSessionDao).getSessionsSearch(anyString())
        }
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAsc() {
        runBlocking {
            sessionRepo.getAllSessionsNotLiveStartTimeAsc()
            Mockito.verify(mockSessionDao).getAllSessionsNotLiveStartTimeAsc()
        }
    }

    @Test
    fun getLatestRecordedSessionDate() {
        runBlocking {
            sessionRepo.getLatestRecordedSessionDate()
            Mockito.verify(mockSessionDao).getLatestRecordedSessionDate()
        }
    }
}