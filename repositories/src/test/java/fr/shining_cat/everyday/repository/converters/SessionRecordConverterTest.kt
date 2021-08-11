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

package fr.shining_cat.everyday.repository.converters

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.Mood
import fr.shining_cat.everyday.models.sessionrecord.MoodValue
import fr.shining_cat.everyday.models.sessionrecord.RealDurationVsPlanned
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.concurrent.TimeUnit

class SessionRecordConverterTest {

    @MockK
    private lateinit var mockLogger: Logger

    private lateinit var sessionRecordConverter: SessionRecordConverter

    private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        assertNotNull(mockLogger)
        sessionRecordConverter = SessionRecordConverter(mockLogger)
    }

    @Test
    fun convertModelToEntity() {
        val modeltranslated = runBlocking {
            sessionRecordConverter.convertModelToEntity(sessionRecord)
        }
        assertEquals(
            sessionRecordEntity, modeltranslated
        )
    }

    @Test
    fun convertEntitytoModel() {
        val entityTranslated = runBlocking {
            sessionRecordConverter.convertEntityToModel(sessionRecordEntity)
        }
        assertEquals(
            sessionRecord, entityTranslated
        )
    }

    @Test
    fun convertModelToEntityNoId() {
        val modeltranslated = runBlocking {
            sessionRecordConverter.convertModelToEntity(sessionRecordNoId)
        }
        assertEquals(
            sessionRecordEntityNoId, modeltranslated
        )
    }

    @Test
    fun convertEntitytoModelNoId() {
        val entityTranslated = runBlocking {
            sessionRecordConverter.convertEntityToModel(sessionRecordEntityNoId)
        }
        assertEquals(
            sessionRecordNoId, entityTranslated
        )
    }

    @Test
    fun convertModelToStringArray() {
        // 0 is for NOT SET so export it as such
        val startMoodRecord = sessionRecord.startMood
        val startBodyValue = startMoodRecord.bodyValue.name
        val startThoughtsValue = startMoodRecord.thoughtsValue.name
        val startFeelingsValue = startMoodRecord.feelingsValue.name
        val startGlobalValue = startMoodRecord.globalValue.name
        //
        val endMoodRecord = sessionRecord.endMood
        val endBodyValue = endMoodRecord.bodyValue.name
        val endThoughtsValue = endMoodRecord.thoughtsValue.name
        val endFeelingsValue = endMoodRecord.feelingsValue.name
        val endGlobalValue = endMoodRecord.globalValue.name
        //
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val startFormatted = formatter.format(Instant.ofEpochMilli(startMoodRecord.timeOfRecord).atZone(ZoneId.systemDefault()))
        val endFormatted = formatter.format(Instant.ofEpochMilli(endMoodRecord.timeOfRecord).atZone(ZoneId.systemDefault()))
        val controlArray = arrayOf(
            startFormatted,
            endFormatted,
            (TimeUnit.MILLISECONDS.toMinutes(sessionRecord.realDuration)).toString() + "mn",
            sessionRecord.notes,
            startBodyValue,
            startThoughtsValue,
            startFeelingsValue,
            startGlobalValue,
            endBodyValue,
            endThoughtsValue,
            endFeelingsValue,
            endGlobalValue,
            sessionRecord.pausesCount.toString(),
            sessionRecord.realDurationVsPlanned.name,
            sessionRecord.guideMp3
        )
        val resultArray = runBlocking {
            sessionRecordConverter.convertModelToStringArray(sessionRecord)
        }
        assertArrayEquals(
            controlArray, resultArray
        )
    }

    val sessionRecord = SessionRecord(
        id = 41,
        startMood = generateMood(
            1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST
        ),
        endMood = generateMood(
            1981, 6, 3, 17, 45, 3, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST
        ),
        notes = "testing notes string",
        realDuration = 1500000,
        pausesCount = 3,
        realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
        guideMp3 = "testing guideMp3 string",
        sessionTypeId = 5678L
    )

    val sessionRecordEntity = SessionRecordEntity(
        id = 41,
        startTimeOfRecord = convertDateToMilliSinceEpoch(1980, 5, 2, 15, 27, 54),
        startBodyValue = -2,
        startThoughtsValue = 0,
        startFeelingsValue = 1,
        startGlobalValue = 2,
        endTimeOfRecord = convertDateToMilliSinceEpoch(1981, 6, 3, 17, 45, 3),
        endBodyValue = 0,
        endThoughtsValue = 1,
        endFeelingsValue = -1,
        endGlobalValue = -2,
        notes = "testing notes string",
        realDuration = 1500000,
        pausesCount = 3,
        realDurationVsPlanned = -1,
        guideMp3 = "testing guideMp3 string",
        sessionTypeId = 5678L
    )
    val sessionRecordNoId = SessionRecord(
        id = -1L,
        startMood = generateMood(
            1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST
        ),
        endMood = generateMood(
            1981, 6, 3, 17, 45, 3, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST
        ),
        notes = "testing notes string",
        realDuration = 1500000,
        pausesCount = 3,
        realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
        guideMp3 = "testing guideMp3 string",
        sessionTypeId = 5678L
    )

    val sessionRecordEntityNoId = SessionRecordEntity(
        id = null,
        startTimeOfRecord = convertDateToMilliSinceEpoch(1980, 5, 2, 15, 27, 54),
        startBodyValue = -2,
        startThoughtsValue = 0,
        startFeelingsValue = 1,
        startGlobalValue = 2,
        endTimeOfRecord = convertDateToMilliSinceEpoch(1981, 6, 3, 17, 45, 3),
        endBodyValue = 0,
        endThoughtsValue = 1,
        endFeelingsValue = -1,
        endGlobalValue = -2,
        notes = "testing notes string",
        realDuration = 1500000,
        pausesCount = 3,
        realDurationVsPlanned = -1,
        guideMp3 = "testing guideMp3 string",
        sessionTypeId = 5678L
    )

    private fun generateMood(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int,
        minute: Int = 0,
        second: Int = 0,
        bodyValue: MoodValue = MoodValue.NOT_SET,
        thoughtsValue: MoodValue = MoodValue.BEST,
        feelingsValue: MoodValue = MoodValue.BAD,
        globalValue: MoodValue = MoodValue.GOOD
    ): Mood {
        return Mood(
            timeOfRecord = convertDateToMilliSinceEpoch(year, month, dayOfMonth, hourOfDay, minute, second),
            bodyValue = bodyValue,
            thoughtsValue = thoughtsValue,
            feelingsValue = feelingsValue,
            globalValue = globalValue
        )
    }

    private fun convertDateToMilliSinceEpoch(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int,
        minutes: Int,
        seconds: Int
    ): Long {
        val zone = ZoneId.systemDefault()
        val rules = zone.rules
        val offset = rules.getOffset(Instant.now())

        val dateAsLong = LocalDateTime.of(
            year, month, dayOfMonth, hourOfDay, minutes, seconds
        ).toInstant(offset).toEpochMilli()
        return dateAsLong
    }
}