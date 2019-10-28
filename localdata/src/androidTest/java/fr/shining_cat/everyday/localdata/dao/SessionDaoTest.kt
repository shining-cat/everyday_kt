package fr.shining_cat.everyday.localdata.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.testutils.dto.SessionDTOTestUtils
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.*

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
////////////////////////////////////////////////////////////////

    @Test
    fun insert() {
        val sessionDTOTestID = runBlocking {
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 25))
        }
        assertEquals(25L, sessionDTOTestID)
        checkTotalCountIs(1)
    }

    @Test
    fun insertMultiple() {
        val insertedIds = runBlocking {
            sessionDao.insertMultiple(SessionDTOTestUtils.generateSessions(20))
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
    fun deleteSessionFromEmptyTable() {
        val sessionDtoToDelete = SessionDTOTestUtils.generateSessionDTO(desiredId = 25)
        //
        checkTotalCountIs(0)
        val countDeleted = runBlocking {
            sessionDao.deleteSession(sessionDtoToDelete)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteNonExistentSession() {
        runBlocking {
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 25))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 26))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 27))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 28))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 29))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 30))
        }
        val sessionDtoToDelete = SessionDTOTestUtils.generateSessionDTO(desiredId = 723)
        //
        checkTotalCountIs(6)
        val countDeleted = runBlocking {
            sessionDao.deleteSession(sessionDtoToDelete)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(6)
    }

    @Test
    fun deleteSession() {
        val sessionDtoToDelete = SessionDTOTestUtils.generateSessionDTO(desiredId = 25)
        runBlocking {
            sessionDao.insert(sessionDtoToDelete)
            sessionDao.insertMultiple(SessionDTOTestUtils.generateSessions(20))
        }
        checkTotalCountIs(21)
        val countDeleted = runBlocking {
            sessionDao.deleteSession(sessionDtoToDelete)
        }
        assertEquals(1, countDeleted)
        checkTotalCountIs(20)
    }

    @Test
    fun deleteAllSessionsOnEmptyTable() {
        checkTotalCountIs(0)
        val numberOfDeletedRows = runBlocking {
            sessionDao.deleteAllSessions()
        }
        assertEquals(0, numberOfDeletedRows)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteAllSessions() {
        runBlocking {
            sessionDao.insertMultiple(SessionDTOTestUtils.generateSessions(73))
        }
        checkTotalCountIs(73)
        val numberOfDeletedRows = runBlocking {
            sessionDao.deleteAllSessions()
        }
        assertEquals(73, numberOfDeletedRows)
        checkTotalCountIs(0)
    }

    @Test
    fun getSessionOnEmptyTableTest() {
        checkTotalCountIs(0)
        val sessionDtoExtractedLive = sessionDao.getSessionLive(75)
        assertNotNull(sessionDtoExtractedLive)
        val sessionDtoExtracted = sessionDtoExtractedLive.getValueBlocking()
        assertNull(sessionDtoExtracted)
    }

    @Test
    fun getNonExistentSessionTest() {
        runBlocking {
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 25))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 26))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 27))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 28))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 29))
        }
        checkTotalCountIs(5)
        val sessionDtoExtractedLive = sessionDao.getSessionLive(73)
        assertNotNull(sessionDtoExtractedLive)
        val sessionDtoExtracted = sessionDtoExtractedLive.getValueBlocking()
        assertNull(sessionDtoExtracted)
    }

    @Test
    fun getSessionTest() {
        val sessionToGetTest = SessionDTOTestUtils.generateSessionDTO(
            desiredId = 73,
            yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32,
            startBodyValue = -1,
            startThoughtsValue = 1,
            startFeelingsValue = 2,
            startGlobalValue = -2,
            yearend = 2004, monthend = 2, dayOfMonthend = 7, hourOfDayend = 15, minuteend = 17, secondend = 51,
            endBodyValue = 1,
            endThoughtsValue = -2,
            endFeelingsValue = 0,
            endGlobalValue = 2,
            notes = "getSessionTest notes",
            realDuration = 123456789,
            pausesCount = 7,
            realDurationVsPlanned = 1,
            guideMp3 = "getSessionTest guideMp3")

        runBlocking {
            sessionDao.insert(sessionToGetTest)
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 25))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 26))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 27))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 28))
            sessionDao.insert(SessionDTOTestUtils.generateSessionDTO(desiredId = 29))
        }
        checkTotalCountIs(6)
        val sessionDtoExtractedLive = sessionDao.getSessionLive(73)
        assertNotNull(sessionDtoExtractedLive)
        val sessionDtoExtracted = sessionDtoExtractedLive.getValueBlocking()
        assertNotNull(sessionDtoExtracted)
        if(sessionDtoExtracted != null){
            assertEquals(73, sessionDtoExtracted.id)
            assertEquals(GregorianCalendar(1623, 3, 5, 22, 21, 32).timeInMillis, sessionDtoExtracted.startTimeOfRecord)
            assertEquals(-1, sessionDtoExtracted.startBodyValue)
            assertEquals(1, sessionDtoExtracted.startThoughtsValue)
            assertEquals(2, sessionDtoExtracted.startFeelingsValue)
            assertEquals(-2, sessionDtoExtracted.startGlobalValue)
            assertEquals(GregorianCalendar(2004, 2, 7, 15, 17, 51).timeInMillis, sessionDtoExtracted.endTimeOfRecord)
            assertEquals(1, sessionDtoExtracted.endBodyValue)
            assertEquals(-2, sessionDtoExtracted.endThoughtsValue)
            assertEquals(0, sessionDtoExtracted.endFeelingsValue)
            assertEquals(2, sessionDtoExtracted.endGlobalValue)
            assertEquals("getSessionTest notes", sessionDtoExtracted.notes)
            assertEquals( 123456789, sessionDtoExtracted.realDuration)
            assertEquals(7, sessionDtoExtracted.pausesCount)
            assertEquals( 1, sessionDtoExtracted.realDurationVsPlanned)
            assertEquals("getSessionTest guideMp3", sessionDtoExtracted.guideMp3)
        }
    }

    @Test
    fun updateNonExistentSession() {
        val sessionDtoToUpdate = SessionDTOTestUtils.generateSessionDTO(desiredId = 25)
        runBlocking {
            sessionDao.insertMultiple(SessionDTOTestUtils.generateSessions(20))
        }
        checkTotalCountIs(20)
        val numberOfUpdatedItems = runBlocking {
            sessionDao.updateSession(sessionDtoToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(20)
    }

    @Test
    fun updateSessionOnEmptyTable() {
        val sessionDtoToUpdate = SessionDTOTestUtils.generateSessionDTO(desiredId = 25)
        checkTotalCountIs(0)
        val numberOfUpdatedItems = runBlocking {
            sessionDao.updateSession(sessionDtoToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(0)
    }

    @Test
    fun updateSession() {
        val sessionDtoToUpdate = SessionDTOTestUtils.generateSessionDTO(
            desiredId = 73,
            yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32,
            startBodyValue = -1,
            startThoughtsValue = 1,
            startFeelingsValue = 2,
            startGlobalValue = -2,
            yearend = 2004, monthend = 2, dayOfMonthend = 7, hourOfDayend = 15, minuteend = 17, secondend = 51,
            endBodyValue = 1,
            endThoughtsValue = -2,
            endFeelingsValue = 0,
            endGlobalValue = 2,
            notes = "updateSession notes",
            realDuration = 123456789,
            pausesCount = 7,
            realDurationVsPlanned = 1,
            guideMp3 = "updateSession guideMp3")

        runBlocking {
            sessionDao.insert(sessionDtoToUpdate)
            sessionDao.insertMultiple(SessionDTOTestUtils.generateSessions(20))
        }
        checkTotalCountIs(21)
        sessionDtoToUpdate.startTimeOfRecord = GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis
        sessionDtoToUpdate.startBodyValue = 2
        sessionDtoToUpdate.startThoughtsValue = 1
        sessionDtoToUpdate.startFeelingsValue = 0
        sessionDtoToUpdate.startGlobalValue = -1
        sessionDtoToUpdate.endTimeOfRecord = GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis
        sessionDtoToUpdate.endBodyValue = -1
        sessionDtoToUpdate.endThoughtsValue = -1
        sessionDtoToUpdate.endFeelingsValue = 2
        sessionDtoToUpdate.endGlobalValue = 1
        sessionDtoToUpdate.notes = "updateSession notes UPDATED"
        sessionDtoToUpdate.realDuration = 987654321
        sessionDtoToUpdate.pausesCount = 1
        sessionDtoToUpdate.realDurationVsPlanned = -1
        sessionDtoToUpdate.guideMp3 = "updateSession guideMp3 UPDATED"
        val numberOfUpdatedItems = runBlocking {
            sessionDao.updateSession(sessionDtoToUpdate)
        }
        assertEquals(1, numberOfUpdatedItems)
        checkTotalCountIs(21)
        val sessionDtoExtractedLive = sessionDao.getSessionLive(73)
        assertNotNull(sessionDtoExtractedLive)
        val sessionDtoExtracted = sessionDtoExtractedLive.getValueBlocking()
        assertNotNull(sessionDtoExtracted)
        if(sessionDtoExtracted != null){
            assertEquals(73, sessionDtoExtracted.id)
            assertEquals(GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis, sessionDtoExtracted.startTimeOfRecord)
            assertEquals(2, sessionDtoExtracted.startBodyValue)
            assertEquals(1, sessionDtoExtracted.startThoughtsValue)
            assertEquals(0, sessionDtoExtracted.startFeelingsValue)
            assertEquals(-1, sessionDtoExtracted.startGlobalValue)
            assertEquals(GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis, sessionDtoExtracted.endTimeOfRecord)
            assertEquals(-1, sessionDtoExtracted.endBodyValue)
            assertEquals(-1, sessionDtoExtracted.endThoughtsValue)
            assertEquals(2, sessionDtoExtracted.endFeelingsValue)
            assertEquals(1, sessionDtoExtracted.endGlobalValue)
            assertEquals("updateSession notes UPDATED", sessionDtoExtracted.notes)
            assertEquals( 987654321, sessionDtoExtracted.realDuration)
            assertEquals(1, sessionDtoExtracted.pausesCount)
            assertEquals( -1, sessionDtoExtracted.realDurationVsPlanned)
            assertEquals("updateSession guideMp3 UPDATED", sessionDtoExtracted.guideMp3)
        }
    }

    @Test
    fun getAllSessionsStartTimeAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1623),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2013),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1953),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1733),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2003),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1843)
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(6, sessionDtosSorted.size)
            var date = 0L
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].startTimeOfRecord >= date)
                date = sessionDtosSorted[i].startTimeOfRecord
                assertEquals(date, sessionDtosSorted[i].startTimeOfRecord)
            }
        }
    }

    @Test
    fun getAllSessionsStartTimeDescOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1623),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2013),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1843),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1953),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1733),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2003)
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(6, sessionDtosSorted.size)
            var date = sessionDtosSorted[sessionDtosSorted.size - 1].startTimeOfRecord
            for(i in 4 downTo 0){
                assert(sessionDtosSorted[i].startTimeOfRecord <= date)
                date = sessionDtosSorted[i].startTimeOfRecord
                assertEquals(date, sessionDtosSorted[i].startTimeOfRecord)
            }
        }
    }

    @Test
    fun getAllSessionsDurationAscOnEmptyTable() {
       checkTotalCountIs(0)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(realDuration = 123L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 234L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 345L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 456L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 567L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 578L)
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(6, sessionDtosSorted.size)
            var duration = 0L
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].realDuration >= duration)
                duration = sessionDtosSorted[i].realDuration
                assertEquals(duration, sessionDtosSorted[i].realDuration)
            }
        }
    }

    @Test
    fun getAllSessionsDurationDescOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(realDuration = 123L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 234L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 345L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 456L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 567L),
            SessionDTOTestUtils.generateSessionDTO(realDuration = 578L)
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(6, sessionDtosSorted.size)
            var duration = sessionDtosSorted[sessionDtosSorted.size - 1].realDuration
            for(i in 4 downTo 0){
                assert(sessionDtosSorted[i].realDuration <= duration)
                duration = sessionDtosSorted[i].realDuration
                assertEquals(duration, sessionDtosSorted[i].realDuration)
            }
        }
    }

    @Test
    fun getAllSessionsWithMp3OnEmptyTable() {
        checkTotalCountIs(0)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsWithMp3()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsWithMp3() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = ""),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 1"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = ""),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 2"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = ""),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 3"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "")
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(7)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsWithMp3()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(3, sessionDtosSorted.size)
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].guideMp3 != "")
            }
        }
    }

    @Test
    fun getAllSessionsWithoutMp3OnEmptyTable() {
        checkTotalCountIs(0)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsWithoutMp3()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = ""),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 1"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = ""),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 2"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = ""),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 3"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "")
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(7)
        val sessionDtosExtractedLive = sessionDao.getAllSessionsWithoutMp3()
        assertNotNull(sessionDtosExtractedLive)
        val sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(4, sessionDtosSorted.size)
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].guideMp3 == "")
            }
        }
    }

    @Test
    fun getSessionsSearchOnEmptyTable() {
        checkTotalCountIs(0)
        //
        var sessionDtosExtractedLive = sessionDao.getSessionsSearch("test 1")
        assertNotNull(sessionDtosExtractedLive)
        var sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }

    }

    @Test
    fun getSessionsSearch() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(notes = "test 1"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "test 1"),
            SessionDTOTestUtils.generateSessionDTO(notes = "notes test 2"),
            SessionDTOTestUtils.generateSessionDTO(notes = "notes test 2"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 2"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 2"),
            SessionDTOTestUtils.generateSessionDTO(notes = "notes test 3"),
            SessionDTOTestUtils.generateSessionDTO(guideMp3 = "guide mp3 test 3")
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
            sessionDao.insertMultiple(SessionDTOTestUtils.generateSessions(5))
        }
        checkTotalCountIs(13)
        //
        var sessionDtosExtractedLive = sessionDao.getSessionsSearch("test 1")
        assertNotNull(sessionDtosExtractedLive)
        var sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(2, sessionDtosSorted.size)
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].guideMp3.contains("test 1") || sessionDtosSorted[i].notes.contains("test 1"))
            }
        }
        //
        sessionDtosExtractedLive = sessionDao.getSessionsSearch("test")
        assertNotNull(sessionDtosExtractedLive)
        sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(8, sessionDtosSorted.size)
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].guideMp3.contains("test") || sessionDtosSorted[i].notes.contains("test"))
            }
        }
        //
        sessionDtosExtractedLive = sessionDao.getSessionsSearch("2")
        assertNotNull(sessionDtosExtractedLive)
        sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(4, sessionDtosSorted.size)
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].guideMp3.contains("2") || sessionDtosSorted[i].notes.contains("2"))
            }
        }
        //
        sessionDtosExtractedLive = sessionDao.getSessionsSearch("notes test 2")
        assertNotNull(sessionDtosExtractedLive)
        sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(2, sessionDtosSorted.size)
            for(i in sessionDtosSorted.indices) {
                assert(sessionDtosSorted[i].guideMp3.contains("notes test 2") || sessionDtosSorted[i].notes.contains("notes test 2"))
            }
        }
        //
        sessionDtosExtractedLive = sessionDao.getSessionsSearch("tralala test")
        assertNotNull(sessionDtosExtractedLive)
        sessionDtosSorted = sessionDtosExtractedLive.getValueBlocking()
        assertNotNull(sessionDtosSorted)
        if(sessionDtosSorted != null){
            assertEquals(0, sessionDtosSorted.size)
        }
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionDtosExtracted = runBlocking {
            sessionDao.getAllSessionsNotLiveStartTimeAsc()
        }
        assertNotNull(sessionDtosExtracted)
        if(sessionDtosExtracted != null){
            assertEquals(0, sessionDtosExtracted.size)
        }
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAsc() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1623),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2003),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1733),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1843),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2013),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1953)
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionDtosExtracted = runBlocking {
            sessionDao.getAllSessionsNotLiveStartTimeAsc()
        }
        assertNotNull(sessionDtosExtracted)
        if(sessionDtosExtracted != null){
            assertEquals(6, sessionDtosExtracted.size)
            var date = 0L
            for(i in sessionDtosExtracted.indices) {
                assert(sessionDtosExtracted[i].startTimeOfRecord >= date)
                date = sessionDtosExtracted[i].startTimeOfRecord
                assertEquals(date, sessionDtosExtracted[i].startTimeOfRecord)
            }
        }
    }

    @Test
    fun getLatestRecordedSessionDateOnEmptyTable() {
        checkTotalCountIs(0)
        val latestRecordedSessionDate = runBlocking {
            sessionDao.getLatestRecordedSessionDate()
        }
        assertNull(latestRecordedSessionDate)
    }

    @Test
    fun getLatestRecordedSessionDate() {
        val sessionToInsertList = listOf(
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2013, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1733, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1953, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 2003, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            SessionDTOTestUtils.generateSessionDTO(yearstart = 1843, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32)
        )
        runBlocking {
            sessionDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val latestRecordedSessionDate = runBlocking {
            sessionDao.getLatestRecordedSessionDate()
        }
        assertNotNull(latestRecordedSessionDate)
        assertEquals(GregorianCalendar(2013, 3, 5, 22, 21, 32).timeInMillis, latestRecordedSessionDate)
    }
}