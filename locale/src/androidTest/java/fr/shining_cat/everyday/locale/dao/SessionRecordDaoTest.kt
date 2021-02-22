/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

class SessionRecordDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sessionRecordDao: SessionRecordDao

    @Before
    fun setupEmptyTable() {
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sessionRecordDao = EveryDayRoomDatabase.getInstance(appContext).sessionRecordDao()
        emptyTableAndCheck()
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    /////////////////////////////
    //  UTILS
    /////////////////////////////
    private fun emptyTableAndCheck() {
        runBlocking {
            sessionRecordDao.deleteAllSessions()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int) {
        val count = runBlocking {
            sessionRecordDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }


    private fun generateSessions(numberOfSessions: Int = 1): List<SessionRecordEntity> {
        val returnList = mutableListOf<SessionRecordEntity>()
        var yearStartInc = 1980
        var yearEndInc = 1981
        for (i in 0 until numberOfSessions) {
            returnList.add(
                generateSessionRecordEntity(
                    yearstart = yearStartInc,
                    yearend = yearEndInc
                )
            )
            yearStartInc++
            yearEndInc++
        }
        return returnList
    }

    private fun generateSessionRecordEntity(
        desiredId: Long = -1L,
        yearstart: Int = 1980,
        monthstart: Int = 5,
        dayOfMonthstart: Int = 2,
        hourOfDaystart: Int = 15,
        minutestart: Int = 27,
        secondstart: Int = 54,
        startBodyValue: Int = -2,
        startThoughtsValue: Int = 0,
        startFeelingsValue: Int = 1,
        startGlobalValue: Int = 2,
        yearend: Int = 1982,
        monthend: Int = 6,
        dayOfMonthend: Int = 3,
        hourOfDayend: Int = 17,
        minuteend: Int = 45,
        secondend: Int = 3,
        endBodyValue: Int = 0,
        endThoughtsValue: Int = 1,
        endFeelingsValue: Int = -1,
        endGlobalValue: Int = -2,
        notes: String = "generateSessionRecordEntity default notes",
        realDuration: Long = 1590000L,
        pausesCount: Int = 7,
        realDurationVsPlanned: Int = 0,
        guideMp3: String = "generateSessionRecordEntity default guideMp3",
        sessionTypeId: Long = 5678L
    ): SessionRecordEntity {
        if (desiredId == -1L) {
            return SessionRecordEntity(
                startTimeOfRecord = GregorianCalendar(
                    yearstart,
                    monthstart,
                    dayOfMonthstart,
                    hourOfDaystart,
                    minutestart,
                    secondstart
                ).timeInMillis,
                startBodyValue = startBodyValue,
                startThoughtsValue = startThoughtsValue,
                startFeelingsValue = startFeelingsValue,
                startGlobalValue = startGlobalValue,

                endTimeOfRecord = GregorianCalendar(
                    yearend,
                    monthend,
                    dayOfMonthend,
                    hourOfDayend,
                    minuteend,
                    secondend
                ).timeInMillis,
                endBodyValue = endBodyValue,
                endThoughtsValue = endThoughtsValue,
                endFeelingsValue = endFeelingsValue,
                endGlobalValue = endGlobalValue,

                notes = notes,
                realDuration = realDuration,
                pausesCount = pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = guideMp3,
                sessionTypeId = sessionTypeId
            )
        } else {
            return SessionRecordEntity(
                id = desiredId,
                startTimeOfRecord = GregorianCalendar(
                    yearstart,
                    monthstart,
                    dayOfMonthstart,
                    hourOfDaystart,
                    minutestart,
                    secondstart
                ).timeInMillis,
                startBodyValue = startBodyValue,
                startThoughtsValue = startThoughtsValue,
                startFeelingsValue = startFeelingsValue,
                startGlobalValue = startGlobalValue,

                endTimeOfRecord = GregorianCalendar(
                    yearend,
                    monthend,
                    dayOfMonthend,
                    hourOfDayend,
                    minuteend,
                    secondend
                ).timeInMillis,
                endBodyValue = endBodyValue,
                endThoughtsValue = endThoughtsValue,
                endFeelingsValue = endFeelingsValue,
                endGlobalValue = endGlobalValue,

                notes = notes,
                realDuration = realDuration,
                pausesCount = pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = guideMp3,
                sessionTypeId = sessionTypeId
            )
        }
    }

    /////////////////////////////
    //  INSERTS
    /////////////////////////////
    @Test
    fun insertOne() {
        val sessionRecordEntityTestID = runBlocking {
            sessionRecordDao.insert(listOf(generateSessionRecordEntity(desiredId = 25)))
        }
        assertEquals(25L, sessionRecordEntityTestID[0])
        checkTotalCountIs(1)
    }

    @Test
    fun insertMultiple() {
        val insertedIds = runBlocking {
            sessionRecordDao.insert(generateSessions(20))
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

    /////////////////////////////
    //  DELETES
    /////////////////////////////
    @Test
    fun deleteSessionFromEmptyTable() {
        val sessionRecordEntityToDelete = generateSessionRecordEntity(desiredId = 25)
        //
        checkTotalCountIs(0)
        val countDeleted = runBlocking {
            sessionRecordDao.delete(sessionRecordEntityToDelete)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteNonExistentSession() {
        runBlocking {
            sessionRecordDao.insert(
                listOf(
                    generateSessionRecordEntity(desiredId = 25),
                    generateSessionRecordEntity(desiredId = 26),
                    generateSessionRecordEntity(desiredId = 27),
                    generateSessionRecordEntity(desiredId = 28),
                    generateSessionRecordEntity(desiredId = 29),
                    generateSessionRecordEntity(desiredId = 30)
                )
            )
        }
        val sessionRecordEntityToDelete = generateSessionRecordEntity(desiredId = 723)
        //
        checkTotalCountIs(6)
        val countDeleted = runBlocking {
            sessionRecordDao.delete(sessionRecordEntityToDelete)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(6)
    }

    @Test
    fun deleteSession() {
        val sessionRecordEntityToDelete = generateSessionRecordEntity(desiredId = 25)
        runBlocking {
            sessionRecordDao.insert(listOf(sessionRecordEntityToDelete))
            sessionRecordDao.insert(generateSessions(20))
        }
        checkTotalCountIs(21)
        val countDeleted = runBlocking {
            sessionRecordDao.delete(sessionRecordEntityToDelete)
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
            sessionRecordDao.insert(generateSessions(73))
        }
        checkTotalCountIs(73)
        val numberOfDeletedRows = runBlocking {
            sessionRecordDao.deleteAllSessions()
        }
        assertEquals(73, numberOfDeletedRows)
        checkTotalCountIs(0)
    }
    /////////////////////////////
    //  GETS
    /////////////////////////////

    @Test
    fun getSessionOnEmptyTableTest() {
        checkTotalCountIs(0)
        val sessionRecordEntityExtracted = runBlocking {
            sessionRecordDao.getSession(75)
        }
        assertNull(sessionRecordEntityExtracted)
    }

    @Test
    fun getNonExistentSessionTest() {
        runBlocking {
            sessionRecordDao.insert(
                listOf(
                    generateSessionRecordEntity(desiredId = 25),
                    generateSessionRecordEntity(desiredId = 26),
                    generateSessionRecordEntity(desiredId = 27),
                    generateSessionRecordEntity(desiredId = 28),
                    generateSessionRecordEntity(desiredId = 29)
                )
            )
        }
        checkTotalCountIs(5)
        val sessionRecordEntityExtracted = runBlocking {
            sessionRecordDao.getSession(73)
        }
        assertNull(sessionRecordEntityExtracted)
    }

    @Test
    fun getSessionTest() {
        val sessionToGetTest = generateSessionRecordEntity(
            desiredId = 73,
            yearstart = 1623,
            monthstart = 3,
            dayOfMonthstart = 5,
            hourOfDaystart = 22,
            minutestart = 21,
            secondstart = 32,
            startBodyValue = -1,
            startThoughtsValue = 1,
            startFeelingsValue = 2,
            startGlobalValue = -2,
            yearend = 2004,
            monthend = 2,
            dayOfMonthend = 7,
            hourOfDayend = 15,
            minuteend = 17,
            secondend = 51,
            endBodyValue = 1,
            endThoughtsValue = -2,
            endFeelingsValue = 0,
            endGlobalValue = 2,
            notes = "getSessionTest notes",
            realDuration = 123456789,
            pausesCount = 7,
            realDurationVsPlanned = 1,
            guideMp3 = "getSessionTest guideMp3"
        )

        runBlocking {
            sessionRecordDao.insert(listOf(sessionToGetTest))
            sessionRecordDao.insert(
                listOf(
                    generateSessionRecordEntity(desiredId = 25),
                    generateSessionRecordEntity(desiredId = 26),
                    generateSessionRecordEntity(desiredId = 27),
                    generateSessionRecordEntity(desiredId = 28),
                    generateSessionRecordEntity(desiredId = 29)
                )
            )
        }
        checkTotalCountIs(6)
        val sessionRecordEntityExtracted = runBlocking {
            sessionRecordDao.getSession(73)
        }
        assertNotNull(sessionRecordEntityExtracted)
        if (sessionRecordEntityExtracted != null) {
            assertEquals(73, sessionRecordEntityExtracted.id)
            assertEquals(
                GregorianCalendar(1623, 3, 5, 22, 21, 32).timeInMillis,
                sessionRecordEntityExtracted.startTimeOfRecord
            )
            assertEquals(-1, sessionRecordEntityExtracted.startBodyValue)
            assertEquals(1, sessionRecordEntityExtracted.startThoughtsValue)
            assertEquals(2, sessionRecordEntityExtracted.startFeelingsValue)
            assertEquals(-2, sessionRecordEntityExtracted.startGlobalValue)
            assertEquals(
                GregorianCalendar(2004, 2, 7, 15, 17, 51).timeInMillis,
                sessionRecordEntityExtracted.endTimeOfRecord
            )
            assertEquals(1, sessionRecordEntityExtracted.endBodyValue)
            assertEquals(-2, sessionRecordEntityExtracted.endThoughtsValue)
            assertEquals(0, sessionRecordEntityExtracted.endFeelingsValue)
            assertEquals(2, sessionRecordEntityExtracted.endGlobalValue)
            assertEquals("getSessionTest notes", sessionRecordEntityExtracted.notes)
            assertEquals(123456789, sessionRecordEntityExtracted.realDuration)
            assertEquals(7, sessionRecordEntityExtracted.pausesCount)
            assertEquals(1, sessionRecordEntityExtracted.realDurationVsPlanned)
            assertEquals("getSessionTest guideMp3", sessionRecordEntityExtracted.guideMp3)
        }
    }

    /////////////////////////////
    //  UPDATES
    /////////////////////////////
    @Test
    fun updateNonExistentSession() {
        val sessionRecordEntityToUpdate = generateSessionRecordEntity(desiredId = 25)
        runBlocking {
            sessionRecordDao.insert(generateSessions(20))
        }
        checkTotalCountIs(20)
        val numberOfUpdatedItems = runBlocking {
            sessionRecordDao.update(sessionRecordEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(20)
    }

    @Test
    fun updateSessionOnEmptyTable() {
        val sessionRecordEntityToUpdate = generateSessionRecordEntity(desiredId = 25)
        checkTotalCountIs(0)
        val numberOfUpdatedItems = runBlocking {
            sessionRecordDao.update(sessionRecordEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(0)
    }

    @Test
    fun updateSession() {
        val sessionRecordEntityToUpdate = generateSessionRecordEntity(
            desiredId = 73,
            yearstart = 1623,
            monthstart = 3,
            dayOfMonthstart = 5,
            hourOfDaystart = 22,
            minutestart = 21,
            secondstart = 32,
            startBodyValue = -1,
            startThoughtsValue = 1,
            startFeelingsValue = 2,
            startGlobalValue = -2,
            yearend = 2004,
            monthend = 2,
            dayOfMonthend = 7,
            hourOfDayend = 15,
            minuteend = 17,
            secondend = 51,
            endBodyValue = 1,
            endThoughtsValue = -2,
            endFeelingsValue = 0,
            endGlobalValue = 2,
            notes = "updateSession notes",
            realDuration = 123456789,
            pausesCount = 7,
            realDurationVsPlanned = 1,
            guideMp3 = "updateSession guideMp3"
        )

        runBlocking {
            sessionRecordDao.insert(listOf(sessionRecordEntityToUpdate))
            sessionRecordDao.insert(generateSessions(20))
        }
        checkTotalCountIs(21)
        sessionRecordEntityToUpdate.startTimeOfRecord =
            GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis
        sessionRecordEntityToUpdate.startBodyValue = 2
        sessionRecordEntityToUpdate.startThoughtsValue = 1
        sessionRecordEntityToUpdate.startFeelingsValue = 0
        sessionRecordEntityToUpdate.startGlobalValue = -1
        sessionRecordEntityToUpdate.endTimeOfRecord =
            GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis
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
            sessionRecordDao.update(sessionRecordEntityToUpdate)
        }
        assertEquals(1, numberOfUpdatedItems)
        checkTotalCountIs(21)
        val sessionRecordEntityExtracted = runBlocking {
            sessionRecordDao.getSession(73)
        }
        assertNotNull(sessionRecordEntityExtracted)
        if (sessionRecordEntityExtracted != null) {
            assertEquals(73, sessionRecordEntityExtracted.id)
            assertEquals(
                GregorianCalendar(1923, 5, 22, 17, 12, 7).timeInMillis,
                sessionRecordEntityExtracted.startTimeOfRecord
            )
            assertEquals(2, sessionRecordEntityExtracted.startBodyValue)
            assertEquals(1, sessionRecordEntityExtracted.startThoughtsValue)
            assertEquals(0, sessionRecordEntityExtracted.startFeelingsValue)
            assertEquals(-1, sessionRecordEntityExtracted.startGlobalValue)
            assertEquals(
                GregorianCalendar(1975, 7, 14, 21, 13, 24).timeInMillis,
                sessionRecordEntityExtracted.endTimeOfRecord
            )
            assertEquals(-1, sessionRecordEntityExtracted.endBodyValue)
            assertEquals(-1, sessionRecordEntityExtracted.endThoughtsValue)
            assertEquals(2, sessionRecordEntityExtracted.endFeelingsValue)
            assertEquals(1, sessionRecordEntityExtracted.endGlobalValue)
            assertEquals("updateSession notes UPDATED", sessionRecordEntityExtracted.notes)
            assertEquals(987654321, sessionRecordEntityExtracted.realDuration)
            assertEquals(1, sessionRecordEntityExtracted.pausesCount)
            assertEquals(-1, sessionRecordEntityExtracted.realDurationVsPlanned)
            assertEquals("updateSession guideMp3 UPDATED", sessionRecordEntityExtracted.guideMp3)
        }
    }
    ///////////////////////////////////
    //GETS ORDERED AND FILTERED
    ///////////////////////////////////

    @Test
    fun getAllSessionsStartTimeAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitiesSorted)
        assertEquals(0, sessionRecordEntitiesSorted.size)

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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitiesSorted)
        assertEquals(6, sessionRecordEntitiesSorted.size)
        var date = 0L
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(sessionRecordEntitiesSorted[i].startTimeOfRecord >= date)
            date = sessionRecordEntitiesSorted[i].startTimeOfRecord
            assertEquals(date, sessionRecordEntitiesSorted[i].startTimeOfRecord)
        }

    }

    @Test
    fun getAllSessionsStartTimeDescOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeDesc()
        }
        assertNotNull(sessionRecordEntitiesSorted)
        assertEquals(0, sessionRecordEntitiesSorted.size)

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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeDesc()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(6, sessionRecordEntitiesSorted.size)
        var date =
            sessionRecordEntitiesSorted[sessionRecordEntitiesSorted.size - 1].startTimeOfRecord
        for (i in 4 downTo 0) {
            assert(sessionRecordEntitiesSorted[i].startTimeOfRecord <= date)
            date = sessionRecordEntitiesSorted[i].startTimeOfRecord
            assertEquals(date, sessionRecordEntitiesSorted[i].startTimeOfRecord)
        }


    }

    @Test
    fun getAllSessionsDurationAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(0, sessionRecordEntitiesSorted.size)
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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(6, sessionRecordEntitiesSorted.size)
        var duration = 0L
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(sessionRecordEntitiesSorted[i].realDuration >= duration)
            duration = sessionRecordEntitiesSorted[i].realDuration
            assertEquals(duration, sessionRecordEntitiesSorted[i].realDuration)
        }

    }

    @Test
    fun getAllSessionsDurationDescOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeDesc()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(0, sessionRecordEntitiesSorted.size)

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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsStartTimeDesc()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(6, sessionRecordEntitiesSorted.size)
        var duration =
            sessionRecordEntitiesSorted[sessionRecordEntitiesSorted.size - 1].realDuration
        for (i in 4 downTo 0) {
            assert(sessionRecordEntitiesSorted[i].realDuration <= duration)
            duration = sessionRecordEntitiesSorted[i].realDuration
            assertEquals(duration, sessionRecordEntitiesSorted[i].realDuration)
        }

    }

    @Test
    fun getAllSessionsWithMp3OnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsWithMp3()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(0, sessionRecordEntitiesSorted.size)

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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(7)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsWithMp3()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(3, sessionRecordEntitiesSorted.size)
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(sessionRecordEntitiesSorted[i].guideMp3 != "")
        }

    }

    @Test
    fun getAllSessionsWithoutMp3OnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsWithoutMp3()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(0, sessionRecordEntitiesSorted.size)

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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(7)
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getAllSessionsWithoutMp3()
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(4, sessionRecordEntitiesSorted.size)
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(sessionRecordEntitiesSorted[i].guideMp3 == "")
        }

    }

    @Test
    fun getSessionsSearchOnEmptyTable() {
        checkTotalCountIs(0)
        //
        val sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getSessionsSearch("test 1")
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(0, sessionRecordEntitiesSorted.size)


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
            sessionRecordDao.insert(sessionToInsertList)
            sessionRecordDao.insert(generateSessions(5))
        }
        checkTotalCountIs(13)
        //
        var sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getSessionsSearch("test 1")
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(2, sessionRecordEntitiesSorted.size)
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(
                sessionRecordEntitiesSorted[i].guideMp3.contains("test 1") || sessionRecordEntitiesSorted[i].notes.contains(
                    "test 1"
                )
            )

        }
//
        sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getSessionsSearch("test")
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(8, sessionRecordEntitiesSorted.size)
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(
                sessionRecordEntitiesSorted[i].guideMp3.contains("test") || sessionRecordEntitiesSorted[i].notes.contains(
                    "test"
                )
            )
        }

//
        sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getSessionsSearch("2")
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(4, sessionRecordEntitiesSorted.size)
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(
                sessionRecordEntitiesSorted[i].guideMp3.contains("2") || sessionRecordEntitiesSorted[i].notes.contains(
                    "2"
                )
            )
        }

//
        sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getSessionsSearch("notes test 2")
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(2, sessionRecordEntitiesSorted.size)
        for (i in sessionRecordEntitiesSorted.indices) {
            assert(
                sessionRecordEntitiesSorted[i].guideMp3.contains("notes test 2") || sessionRecordEntitiesSorted[i].notes.contains(
                    "notes test 2"
                )
            )
        }

//
        sessionRecordEntitiesSorted = runBlocking {
            sessionRecordDao.getSessionsSearch("tralala test")
        }
        assertNotNull(sessionRecordEntitiesSorted)

        assertEquals(0, sessionRecordEntitiesSorted.size)
    }

    @Test
    fun getAllSessionsNotLiveStartTimeAscOnEmptyTable() {
        checkTotalCountIs(0)
        val sessionRecordEntitiesExtracted = runBlocking {
            sessionRecordDao.asyncGetAllSessionsStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitiesExtracted)
        assertEquals(0, sessionRecordEntitiesExtracted.size)

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
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val sessionRecordEntitiesExtracted = runBlocking {
            sessionRecordDao.asyncGetAllSessionsStartTimeAsc()
        }
        assertNotNull(sessionRecordEntitiesExtracted)
        if (sessionRecordEntitiesExtracted != null) {
            assertEquals(6, sessionRecordEntitiesExtracted.size)
            var date = 0L
            for (i in sessionRecordEntitiesExtracted.indices) {
                assert(sessionRecordEntitiesExtracted[i].startTimeOfRecord >= date)
                date = sessionRecordEntitiesExtracted[i].startTimeOfRecord
                assertEquals(date, sessionRecordEntitiesExtracted[i].startTimeOfRecord)
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
            generateSessionRecordEntity(
                yearstart = 1623,
                monthstart = 3,
                dayOfMonthstart = 5,
                hourOfDaystart = 22,
                minutestart = 21,
                secondstart = 32
            ),
            generateSessionRecordEntity(
                yearstart = 2013,
                monthstart = 3,
                dayOfMonthstart = 5,
                hourOfDaystart = 22,
                minutestart = 21,
                secondstart = 32
            ),
            generateSessionRecordEntity(
                yearstart = 1733,
                monthstart = 3,
                dayOfMonthstart = 5,
                hourOfDaystart = 22,
                minutestart = 21,
                secondstart = 32
            ),
            generateSessionRecordEntity(
                yearstart = 1953,
                monthstart = 3,
                dayOfMonthstart = 5,
                hourOfDaystart = 22,
                minutestart = 21,
                secondstart = 32
            ),
            generateSessionRecordEntity(
                yearstart = 2003,
                monthstart = 3,
                dayOfMonthstart = 5,
                hourOfDaystart = 22,
                minutestart = 21,
                secondstart = 32
            ),
            generateSessionRecordEntity(
                yearstart = 1843,
                monthstart = 3,
                dayOfMonthstart = 5,
                hourOfDaystart = 22,
                minutestart = 21,
                secondstart = 32
            )
        )
        runBlocking {
            sessionRecordDao.insert(sessionToInsertList)
        }
        checkTotalCountIs(6)
        val latestRecordedSessionDate = runBlocking {
            sessionRecordDao.getMostRecentSessionRecordDate()
        }
        assertNotNull(latestRecordedSessionDate)
        assertEquals(
            GregorianCalendar(2013, 3, 5, 22, 21, 32).timeInMillis,
            latestRecordedSessionDate
        )
    }
}