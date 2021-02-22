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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.*

class SessionRecordConverterTest {

    @MockK
    private lateinit var mockLogger: Logger

    private lateinit var sessionRecordConverter: SessionRecordConverter

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
        assertEquals(modeltranslated, sessionRecordEntity)
    }

    @Test
    fun convertEntitytoModel() {
        val entityTranslated = runBlocking {
            sessionRecordConverter.convertEntitytoModel(sessionRecordEntity)
        }
        assertEquals(entityTranslated, sessionRecord)
    }

    val sessionRecord =
        SessionRecord(
            id = 41,
            startMood = generateMood(
                1980,
                5,
                2,
                15,
                27,
                54,
                MoodValue.WORST,
                MoodValue.NOT_SET,
                MoodValue.GOOD,
                MoodValue.BEST
            ),
            endMood = generateMood(
                1981,
                6,
                3,
                17,
                45,
                3,
                MoodValue.NOT_SET,
                MoodValue.GOOD,
                MoodValue.BAD,
                MoodValue.WORST
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
        startTimeOfRecord = GregorianCalendar(1980, 5, 2, 15, 27, 54).timeInMillis,
        startBodyValue = -2,
        startThoughtsValue = 0,
        startFeelingsValue = 1,
        startGlobalValue = 2,
        endTimeOfRecord = GregorianCalendar(1981, 6, 3, 17, 45, 3).timeInMillis,
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

    fun generateMood(
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
            timeOfRecord = GregorianCalendar(
                year,
                month,
                dayOfMonth,
                hourOfDay,
                minute,
                second
            ).timeInMillis,
            bodyValue = bodyValue,
            thoughtsValue = thoughtsValue,
            feelingsValue = feelingsValue,
            globalValue = globalValue
        )
    }
}