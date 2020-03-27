package fr.shining_cat.everyday.repository.converter

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SessionRecordConverterTest {

    private lateinit var sessionRecordConverter: SessionRecordConverter
    
    @Before
    fun setUp() {
        sessionRecordConverter = SessionRecordConverter()
    }
    @Test
    fun tests(){
        Assert.fail("TODO: write correct tests")
    }
    
//    @Test
//    fun convertIntToRealDurationVsPlanned(){
//        assertEquals(RealDurationVsPlanned.EQUAL, SessionRecordConverter.convertIntToRealDurationVsPlanned(0))
//        assertEquals(RealDurationVsPlanned.REAL_SHORTER, SessionRecordConverter.convertIntToRealDurationVsPlanned(-1))
//        assertEquals(RealDurationVsPlanned.REAL_LONGER, SessionRecordConverter.convertIntToRealDurationVsPlanned(1))
//    }
//
//    @Test
//    fun convertRealDurationVsPlannedtoInt(){
//        assertEquals(0, SessionRecordConverter.convertRealDurationVsPlannedtoInt(RealDurationVsPlanned.EQUAL))
//        assertEquals(-1, SessionRecordConverter.convertRealDurationVsPlannedtoInt(RealDurationVsPlanned.REAL_SHORTER))
//        assertEquals(1, SessionRecordConverter.convertRealDurationVsPlannedtoInt(RealDurationVsPlanned.REAL_LONGER))
//    }
//
//    @Test
//    fun convertMoodValueToInt(){
//        assertEquals(-2, SessionRecordConverter.convertMoodValueToInt(MoodValue.WORST))
//        assertEquals(-1, SessionRecordConverter.convertMoodValueToInt(MoodValue.BAD))
//        assertEquals(0, SessionRecordConverter.convertMoodValueToInt(MoodValue.NOT_SET))
//        assertEquals(1, SessionRecordConverter.convertMoodValueToInt(MoodValue.GOOD))
//        assertEquals(2, SessionRecordConverter.convertMoodValueToInt(MoodValue.BEST))
//    }
//
//    @Test
//    fun convertIntToMoodValue(){
//        assertEquals(MoodValue.WORST, SessionRecordConverter.convertIntToMoodValue(-2))
//        assertEquals(MoodValue.BAD, SessionRecordConverter.convertIntToMoodValue(-1))
//        assertEquals(MoodValue.NOT_SET, SessionRecordConverter.convertIntToMoodValue(0))
//        assertEquals(MoodValue.GOOD, SessionRecordConverter.convertIntToMoodValue(1))
//        assertEquals(MoodValue.BEST, SessionRecordConverter.convertIntToMoodValue(2))
//    }

//    @Test
//    fun convertModelToEntity() {
//        //without ID
//        val sessionModel = generateSession(
//            startMood = generateMood(1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST),
//            endMood =   generateMood(1981,6,3,17,45,3, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST),
//            notes = "convertModelToDTO testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
//            guideMp3 = "convertModelToDTO testing guideMp3 string"
//        )
//        val sessionDto = SessionEntityTestUtils.generateSessionEntity(
//            yearstart = 1980,
//            monthstart = 5,
//            dayOfMonthstart = 2,
//            hourOfDaystart = 15,
//            minutestart = 27,
//            secondstart = 54,
//            startBodyValue = -2,
//            startThoughtsValue = 0,
//            startFeelingsValue = 1,
//            startGlobalValue = 2,
//            yearend = 1981,
//            monthend = 6,
//            dayOfMonthend = 3,
//            hourOfDayend = 17,
//            minuteend = 45,
//            secondend = 3,
//            endBodyValue = 0,
//            endThoughtsValue = 1,
//            endFeelingsValue = -1,
//            endGlobalValue = -2,
//            notes = "convertModelToDTO testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = -1,
//            guideMp3 = "convertModelToDTO testing guideMp3 string"
//        )
//        val sessionDTOtranslated = sessionRecordConverter.convertModelToEntity(sessionModel)
//        compareTwoDTOsWithoutId(sessionDTOtranslated, sessionDto)
//        //with ID
//        val sessionModelWithId = generateSession(
//            id = 41,
//            startMood = generateMood(1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST),
//            endMood =   generateMood(1981,6,3,17,45,3,MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST),
//            notes = "convertModelToDTO testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
//            guideMp3 = "convertModelToDTO testing guideMp3 string"
//        )
//        val sessionDtoWithId = SessionEntityTestUtils.generateSessionEntity(
//            desiredId = 41,
//            yearstart = 1980,
//            monthstart = 5,
//            dayOfMonthstart = 2,
//            hourOfDaystart = 15,
//            minutestart = 27,
//            secondstart = 54,
//            startBodyValue = -2,
//            startThoughtsValue = 0,
//            startFeelingsValue = 1,
//            startGlobalValue = 2,
//            yearend = 1981,
//            monthend = 6,
//            dayOfMonthend = 3,
//            hourOfDayend = 17,
//            minuteend = 45,
//            secondend = 3,
//            endBodyValue = 0,
//            endThoughtsValue = 1,
//            endFeelingsValue = -1,
//            endGlobalValue = -2,
//            notes = "convertModelToDTO testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = -1,
//            guideMp3 = "convertModelToDTO testing guideMp3 string"
//        )
//        val sessionDTOtranslatedWithId = sessionRecordConverter.convertModelToEntity(sessionModelWithId)
//        assertEquals(sessionDTOtranslatedWithId, sessionDtoWithId)
//    }
//
//    @Test
//    fun convertDTOtoModel(){
//        //with ID
//        val sessionModelWithId = generateSession(
//            id = 41,
//            startMood = generateMood(1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST),
//            endMood =   generateMood(1981,6,3,17,45,3,MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST),
//            notes = "convertDTOtoModel testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
//            guideMp3 = "convertDTOtoModel testing guideMp3 string"
//        )
//        val sessionDtoWithId = SessionEntityTestUtils.generateSessionEntity(
//            desiredId = 41,
//            yearstart = 1980,
//            monthstart = 5,
//            dayOfMonthstart = 2,
//            hourOfDaystart = 15,
//            minutestart = 27,
//            secondstart = 54,
//            startBodyValue = -2,
//            startThoughtsValue = 0,
//            startFeelingsValue = 1,
//            startGlobalValue = 2,
//            yearend = 1981,
//            monthend = 6,
//            dayOfMonthend = 3,
//            hourOfDayend = 17,
//            minuteend = 45,
//            secondend = 3,
//            endBodyValue = 0,
//            endThoughtsValue = 1,
//            endFeelingsValue = -1,
//            endGlobalValue = -2,
//            notes = "convertDTOtoModel testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = -1,
//            guideMp3 = "convertDTOtoModel testing guideMp3 string"
//        )
//        val sessionModelTranslatedWithId = sessionRecordConverter.convertEntitytoModel(sessionDtoWithId)
//        //here we check the ID field conversion
//        assertEquals(sessionModelTranslatedWithId, sessionModelWithId)
//    }
//
//    @Test
//    fun convertDTOtoModelToDTO(){
//        //with ID
//        val sessionDto = SessionEntityTestUtils.generateSessionEntity(
//            desiredId = 76,
//            yearstart = 1980,
//            monthstart = 5,
//            dayOfMonthstart = 2,
//            hourOfDaystart = 15,
//            minutestart = 27,
//            secondstart = 54,
//            startBodyValue = -2,
//            startThoughtsValue = 0,
//            startFeelingsValue = 1,
//            startGlobalValue = 2,
//            yearend = 1981,
//            monthend = 6,
//            dayOfMonthend = 3,
//            hourOfDayend = 17,
//            minuteend = 45,
//            secondend = 3,
//            endBodyValue = 0,
//            endThoughtsValue = 1,
//            endFeelingsValue = -1,
//            endGlobalValue = -2,
//            notes = "convertDTOtoModelToDTO testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = -1,
//            guideMp3 = "convertDTOtoModelToDTO testing guideMp3 string"
//        )
//        val sessionModelTranslated = sessionRecordConverter.convertEntitytoModel(sessionDto)
//        val sessionDTOtranslated = sessionRecordConverter.convertModelToEntity(sessionModelTranslated)
//        compareTwoDTOsWithoutId(sessionDto, sessionDTOtranslated)
//    }
//
//    @Test
//    fun convertModelToDTOToModel(){
//        //without ID
//        val sessionModel = generateSession(
//            startMood = generateMood(1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST),
//            endMood =   generateMood(1981,6,3,17,45,3,MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST),
//            notes = "convertModelToDTOToModel testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
//            guideMp3 = "convertModelToDTOToModel testing guideMp3 string"
//        )
//        val sessionDTOtranslated = sessionRecordConverter.convertModelToEntity(sessionModel)
//        val sessionModelTranslated = sessionRecordConverter.convertEntitytoModel(sessionDTOtranslated)
//        compareTwoModelsWithoutId(sessionModel, sessionModelTranslated)
//        //with ID
//        val sessionModelWithId = generateSession(
//            id = 94,
//            startMood = generateMood(1980, 5, 2, 15, 27, 54, MoodValue.WORST, MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BEST),
//            endMood =   generateMood(1981,6,3,17,45,3,MoodValue.NOT_SET, MoodValue.GOOD, MoodValue.BAD, MoodValue.WORST),
//            notes = "convertModelToDTOToModel testing notes string",
//            realDuration = 1500000,
//            pausesCount = 3,
//            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
//            guideMp3 = "convertModelToDTOToModel testing guideMp3 string"
//        )
//        val sessionDTOtranslatedWithId = sessionRecordConverter.convertModelToEntity(sessionModelWithId)
//        val sessionModelTranslatedWithId = sessionRecordConverter.convertEntitytoModel(sessionDTOtranslatedWithId)
//        compareTwoModelsWithoutId(sessionModelWithId, sessionModelTranslatedWithId)
//    }
//
//    private fun compareTwoModelsWithoutId(sessionRecord1: SessionRecord, sessionRecord2: SessionRecord){
//        assertEquals(sessionRecord1.startMood, sessionRecord2.startMood)
//        assertEquals(sessionRecord1.endMood, sessionRecord2.endMood)
//        assertEquals(sessionRecord1.notes, sessionRecord2.notes)
//        assertEquals(sessionRecord1.realDuration, sessionRecord2.realDuration)
//        assertEquals(sessionRecord1.pausesCount, sessionRecord2.pausesCount)
//        assertEquals(sessionRecord1.realDurationVsPlanned, sessionRecord2.realDurationVsPlanned)
//        assertEquals(sessionRecord1.guideMp3, sessionRecord2.guideMp3)
//    }
//
//    private fun compareTwoDTOsWithoutId(sessionRecordEntity1: SessionRecordEntity, sessionRecordEntity2: SessionRecordEntity){
//        assertEquals(sessionRecordEntity1.startTimeOfRecord, sessionRecordEntity2.startTimeOfRecord)
//        assertEquals(sessionRecordEntity1.startBodyValue, sessionRecordEntity2.startBodyValue)
//        assertEquals(sessionRecordEntity1.startThoughtsValue, sessionRecordEntity2.startThoughtsValue)
//        assertEquals(sessionRecordEntity1.startFeelingsValue, sessionRecordEntity2.startFeelingsValue)
//        assertEquals(sessionRecordEntity1.startGlobalValue, sessionRecordEntity2.startGlobalValue)
//
//        assertEquals(sessionRecordEntity1.endTimeOfRecord, sessionRecordEntity2.endTimeOfRecord)
//        assertEquals(sessionRecordEntity1.endBodyValue, sessionRecordEntity2.endBodyValue)
//        assertEquals(sessionRecordEntity1.endThoughtsValue, sessionRecordEntity2.endThoughtsValue)
//        assertEquals(sessionRecordEntity1.endFeelingsValue, sessionRecordEntity2.endFeelingsValue)
//        assertEquals(sessionRecordEntity1.endGlobalValue, sessionRecordEntity2.endGlobalValue)
//
//        assertEquals(sessionRecordEntity1.notes, sessionRecordEntity2.notes)
//        assertEquals(sessionRecordEntity1.realDuration, sessionRecordEntity2.realDuration)
//        assertEquals(sessionRecordEntity1.pausesCount, sessionRecordEntity2.pausesCount)
//        assertEquals(sessionRecordEntity1.realDurationVsPlanned, sessionRecordEntity2.realDurationVsPlanned)
//        assertEquals(sessionRecordEntity1.guideMp3, sessionRecordEntity2.guideMp3)
//    }
//
//    fun generateSession(
//        id: Long = -1L,
//        startMood: Mood = MoodModelTestUtils.generateMood(
//            1980, 5, 2, 15, 27, 54,
//            MoodValue.NOT_SET,
//            MoodValue.BEST,
//            MoodValue.BAD,
//            MoodValue.GOOD
//        ),
//        endMood: Mood = MoodModelTestUtils.generateMood(1982, 6, 3, 17, 45, 3,
//            MoodValue.NOT_SET,
//            MoodValue.BEST,
//            MoodValue.BAD,
//            MoodValue.GOOD),
//        notes: String = "generateSession default notes",
//        realDuration: Long = 123456L,
//        pausesCount: Int = 0,
//        realDurationVsPlanned: RealDurationVsPlanned = RealDurationVsPlanned.EQUAL,
//        guideMp3: String = "generateSession default guideMp3"
//    ): SessionRecord {
//        if (id == -1L) {
//            return SessionRecord(
//                startMood = startMood,
//                endMood = endMood,
//                notes = notes,
//                realDuration = realDuration,
//                pausesCount = pausesCount,
//                realDurationVsPlanned = realDurationVsPlanned,
//                guideMp3 = guideMp3
//            )
//        } else {
//            return SessionRecord(
//                id = id,
//                startMood = startMood,
//                endMood = endMood,
//                notes = notes,
//                realDuration = realDuration,
//                pausesCount = pausesCount,
//                realDurationVsPlanned = realDurationVsPlanned,
//                guideMp3 = guideMp3
//            )
//        }
//    }
//
//    fun generateMood(
//        year: Int,
//        month: Int,
//        dayOfMonth: Int,
//        hourOfDay: Int,
//        minute: Int = 0,
//        second: Int = 0,
//        bodyValue: MoodValue = MoodValue.NOT_SET,
//        thoughtsValue: MoodValue = MoodValue.BEST,
//        feelingsValue: MoodValue = MoodValue.BAD,
//        globalValue: MoodValue = MoodValue.GOOD
//    ): Mood {
//        return Mood(
//            timeOfRecord = GregorianCalendar(
//                year,
//                month,
//                dayOfMonth,
//                hourOfDay,
//                minute,
//                second
//            ).timeInMillis,
//            bodyValue = bodyValue,
//            thoughtsValue = thoughtsValue,
//            feelingsValue = feelingsValue,
//            globalValue = globalValue
//        )
//    }
}