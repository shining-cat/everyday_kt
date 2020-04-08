package fr.shining_cat.everyday.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class SessionRecordDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sessionRecordDao: SessionRecordDao

    @Before
    fun setupEmptyTable(){
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sessionRecordDao = EveryDayRoomDatabase.getInstance(appContext).sessionDao()
        emptyTableAndCheck()
    }

    private fun emptyTableAndCheck(){
        runBlocking {
            sessionRecordDao.deleteAllSessions()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int){
        val count = runBlocking {
            sessionRecordDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    private fun generateSessions(numberOfSessions:Int = 1):List<SessionRecordEntity>{
        val returnList = mutableListOf<SessionRecordEntity>()
        var yearStartInc = 1980
        var yearEndInc = 1981
        for(i in 0 until numberOfSessions){
            returnList.add(generateSessionRecordEntity(
                yearstart = yearStartInc,
                yearend =  yearEndInc
            ))
            yearStartInc++
            yearEndInc++
        }
        return returnList
    }

    private fun generateSessionRecordEntity(
        desiredId:Long = -1L,
        yearstart: Int = 1980,
        monthstart: Int = 5,
        dayOfMonthstart: Int = 2,
        hourOfDaystart: Int = 15,
        minutestart: Int = 27,
        secondstart: Int = 54,
        startBodyValue:Int = -2,
        startThoughtsValue:Int = 0,
        startFeelingsValue:Int = 1,
        startGlobalValue:Int = 2,
        yearend: Int = 1982,
        monthend: Int = 6,
        dayOfMonthend: Int = 3,
        hourOfDayend: Int = 17,
        minuteend: Int = 45,
        secondend: Int = 3,
        endBodyValue:Int = 0,
        endThoughtsValue:Int = 1,
        endFeelingsValue:Int = -1,
        endGlobalValue:Int = -2,
        notes:String = "generateSessionRecordEntity default notes",
        realDuration:Long = 1590000,
        pausesCount:Int = 7,
        realDurationVsPlanned:Int = 0,
        guideMp3:String = "generateSessionRecordEntity default guideMp3"
    ):SessionRecordEntity{
        if(desiredId==-1L) {
            return SessionRecordEntity(
                startTimeOfRecord = GregorianCalendar(yearstart, monthstart, dayOfMonthstart, hourOfDaystart, minutestart, secondstart).timeInMillis,
                startBodyValue = startBodyValue,
                startThoughtsValue = startThoughtsValue,
                startFeelingsValue = startFeelingsValue,
                startGlobalValue = startGlobalValue,

                endTimeOfRecord = GregorianCalendar(yearend, monthend, dayOfMonthend, hourOfDayend, minuteend, secondend).timeInMillis,
                endBodyValue = endBodyValue,
                endThoughtsValue = endThoughtsValue,
                endFeelingsValue = endFeelingsValue,
                endGlobalValue = endGlobalValue,

                notes = notes,
                realDuration = realDuration,
                pausesCount = pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = guideMp3
            )
        }else{
            return SessionRecordEntity(
                id = desiredId,
                startTimeOfRecord = GregorianCalendar(yearstart, monthstart, dayOfMonthstart, hourOfDaystart, minutestart, secondstart).timeInMillis,
                startBodyValue = startBodyValue,
                startThoughtsValue = startThoughtsValue,
                startFeelingsValue = startFeelingsValue,
                startGlobalValue = startGlobalValue,

                endTimeOfRecord = GregorianCalendar(yearend, monthend, dayOfMonthend, hourOfDayend, minuteend, secondend).timeInMillis,
                endBodyValue = endBodyValue,
                endThoughtsValue = endThoughtsValue,
                endFeelingsValue = endFeelingsValue,
                endGlobalValue = endGlobalValue,

                notes = notes,
                realDuration = realDuration,
                pausesCount = pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = guideMp3
            )
        }
    }

//////////////////////////////////////////////////////////////////

    @Test
    fun insert() {
        val sessionRecordEntityTestID = runBlocking {
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 25))
        }
        assertEquals(25L, sessionRecordEntityTestID)
        checkTotalCountIs(1)
    }

    @Test
    fun insertMultiple() {
        val insertedIds = runBlocking {
            sessionRecordDao.insertMultiple(generateSessions(20))
        }
        assertEquals(20, insertedIds.size)
        assertNotNull(insertedIds)
        var i = 1L
        for (id in insertedIds) {
            assertEquals(i, id)
            i++
        }
        checkTotalCountIs(20)
    }

    @Test
    fun deleteSessionFromEmptyTable() {
        val sessionRecordEntityToDelete = generateSessionRecordEntity(desiredId = 25)
        //
        checkTotalCountIs(0)
        val countDeleted = runBlocking {
            sessionRecordDao.deleteSession(sessionRecordEntityToDelete)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteNonExistentSession() {
        runBlocking {
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 25))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 26))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 27))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 28))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 29))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 30))
        }
        val sessionRecordEntityToDelete = generateSessionRecordEntity(desiredId = 723)
        //
        checkTotalCountIs(6)
        val countDeleted = runBlocking {
            sessionRecordDao.deleteSession(sessionRecordEntityToDelete)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(6)
    }

    @Test
    fun deleteSession() {
        val sessionRecordEntityToDelete = generateSessionRecordEntity(desiredId = 25)
        runBlocking {
            sessionRecordDao.insert(sessionRecordEntityToDelete)
            sessionRecordDao.insertMultiple(generateSessions(20))
        }
        checkTotalCountIs(21)
        val countDeleted = runBlocking {
            sessionRecordDao.deleteSession(sessionRecordEntityToDelete)
        }
        assertEquals(1, countDeleted)
        checkTotalCountIs(20)
    }

    @Test
    fun deleteAllSessionsOnEmptyTable() {
        checkTotalCountIs(0)
        val numberOfDeletedRows = runBlocking {
            sessionRecordDao.deleteAllSessions()
        }
        assertEquals(0, numberOfDeletedRows)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteAllSessions() {
        runBlocking {
            sessionRecordDao.insertMultiple(generateSessions(73))
        }
        checkTotalCountIs(73)
        val numberOfDeletedRows = runBlocking {
            sessionRecordDao.deleteAllSessions()
        }
        assertEquals(73, numberOfDeletedRows)
        checkTotalCountIs(0)
    }

    @Test
    fun getSessionOnEmptyTableTest() {
        checkTotalCountIs(0)
        val sessionRecordEntityExtractedLive = sessionRecordDao.getSessionLive(75)
        assertNotNull(sessionRecordEntityExtractedLive)
        val sessionRecordEntityExtracted = sessionRecordEntityExtractedLive.getValueBlocking()
        assertNull(sessionRecordEntityExtracted)
    }

    @Test
    fun getNonExistentSessionTest() {
        runBlocking {
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 25))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 26))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 27))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 28))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 29))
        }
        checkTotalCountIs(5)
        val sessionRecordEntityExtractedLive = sessionRecordDao.getSessionLive(73)
        assertNotNull(sessionRecordEntityExtractedLive)
        val sessionRecordEntityExtracted = sessionRecordEntityExtractedLive.getValueBlocking()
        assertNull(sessionRecordEntityExtracted)
    }

    @Test
    fun getSessionTest() {
        val sessionToGetTest = generateSessionRecordEntity(
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
            sessionRecordDao.insert(sessionToGetTest)
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 25))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 26))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 27))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 28))
            sessionRecordDao.insert(generateSessionRecordEntity(desiredId = 29))
        }
        checkTotalCountIs(6)
        val sessionRecordEntityExtractedLive = sessionRecordDao.getSessionLive(73)
        assertNotNull(sessionRecordEntityExtractedLive)
        val sessionRecordEntityExtracted = sessionRecordEntityExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntityExtracted)
        if(sessionRecordEntityExtracted != null){
            assertEquals(73, sessionRecordEntityExtracted.id)
            assertEquals(GregorianCalendar(1623, 3, 5, 22, 21, 32).timeInMillis, sessionRecordEntityExtracted.startTimeOfRecord)
            assertEquals(-1, sessionRecordEntityExtracted.startBodyValue)
            assertEquals(1, sessionRecordEntityExtracted.startThoughtsValue)
            assertEquals(2, sessionRecordEntityExtracted.startFeelingsValue)
            assertEquals(-2, sessionRecordEntityExtracted.startGlobalValue)
            assertEquals(GregorianCalendar(2004, 2, 7, 15, 17, 51).timeInMillis, sessionRecordEntityExtracted.endTimeOfRecord)
            assertEquals(1, sessionRecordEntityExtracted.endBodyValue)
            assertEquals(-2, sessionRecordEntityExtracted.endThoughtsValue)
            assertEquals(0, sessionRecordEntityExtracted.endFeelingsValue)
            assertEquals(2, sessionRecordEntityExtracted.endGlobalValue)
            assertEquals("getSessionTest notes", sessionRecordEntityExtracted.notes)
            assertEquals( 123456789, sessionRecordEntityExtracted.realDuration)
            assertEquals(7, sessionRecordEntityExtracted.pausesCount)
            assertEquals( 1, sessionRecordEntityExtracted.realDurationVsPlanned)
            assertEquals("getSessionTest guideMp3", sessionRecordEntityExtracted.guideMp3)
        }
    }

    @Test
    fun updateNonExistentSession() {
        val sessionRecordEntityToUpdate = generateSessionRecordEntity(desiredId = 25)
        runBlocking {
            sessionRecordDao.insertMultiple(generateSessions(20))
        }
        checkTotalCountIs(20)
        val numberOfUpdatedItems = runBlocking {
            sessionRecordDao.updateSession(sessionRecordEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(20)
    }

    @Test
    fun updateSessionOnEmptyTable() {
        val sessionRecordEntityToUpdate = generateSessionRecordEntity(desiredId = 25)
        checkTotalCountIs(0)
        val numberOfUpdatedItems = runBlocking {
            sessionRecordDao.updateSession(sessionRecordEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(0)
    }

    @Test
    fun updateSession() {
        val sessionRecordEntityToUpdate = generateSessionRecordEntity(
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
            sessionRecordDao.insert(sessionRecordEntityToUpdate)
            sessionRecordDao.insertMultiple(generateSessions(20))
        }
        checkTotalCountIs(21)
        sessionRecordEntityToUpdate.startTimeOfRecord = GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis
        sessionRecordEntityToUpdate.startBodyValue = 2
        sessionRecordEntityToUpdate.startThoughtsValue = 1
        sessionRecordEntityToUpdate.startFeelingsValue = 0
        sessionRecordEntityToUpdate.startGlobalValue = -1
        sessionRecordEntityToUpdate.endTimeOfRecord = GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis
        sessionRecordEntityToUpdate.endBodyValue = -1
        sessionRecordEntityToUpdate.endThoughtsValue = -1
        sessionRecordEntityToUpdate.endFeelingsValue = 2
        sessionRecordEntityToUpdate.endGlobalValue = 1
        sessionRecordEntityToUpdate.notes = "updateSession notes UPDATED"
        sessionRecordEntityToUpdate.realDuration = 987654321
        sessionRecordEntityToUpdate.pausesCount = 1
        sessionRecordEntityToUpdate.realDurationVsPlanned = -1
        sessionRecordEntityToUpdate.guideMp3 = "updateSession guideMp3 UPDATED"
        val numberOfUpdatedItems = runBlocking {
            sessionRecordDao.updateSession(sessionRecordEntityToUpdate)
        }
        assertEquals(1, numberOfUpdatedItems)
        checkTotalCountIs(21)
        val sessionRecordEntityExtractedLive = sessionRecordDao.getSessionLive(73)
        assertNotNull(sessionRecordEntityExtractedLive)
        val sessionRecordEntityExtracted = sessionRecordEntityExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntityExtracted)
        if(sessionRecordEntityExtracted != null){
            assertEquals(73, sessionRecordEntityExtracted.id)
            assertEquals(GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis, sessionRecordEntityExtracted.startTimeOfRecord)
            assertEquals(2, sessionRecordEntityExtracted.startBodyValue)
            assertEquals(1, sessionRecordEntityExtracted.startThoughtsValue)
            assertEquals(0, sessionRecordEntityExtracted.startFeelingsValue)
            assertEquals(-1, sessionRecordEntityExtracted.startGlobalValue)
            assertEquals(GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis, sessionRecordEntityExtracted.endTimeOfRecord)
            assertEquals(-1, sessionRecordEntityExtracted.endBodyValue)
            assertEquals(-1, sessionRecordEntityExtracted.endThoughtsValue)
            assertEquals(2, sessionRecordEntityExtracted.endFeelingsValue)
            assertEquals(1, sessionRecordEntityExtracted.endGlobalValue)
            assertEquals("updateSession notes UPDATED", sessionRecordEntityExtracted.notes)
            assertEquals( 987654321, sessionRecordEntityExtracted.realDuration)
            assertEquals(1, sessionRecordEntityExtracted.pausesCount)
            assertEquals( -1, sessionRecordEntityExtracted.realDurationVsPlanned)
            assertEquals("updateSession guideMp3 UPDATED", sessionRecordEntityExtracted.guideMp3)
        }
    }

    @Test
    fun getAllSessionsStartTimeAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsStartTimeAsc() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(yearstart = 1623),
            generateSessionRecordEntity(yearstart = 2013),
            generateSessionRecordEntity(yearstart = 1953),
            generateSessionRecordEntity(yearstart = 1733),
            generateSessionRecordEntity(yearstart = 2003),
            generateSessionRecordEntity(yearstart = 1843)
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(6, sessionRecordEntitysSorted.size)
            var date = 0L
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].startTimeOfRecord >= date)
                date = sessionRecordEntitysSorted[i].startTimeOfRecord
                assertEquals(date, sessionRecordEntitysSorted[i].startTimeOfRecord)
            }
        }
    }

    @Test
    fun getAllSessionsStartTimeDescOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsStartTimeDesc() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(yearstart = 1623),
            generateSessionRecordEntity(yearstart = 2013),
            generateSessionRecordEntity(yearstart = 1843),
            generateSessionRecordEntity(yearstart = 1953),
            generateSessionRecordEntity(yearstart = 1733),
            generateSessionRecordEntity(yearstart = 2003)
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(6, sessionRecordEntitysSorted.size)
            var date = sessionRecordEntitysSorted[sessionRecordEntitysSorted.size - 1].startTimeOfRecord
            for(i in 4 downTo 0){
                assert(sessionRecordEntitysSorted[i].startTimeOfRecord <= date)
                date = sessionRecordEntitysSorted[i].startTimeOfRecord
                assertEquals(date, sessionRecordEntitysSorted[i].startTimeOfRecord)
            }
        }
    }

    @Test
    fun getAllSessionsDurationAscOnEmptyTable() {
       checkTotalCountIs(0)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsDurationAsc() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(realDuration = 123L),
            generateSessionRecordEntity(realDuration = 234L),
            generateSessionRecordEntity(realDuration = 345L),
            generateSessionRecordEntity(realDuration = 456L),
            generateSessionRecordEntity(realDuration = 567L),
            generateSessionRecordEntity(realDuration = 578L)
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeAsc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(6, sessionRecordEntitysSorted.size)
            var duration = 0L
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].realDuration >= duration)
                duration = sessionRecordEntitysSorted[i].realDuration
                assertEquals(duration, sessionRecordEntitysSorted[i].realDuration)
            }
        }
    }

    @Test
    fun getAllSessionsDurationDescOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsDurationDesc() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(realDuration = 123L),
            generateSessionRecordEntity(realDuration = 234L),
            generateSessionRecordEntity(realDuration = 345L),
            generateSessionRecordEntity(realDuration = 456L),
            generateSessionRecordEntity(realDuration = 567L),
            generateSessionRecordEntity(realDuration = 578L)
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsStartTimeDesc()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(6, sessionRecordEntitysSorted.size)
            var duration = sessionRecordEntitysSorted[sessionRecordEntitysSorted.size - 1].realDuration
            for(i in 4 downTo 0){
                assert(sessionRecordEntitysSorted[i].realDuration <= duration)
                duration = sessionRecordEntitysSorted[i].realDuration
                assertEquals(duration, sessionRecordEntitysSorted[i].realDuration)
            }
        }
    }

    @Test
    fun getAllSessionsWithMp3OnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsWithMp3()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsWithMp3() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(guideMp3 = ""),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 1"),
            generateSessionRecordEntity(guideMp3 = ""),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 2"),
            generateSessionRecordEntity(guideMp3 = ""),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 3"),
            generateSessionRecordEntity(guideMp3 = "")
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(7)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsWithMp3()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(3, sessionRecordEntitysSorted.size)
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].guideMp3 != "")
            }
        }
    }

    @Test
    fun getAllSessionsWithoutMp3OnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsWithoutMp3()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsWithoutMp3() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(guideMp3 = ""),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 1"),
            generateSessionRecordEntity(guideMp3 = ""),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 2"),
            generateSessionRecordEntity(guideMp3 = ""),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 3"),
            generateSessionRecordEntity(guideMp3 = "")
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(7)
        val sessionRecordEntitysExtractedLive = sessionRecordDao.getAllSessionsWithoutMp3()
        assertNotNull(sessionRecordEntitysExtractedLive)
        val sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(4, sessionRecordEntitysSorted.size)
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].guideMp3 == "")
            }
        }
    }

    @Test
    fun getSessionsSearchOnEmptyTable() {
        checkTotalCountIs(0)
        //
        var sessionRecordEntitysExtractedLive = sessionRecordDao.getSessionsSearch("test 1")
        assertNotNull(sessionRecordEntitysExtractedLive)
        var sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }

    }

    @Test
    fun getSessionsSearch() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(notes = "test 1"),
            generateSessionRecordEntity(guideMp3 = "test 1"),
            generateSessionRecordEntity(notes = "notes test 2"),
            generateSessionRecordEntity(notes = "notes test 2"),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 2"),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 2"),
            generateSessionRecordEntity(notes = "notes test 3"),
            generateSessionRecordEntity(guideMp3 = "guide mp3 test 3")
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
            sessionRecordDao.insertMultiple(generateSessions(5))
        }
        checkTotalCountIs(13)
        //
        var sessionRecordEntitysExtractedLive = sessionRecordDao.getSessionsSearch("test 1")
        assertNotNull(sessionRecordEntitysExtractedLive)
        var sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(2, sessionRecordEntitysSorted.size)
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].guideMp3.contains("test 1") || sessionRecordEntitysSorted[i].notes.contains("test 1"))
            }
        }
        //
        sessionRecordEntitysExtractedLive = sessionRecordDao.getSessionsSearch("test")
        assertNotNull(sessionRecordEntitysExtractedLive)
        sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(8, sessionRecordEntitysSorted.size)
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].guideMp3.contains("test") || sessionRecordEntitysSorted[i].notes.contains("test"))
            }
        }
        //
        sessionRecordEntitysExtractedLive = sessionRecordDao.getSessionsSearch("2")
        assertNotNull(sessionRecordEntitysExtractedLive)
        sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(4, sessionRecordEntitysSorted.size)
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].guideMp3.contains("2") || sessionRecordEntitysSorted[i].notes.contains("2"))
            }
        }
        //
        sessionRecordEntitysExtractedLive = sessionRecordDao.getSessionsSearch("notes test 2")
        assertNotNull(sessionRecordEntitysExtractedLive)
        sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(2, sessionRecordEntitysSorted.size)
            for(i in sessionRecordEntitysSorted.indices) {
                assert(sessionRecordEntitysSorted[i].guideMp3.contains("notes test 2") || sessionRecordEntitysSorted[i].notes.contains("notes test 2"))
            }
        }
        //
        sessionRecordEntitysExtractedLive = sessionRecordDao.getSessionsSearch("tralala test")
        assertNotNull(sessionRecordEntitysExtractedLive)
        sessionRecordEntitysSorted = sessionRecordEntitysExtractedLive.getValueBlocking()
        assertNotNull(sessionRecordEntitysSorted)
        if(sessionRecordEntitysSorted != null){
            assertEquals(0, sessionRecordEntitysSorted.size)
        }
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitysExtracted = runBlocking {
            sessionRecordDao.getAllSessionsNotLiveStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitysExtracted)
        if(sessionRecordEntitysExtracted != null){
            assertEquals(0, sessionRecordEntitysExtracted.size)
        }
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAsc() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(yearstart = 1623),
            generateSessionRecordEntity(yearstart = 2003),
            generateSessionRecordEntity(yearstart = 1733),
            generateSessionRecordEntity(yearstart = 1843),
            generateSessionRecordEntity(yearstart = 2013),
            generateSessionRecordEntity(yearstart = 1953)
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitysExtracted = runBlocking {
            sessionRecordDao.getAllSessionsNotLiveStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitysExtracted)
        if(sessionRecordEntitysExtracted != null){
            assertEquals(6, sessionRecordEntitysExtracted.size)
            var date = 0L
            for(i in sessionRecordEntitysExtracted.indices) {
                assert(sessionRecordEntitysExtracted[i].startTimeOfRecord >= date)
                date = sessionRecordEntitysExtracted[i].startTimeOfRecord
                assertEquals(date, sessionRecordEntitysExtracted[i].startTimeOfRecord)
            }
        }
    }

    @Test
    fun getMostRecentSessionRecordDateOnEmptyTable() {
        checkTotalCountIs(0)
        val latestRecordedSessionDate = runBlocking {
            sessionRecordDao.getMostRecentSessionRecordDate()
        }
        assertNull(latestRecordedSessionDate)
    }

    @Test
    fun getMostRecentSessionRecordDate() {
        val sessionToInsertList = listOf(
            generateSessionRecordEntity(yearstart = 1623, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            generateSessionRecordEntity(yearstart = 2013, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            generateSessionRecordEntity(yearstart = 1733, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            generateSessionRecordEntity(yearstart = 1953, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            generateSessionRecordEntity(yearstart = 2003, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32),
            generateSessionRecordEntity(yearstart = 1843, monthstart = 3, dayOfMonthstart = 5, hourOfDaystart = 22, minutestart = 21, secondstart = 32)
        )
        runBlocking {
            sessionRecordDao.insertMultiple(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val latestRecordedSessionDate = runBlocking {
            sessionRecordDao.getMostRecentSessionRecordDate()
        }
        assertNotNull(latestRecordedSessionDate)
        assertEquals(GregorianCalendar(2013, 3, 5, 22, 21, 32).timeInMillis, latestRecordedSessionDate)
    }
}