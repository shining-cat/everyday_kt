package fr.shining_cat.everyday.repository

import fr.shining_cat.everyday.localdata.dao.SessionDao
import fr.shining_cat.everyday.localdata.dto.SessionDTO
import fr.shining_cat.everyday.testutils.model.SessionModelTestUtils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SessionRepositoryTest: AbstractBaseTest() {

    @Mock
    private lateinit var mockSessionDao: SessionDao

    private lateinit var sessionRepo: SessionRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertNotNull(mockSessionDao)
        sessionRepo = SessionRepository(mockSessionDao)
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
            sessionRepo.insert(SessionModelTestUtils.sessionModelTestA)
            Mockito.verify(mockSessionDao).insert(any<SessionDTO>())
        }
    }

    @Test
    fun insertMultiple() {
        fail("TEST TO WRITE")
    }

    @Test
    fun updateSession() {
        fail("TEST TO WRITE")
    }

    @Test
    fun deleteSession() {
        fail("TEST TO WRITE")
    }

    @Test
    fun deleteAllSessions() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getAllSessionsDurationAsc() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getAllSessionsWithMp3() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getSessionsSearch() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAsc() {
        fail("TEST TO WRITE")
    }

    @Test
    fun getLatestRecordedSessionDate() {
        fail("TEST TO WRITE")
    }
}