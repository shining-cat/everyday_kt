package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.Mood
import fr.shining_cat.everyday.models.MoodValue
import fr.shining_cat.everyday.models.RealDurationVsPlanned
import fr.shining_cat.everyday.models.SessionRecord
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class SessionRecordConverterTest {

    private lateinit var sessionRecordConverter: SessionRecordConverter

    @Before
    fun setUp() {
        sessionRecordConverter = SessionRecordConverter()
    }

    @Test
    fun convertModelToEntity() {
        //without ID
        val sessionModel = generateSession(
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
            notes = "convertModelToEntity testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
            guideMp3 = "convertModelToEntity testing guideMp3 string"
        )
        val SessionRecordEntity = generateSessionRecordEntity(
            yearstart = 1980,
            monthstart = 5,
            dayOfMonthstart = 2,
            hourOfDaystart = 15,
            minutestart = 27,
            secondstart = 54,
            startBodyValue = -2,
            startThoughtsValue = 0,
            startFeelingsValue = 1,
            startGlobalValue = 2,
            yearend = 1981,
            monthend = 6,
            dayOfMonthend = 3,
            hourOfDayend = 17,
            minuteend = 45,
            secondend = 3,
            endBodyValue = 0,
            endThoughtsValue = 1,
            endFeelingsValue = -1,
            endGlobalValue = -2,
            notes = "convertModelToEntity testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToEntity testing guideMp3 string"
        )
        val SessionRecordEntitytranslated =
            sessionRecordConverter.convertModelToEntity(sessionModel)
        compareTwoEntitysWithoutId(SessionRecordEntitytranslated, SessionRecordEntity)
        //with ID
        val sessionModelWithId = generateSession(
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
            notes = "convertModelToEntity testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
            guideMp3 = "convertModelToEntity testing guideMp3 string"
        )
        val SessionRecordEntityWithId = generateSessionRecordEntity(
            desiredId = 41,
            yearstart = 1980,
            monthstart = 5,
            dayOfMonthstart = 2,
            hourOfDaystart = 15,
            minutestart = 27,
            secondstart = 54,
            startBodyValue = -2,
            startThoughtsValue = 0,
            startFeelingsValue = 1,
            startGlobalValue = 2,
            yearend = 1981,
            monthend = 6,
            dayOfMonthend = 3,
            hourOfDayend = 17,
            minuteend = 45,
            secondend = 3,
            endBodyValue = 0,
            endThoughtsValue = 1,
            endFeelingsValue = -1,
            endGlobalValue = -2,
            notes = "convertModelToEntity testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToEntity testing guideMp3 string"
        )
        val SessionRecordEntitytranslatedWithId =
            sessionRecordConverter.convertModelToEntity(sessionModelWithId)
        assertEquals(SessionRecordEntitytranslatedWithId, SessionRecordEntityWithId)
    }

    @Test
    fun convertEntitytoModel() {
        //with ID
        val sessionModelWithId = generateSession(
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
            notes = "convertEntitytoModel testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
            guideMp3 = "convertEntitytoModel testing guideMp3 string"
        )
        val SessionRecordEntityWithId = generateSessionRecordEntity(
            desiredId = 41,
            yearstart = 1980,
            monthstart = 5,
            dayOfMonthstart = 2,
            hourOfDaystart = 15,
            minutestart = 27,
            secondstart = 54,
            startBodyValue = -2,
            startThoughtsValue = 0,
            startFeelingsValue = 1,
            startGlobalValue = 2,
            yearend = 1981,
            monthend = 6,
            dayOfMonthend = 3,
            hourOfDayend = 17,
            minuteend = 45,
            secondend = 3,
            endBodyValue = 0,
            endThoughtsValue = 1,
            endFeelingsValue = -1,
            endGlobalValue = -2,
            notes = "convertEntitytoModel testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertEntitytoModel testing guideMp3 string"
        )
        val sessionModelTranslatedWithId =
            sessionRecordConverter.convertEntitytoModel(SessionRecordEntityWithId)
        //here we check the ID field conversion
        assertEquals(sessionModelTranslatedWithId, sessionModelWithId)
    }

    private fun compareTwoEntitysWithoutId(
        sessionRecordEntity1: SessionRecordEntity,
        sessionRecordEntity2: SessionRecordEntity
    ) {
        assertEquals(sessionRecordEntity1.startTimeOfRecord, sessionRecordEntity2.startTimeOfRecord)
        assertEquals(sessionRecordEntity1.startBodyValue, sessionRecordEntity2.startBodyValue)
        assertEquals(
            sessionRecordEntity1.startThoughtsValue,
            sessionRecordEntity2.startThoughtsValue
        )
        assertEquals(
            sessionRecordEntity1.startFeelingsValue,
            sessionRecordEntity2.startFeelingsValue
        )
        assertEquals(sessionRecordEntity1.startGlobalValue, sessionRecordEntity2.startGlobalValue)

        assertEquals(sessionRecordEntity1.endTimeOfRecord, sessionRecordEntity2.endTimeOfRecord)
        assertEquals(sessionRecordEntity1.endBodyValue, sessionRecordEntity2.endBodyValue)
        assertEquals(sessionRecordEntity1.endThoughtsValue, sessionRecordEntity2.endThoughtsValue)
        assertEquals(sessionRecordEntity1.endFeelingsValue, sessionRecordEntity2.endFeelingsValue)
        assertEquals(sessionRecordEntity1.endGlobalValue, sessionRecordEntity2.endGlobalValue)

        assertEquals(sessionRecordEntity1.notes, sessionRecordEntity2.notes)
        assertEquals(sessionRecordEntity1.realDuration, sessionRecordEntity2.realDuration)
        assertEquals(sessionRecordEntity1.pausesCount, sessionRecordEntity2.pausesCount)
        assertEquals(
            sessionRecordEntity1.realDurationVsPlanned,
            sessionRecordEntity2.realDurationVsPlanned
        )
        assertEquals(sessionRecordEntity1.guideMp3, sessionRecordEntity2.guideMp3)
    }

    fun generateSession(
        id: Long = -1L,
        startMood: Mood = generateMood(
            1980, 5, 2, 15, 27, 54,
            MoodValue.NOT_SET,
            MoodValue.BEST,
            MoodValue.BAD,
            MoodValue.GOOD
        ),
        endMood: Mood = generateMood(
            1982, 6, 3, 17, 45, 3,
            MoodValue.NOT_SET,
            MoodValue.BEST,
            MoodValue.BAD,
            MoodValue.GOOD
        ),
        notes: String = "generateSession default notes",
        realDuration: Long = 123456L,
        pausesCount: Int = 0,
        realDurationVsPlanned: RealDurationVsPlanned = RealDurationVsPlanned.EQUAL,
        guideMp3: String = "generateSession default guideMp3"
    ): SessionRecord {
        if (id == -1L) {
            return SessionRecord(
                startMood = startMood,
                endMood = endMood,
                notes = notes,
                realDuration = realDuration,
                pausesCount = pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = guideMp3
            )
        } else {
            return SessionRecord(
                id = id,
                startMood = startMood,
                endMood = endMood,
                notes = notes,
                realDuration = realDuration,
                pausesCount = pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = guideMp3
            )
        }
    }

    fun generateSessionRecordEntity(
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
        realDuration: Long = 1590000,
        pausesCount: Int = 7,
        realDurationVsPlanned: Int = 0,
        guideMp3: String = "generateSessionRecordEntity default guideMp3"
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
                guideMp3 = guideMp3
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
                guideMp3 = guideMp3
            )
        }
    }


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