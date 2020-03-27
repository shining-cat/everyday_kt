package fr.shining_cat.everyday.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionRecordDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sessionRecordDao: SessionRecordDao

    @Before
    fun setupEmptyTable(){
//        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sessionRecordDao = EveryDayRoomDatabase.getInstance(appContext).sessionDao()
//        emptyTableAndCheck()
    }
    @Test
    fun tests(){
        Assert.fail("TODO: write correct tests")
    }
//    private fun emptyTableAndCheck(){
//        runBlocking {
//            sessionRecordDao.deleteAllSessions()
//        }
//        checkTotalCountIs(0)
//    }
//
//    private fun checkTotalCountIs(expectedCount: Int){
//        val count = runBlocking {
//            sessionRecordDao.getNumberOfRows()
//        }
//        assertEquals(expectedCount, count)
//    }
//
//    @After
//    fun tearDown() {
//        EveryDayRoomDatabase.closeAndDestroy()
//    }
//////////////////////////////////////////////////////////////////
//
//    @Test
//    fun insert() {
//        val sessionEntityTestID = runBlocking {
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 25))
//        }
//        assertEquals(25L, sessionEntityTestID)
//        checkTotalCountIs(1)
//    }
//
//    @Test
//    fun insertMultiple() {
//        val insertedIds = runBlocking {
//            sessionRecordDao.insertMultiple(SessionEntityTestUtils.generateSessions(20))
//        }
//        assertEquals(20, insertedIds.size)
//        Assert.assertNotNull(insertedIds)
//        var i = 1L
//        for (id in insertedIds) {
//            assertEquals(i, id)
//            i++
//        }
//        checkTotalCountIs(20)
//    }
//
//    @Test
//    fun deleteSessionFromEmptyTable() {
//        val sessionEntityToDelete = SessionEntityTestUtils.generateSessionEntity(desiredId = 25)
//        //
//        checkTotalCountIs(0)
//        val countDeleted = runBlocking {
//            sessionRecordDao.deleteSession(sessionEntityToDelete)
//        }
//        assertEquals(0, countDeleted)
//        checkTotalCountIs(0)
//    }
//
//    @Test
//    fun deleteNonExistentSession() {
//        runBlocking {
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 25))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 26))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 27))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 28))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 29))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 30))
//        }
//        val sessionEntityToDelete = SessionEntityTestUtils.generateSessionEntity(desiredId = 723)
//        //
//        checkTotalCountIs(6)
//        val countDeleted = runBlocking {
//            sessionRecordDao.deleteSession(sessionEntityToDelete)
//        }
//        assertEquals(0, countDeleted)
//        checkTotalCountIs(6)
//    }
//
//    @Test
//    fun deleteSession() {
//        val sessionEntityToDelete = SessionEntityTestUtils.generateSessionEntity(desiredId = 25)
//        runBlocking {
//            sessionRecordDao.insert(sessionEntityToDelete)
//            sessionRecordDao.insertMultiple(SessionEntityTestUtils.generateSessions(20))
//        }
//        checkTotalCountIs(21)
//        val countDeleted = runBlocking {
//            sessionRecordDao.deleteSession(sessionEntityToDelete)
//        }
//        assertEquals(1, countDeleted)
//        checkTotalCountIs(20)
//    }
//
//    @Test
//    fun deleteAllSessionsOnEmptyTable() {
//        checkTotalCountIs(0)
//        val numberOfDeletedRows = runBlocking {
//            sessionRecordDao.deleteAllSessions()
//        }
//        assertEquals(0, numberOfDeletedRows)
//        checkTotalCountIs(0)
//    }
//
//    @Test
//    fun deleteAllSessions() {
//        runBlocking {
//            sessionRecordDao.insertMultiple(SessionEntityTestUtils.generateSessions(73))
//        }
//        checkTotalCountIs(73)
//        val numberOfDeletedRows = runBlocking {
//            sessionRecordDao.deleteAllSessions()
//        }
//        assertEquals(73, numberOfDeletedRows)
//        checkTotalCountIs(0)
//    }
//
//    @Test
//    fun getSessionOnEmptyTableTest() {
//        checkTotalCountIs(0)
//        val sessionEntityExtractedLive = sessionRecordDao.getSessionLive(75)
//        assertNotNull(sessionEntityExtractedLive)
//        val sessionEntityExtracted = sessionEntityExtractedLive.getValueBlocking()
//        assertNull(sessionEntityExtracted)
//    }
//
//    @Test
//    fun getNonExistentSessionTest() {
//        runBlocking {
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 25))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 26))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 27))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 28))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 29))
//        }
//        checkTotalCountIs(5)
//        val sessionEntityExtractedLive = sessionRecordDao.getSessionLive(73)
//        assertNotNull(sessionEntityExtractedLive)
//        val sessionEntityExtracted = sessionEntityExtractedLive.getValueBlocking()
//        assertNull(sessionEntityExtracted)
//    }
//
//    @Test
//    fun getSessionTest() {
//        val sessionToGetTest = SessionEntityTestUtils.generateSessionEntity(
//            desiredId = 73,
//            yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32,
//            startBodyValue = -1,
//            startThoughtsValue = 1,
//            startFeelingsValue = 2,
//            startGlobalValue = -2,
//            yearend = 2004, monthend = 2, dayOfMonthend = 7, hourOfDayend = 15, minuteend = 17, secondend = 51,
//            endBodyValue = 1,
//            endThoughtsValue = -2,
//            endFeelingsValue = 0,
//            endGlobalValue = 2,
//            notes = "getSessionTest notes",
//            realDuration = 123456789,
//            pausesCount = 7,
//            realDurationVsPlanned = 1,
//            guideMp3 = "getSessionTest guideMp3")
//
//        runBlocking {
//            sessionRecordDao.insert(sessionToGetTest)
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 25))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 26))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 27))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 28))
//            sessionRecordDao.insert(SessionEntityTestUtils.generateSessionEntity(desiredId = 29))
//        }
//        checkTotalCountIs(6)
//        val sessionEntityExtractedLive = sessionRecordDao.getSessionLive(73)
//        assertNotNull(sessionEntityExtractedLive)
//        val sessionEntityExtracted = sessionEntityExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntityExtracted)
//        if(sessionEntityExtracted != null){
//            assertEquals(73, sessionEntityExtracted.id)
//            assertEquals(GregorianCalendar(1623, 3, 5, 22, 21, 32).timeInMillis, sessionEntityExtracted.startTimeOfRecord)
//            assertEquals(-1, sessionEntityExtracted.startBodyValue)
//            assertEquals(1, sessionEntityExtracted.startThoughtsValue)
//            assertEquals(2, sessionEntityExtracted.startFeelingsValue)
//            assertEquals(-2, sessionEntityExtracted.startGlobalValue)
//            assertEquals(GregorianCalendar(2004, 2, 7, 15, 17, 51).timeInMillis, sessionEntityExtracted.endTimeOfRecord)
//            assertEquals(1, sessionEntityExtracted.endBodyValue)
//            assertEquals(-2, sessionEntityExtracted.endThoughtsValue)
//            assertEquals(0, sessionEntityExtracted.endFeelingsValue)
//            assertEquals(2, sessionEntityExtracted.endGlobalValue)
//            assertEquals("getSessionTest notes", sessionEntityExtracted.notes)
//            assertEquals( 123456789, sessionEntityExtracted.realDuration)
//            assertEquals(7, sessionEntityExtracted.pausesCount)
//            assertEquals( 1, sessionEntityExtracted.realDurationVsPlanned)
//            assertEquals("getSessionTest guideMp3", sessionEntityExtracted.guideMp3)
//        }
//    }
//
//    @Test
//    fun updateNonExistentSession() {
//        val sessionEntityToUpdate = SessionEntityTestUtils.generateSessionEntity(desiredId = 25)
//        runBlocking {
//            sessionRecordDao.insertMultiple(SessionEntityTestUtils.generateSessions(20))
//        }
//        checkTotalCountIs(20)
//        val numberOfUpdatedItems = runBlocking {
//            sessionRecordDao.updateSession(sessionEntityToUpdate)
//        }
//        assertEquals(0, numberOfUpdatedItems)
//        checkTotalCountIs(20)
//    }
//
//    @Test
//    fun updateSessionOnEmptyTable() {
//        val sessionEntityToUpdate = SessionEntityTestUtils.generateSessionEntity(desiredId = 25)
//        checkTotalCountIs(0)
//        val numberOfUpdatedItems = runBlocking {
//            sessionRecordDao.updateSession(sessionEntityToUpdate)
//        }
//        assertEquals(0, numberOfUpdatedItems)
//        checkTotalCountIs(0)
//    }
//
//    @Test
//    fun updateSession() {
//        val sessionEntityToUpdate = SessionEntityTestUtils.generateSessionEntity(
//            desiredId = 73,
//            yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32,
//            startBodyValue = -1,
//            startThoughtsValue = 1,
//            startFeelingsValue = 2,
//            startGlobalValue = -2,
//            yearend = 2004, monthend = 2, dayOfMonthend = 7, hourOfDayend = 15, minuteend = 17, secondend = 51,
//            endBodyValue = 1,
//            endThoughtsValue = -2,
//            endFeelingsValue = 0,
//            endGlobalValue = 2,
//            notes = "updateSession notes",
//            realDuration = 123456789,
//            pausesCount = 7,
//            realDurationVsPlanned = 1,
//            guideMp3 = "updateSession guideMp3")
//
//        runBlocking {
//            sessionRecordDao.insert(sessionEntityToUpdate)
//            sessionRecordDao.insertMultiple(SessionEntityTestUtils.generateSessions(20))
//        }
//        checkTotalCountIs(21)
//        sessionEntityToUpdate.startTimeOfRecord = GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis
//        sessionEntityToUpdate.startBodyValue = 2
//        sessionEntityToUpdate.startThoughtsValue = 1
//        sessionEntityToUpdate.startFeelingsValue = 0
//        sessionEntityToUpdate.startGlobalValue = -1
//        sessionEntityToUpdate.endTimeOfRecord = GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis
//        sessionEntityToUpdate.endBodyValue = -1
//        sessionEntityToUpdate.endThoughtsValue = -1
//        sessionEntityToUpdate.endFeelingsValue = 2
//        sessionEntityToUpdate.endGlobalValue = 1
//        sessionEntityToUpdate.notes = "updateSession notes UPDATED"
//        sessionEntityToUpdate.realDuration = 987654321
//        sessionEntityToUpdate.pausesCount = 1
//        sessionEntityToUpdate.realDurationVsPlanned = -1
//        sessionEntityToUpdate.guideMp3 = "updateSession guideMp3 UPDATED"
//        val numberOfUpdatedItems = runBlocking {
//            sessionRecordDao.updateSession(sessionEntityToUpdate)
//        }
//        assertEquals(1, numberOfUpdatedItems)
//        checkTotalCountIs(21)
//        val sessionEntityExtractedLive = sessionRecordDao.getSessionLive(73)
//        assertNotNull(sessionEntityExtractedLive)
//        val sessionEntityExtracted = sessionEntityExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntityExtracted)
//        if(sessionEntityExtracted != null){
//            assertEquals(73, sessionEntityExtracted.id)
//            assertEquals(GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis, sessionEntityExtracted.startTimeOfRecord)
//            assertEquals(2, sessionEntityExtracted.startBodyValue)
//            assertEquals(1, sessionEntityExtracted.startThoughtsValue)
//            assertEquals(0, sessionEntityExtracted.startFeelingsValue)
//            assertEquals(-1, sessionEntityExtracted.startGlobalValue)
//            assertEquals(GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis, sessionEntityExtracted.endTimeOfRecord)
//            assertEquals(-1, sessionEntityExtracted.endBodyValue)
//            assertEquals(-1, sessionEntityExtracted.endThoughtsValue)
//            assertEquals(2, sessionEntityExtracted.endFeelingsValue)
//            assertEquals(1, sessionEntityExtracted.endGlobalValue)
//            assertEquals("updateSession notes UPDATED", sessionEntityExtracted.notes)
//            assertEquals( 987654321, sessionEntityExtracted.realDuration)
//            assertEquals(1, sessionEntityExtracted.pausesCount)
//            assertEquals( -1, sessionEntityExtracted.realDurationVsPlanned)
//            assertEquals("updateSession guideMp3 UPDATED", sessionEntityExtracted.guideMp3)
//        }
//    }
//
//    @Test
//    fun getAllSessionsStartTimeAscOnEmptyTable() {
//        checkTotalCountIs(0)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsStartTimeAsc() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1623),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2013),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1953),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1733),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2003),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1843)
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(6)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(6, sessionEntitysSorted.size)
//            var date = 0L
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].startTimeOfRecord >= date)
//                date = sessionEntitysSorted[i].startTimeOfRecord
//                assertEquals(date, sessionEntitysSorted[i].startTimeOfRecord)
//            }
//        }
//    }
//
//    @Test
//    fun getAllSessionsStartTimeDescOnEmptyTable() {
//        checkTotalCountIs(0)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsStartTimeDesc() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1623),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2013),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1843),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1953),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1733),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2003)
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(6)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(6, sessionEntitysSorted.size)
//            var date = sessionEntitysSorted[sessionEntitysSorted.size - 1].startTimeOfRecord
//            for(i in 4 downTo 0){
//                assert(sessionEntitysSorted[i].startTimeOfRecord <= date)
//                date = sessionEntitysSorted[i].startTimeOfRecord
//                assertEquals(date, sessionEntitysSorted[i].startTimeOfRecord)
//            }
//        }
//    }
//
//    @Test
//    fun getAllSessionsDurationAscOnEmptyTable() {
//       checkTotalCountIs(0)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsDurationAsc() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 123L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 234L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 345L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 456L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 567L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 578L)
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(6)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(6, sessionEntitysSorted.size)
//            var duration = 0L
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].realDuration >= duration)
//                duration = sessionEntitysSorted[i].realDuration
//                assertEquals(duration, sessionEntitysSorted[i].realDuration)
//            }
//        }
//    }
//
//    @Test
//    fun getAllSessionsDurationDescOnEmptyTable() {
//        checkTotalCountIs(0)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsDurationDesc() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 123L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 234L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 345L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 456L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 567L),
//            SessionEntityTestUtils.generateSessionEntity(realDuration = 578L)
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(6)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(6, sessionEntitysSorted.size)
//            var duration = sessionEntitysSorted[sessionEntitysSorted.size - 1].realDuration
//            for(i in 4 downTo 0){
//                assert(sessionEntitysSorted[i].realDuration <= duration)
//                duration = sessionEntitysSorted[i].realDuration
//                assertEquals(duration, sessionEntitysSorted[i].realDuration)
//            }
//        }
//    }
//
//    @Test
//    fun getAllSessionsWithMp3OnEmptyTable() {
//        checkTotalCountIs(0)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsWithMp3()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsWithMp3() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = ""),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 1"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = ""),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 2"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = ""),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 3"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "")
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(7)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsWithMp3()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(3, sessionEntitysSorted.size)
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].guideMp3 != "")
//            }
//        }
//    }
//
//    @Test
//    fun getAllSessionsWithoutMp3OnEmptyTable() {
//        checkTotalCountIs(0)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsWithoutMp3()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsWithoutMp3() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = ""),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 1"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = ""),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 2"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = ""),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 3"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "")
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(7)
//        val sessionEntitysExtractedLive = sessionRecordDao.getAllSessionsWithoutMp3()
//        assertNotNull(sessionEntitysExtractedLive)
//        val sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(4, sessionEntitysSorted.size)
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].guideMp3 == "")
//            }
//        }
//    }
//
//    @Test
//    fun getSessionsSearchOnEmptyTable() {
//        checkTotalCountIs(0)
//        //
//        var sessionEntitysExtractedLive = sessionRecordDao.getSessionsSearch("test 1")
//        assertNotNull(sessionEntitysExtractedLive)
//        var sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//
//    }
//
//    @Test
//    fun getSessionsSearch() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(notes = "test 1"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "test 1"),
//            SessionEntityTestUtils.generateSessionEntity(notes = "notes test 2"),
//            SessionEntityTestUtils.generateSessionEntity(notes = "notes test 2"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 2"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 2"),
//            SessionEntityTestUtils.generateSessionEntity(notes = "notes test 3"),
//            SessionEntityTestUtils.generateSessionEntity(guideMp3 = "guide mp3 test 3")
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//            sessionRecordDao.insertMultiple(SessionEntityTestUtils.generateSessions(5))
//        }
//        checkTotalCountIs(13)
//        //
//        var sessionEntitysExtractedLive = sessionRecordDao.getSessionsSearch("test 1")
//        assertNotNull(sessionEntitysExtractedLive)
//        var sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(2, sessionEntitysSorted.size)
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].guideMp3.contains("test 1") || sessionEntitysSorted[i].notes.contains("test 1"))
//            }
//        }
//        //
//        sessionEntitysExtractedLive = sessionRecordDao.getSessionsSearch("test")
//        assertNotNull(sessionEntitysExtractedLive)
//        sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(8, sessionEntitysSorted.size)
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].guideMp3.contains("test") || sessionEntitysSorted[i].notes.contains("test"))
//            }
//        }
//        //
//        sessionEntitysExtractedLive = sessionRecordDao.getSessionsSearch("2")
//        assertNotNull(sessionEntitysExtractedLive)
//        sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(4, sessionEntitysSorted.size)
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].guideMp3.contains("2") || sessionEntitysSorted[i].notes.contains("2"))
//            }
//        }
//        //
//        sessionEntitysExtractedLive = sessionRecordDao.getSessionsSearch("notes test 2")
//        assertNotNull(sessionEntitysExtractedLive)
//        sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(2, sessionEntitysSorted.size)
//            for(i in sessionEntitysSorted.indices) {
//                assert(sessionEntitysSorted[i].guideMp3.contains("notes test 2") || sessionEntitysSorted[i].notes.contains("notes test 2"))
//            }
//        }
//        //
//        sessionEntitysExtractedLive = sessionRecordDao.getSessionsSearch("tralala test")
//        assertNotNull(sessionEntitysExtractedLive)
//        sessionEntitysSorted = sessionEntitysExtractedLive.getValueBlocking()
//        assertNotNull(sessionEntitysSorted)
//        if(sessionEntitysSorted != null){
//            assertEquals(0, sessionEntitysSorted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsNotLiveStartTimeAscOnEmptyTable() {
//        checkTotalCountIs(0)
//        val sessionEntitysExtracted = runBlocking {
//            sessionRecordDao.getAllSessionsNotLiveStartTimeAsc()
//        }
//        assertNotNull(sessionEntitysExtracted)
//        if(sessionEntitysExtracted != null){
//            assertEquals(0, sessionEntitysExtracted.size)
//        }
//    }
//
//    @Test
//    fun getAllSessionsNotLiveStartTimeAsc() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1623),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2003),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1733),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1843),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2013),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1953)
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(6)
//        val sessionEntitysExtracted = runBlocking {
//            sessionRecordDao.getAllSessionsNotLiveStartTimeAsc()
//        }
//        assertNotNull(sessionEntitysExtracted)
//        if(sessionEntitysExtracted != null){
//            assertEquals(6, sessionEntitysExtracted.size)
//            var date = 0L
//            for(i in sessionEntitysExtracted.indices) {
//                assert(sessionEntitysExtracted[i].startTimeOfRecord >= date)
//                date = sessionEntitysExtracted[i].startTimeOfRecord
//                assertEquals(date, sessionEntitysExtracted[i].startTimeOfRecord)
//            }
//        }
//    }
//
//    @Test
//    fun getLatestRecordedSessionDateOnEmptyTable() {
//        checkTotalCountIs(0)
//        val latestRecordedSessionDate = runBlocking {
//            sessionRecordDao.getLatestRecordedSessionDate()
//        }
//        assertNull(latestRecordedSessionDate)
//    }
//
//    @Test
//    fun getLatestRecordedSessionDate() {
//        val sessionToInsertList = listOf(
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2013, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1733, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1953, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 2003, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
//            SessionEntityTestUtils.generateSessionEntity(yearstart = 1843, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32)
//        )
//        runBlocking {
//            sessionRecordDao.insertMultiple(sessionToInsertList)
//        }
//        checkTotalCountIs(6)
//        val latestRecordedSessionDate = runBlocking {
//            sessionRecordDao.getLatestRecordedSessionDate()
//        }
//        assertNotNull(latestRecordedSessionDate)
//        assertEquals(GregorianCalendar(2013, 3, 5, 22, 21, 32).timeInMillis, latestRecordedSessionDate)
//    }
}