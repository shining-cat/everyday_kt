package fr.shining_cat.everyday.localdata.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.testutils.dto.SessionDTOTestUtils
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sessionDao: SessionDao

    @Before
    fun setupEmptyTable(){
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sessionDao = EveryDayRoomDatabase.getInstance(appContext).sessionDao()
        emptyTableAndCheck()
    }

    private fun emptyTableAndCheck(){
        runBlocking {
            sessionDao.deleteAllSessions()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int){
        val count = runBlocking {
            sessionDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    @Test
    fun insert() {
        val sessionDTO = SessionDTOTestUtils.generateSessionDTO(desiredId = 25)
        val sessionDTOTestID = runBlocking {
            sessionDao.insert(sessionDTO)
        }
        assertEquals(25L, sessionDTOTestID)
        checkTotalCountIs(1)
    }

    @Test
    fun insertMultiple() {
        val sessionsToInsertList = SessionDTOTestUtils.generateSessions(20)
        val insertedIds = runBlocking {
            sessionDao.insertMultiple(sessionsToInsertList)
        }
        assertEquals(20, insertedIds.size)
        Assert.assertNotNull(insertedIds)
        var i = 1L
        for (id in insertedIds) {
            assertEquals(i, id)
            i++
        }
        checkTotalCountIs(20)
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
    fun getAllSessionsDurationDesc() {
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