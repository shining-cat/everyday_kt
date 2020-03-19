package fr.shining_cat.everyday.repository.converter

import org.junit.Test
import org.junit.Assert.assertEquals

import fr.shining_cat.everyday.locale.entities.SessionDTO
import fr.shining_cat.everyday.models.MoodValue
import fr.shining_cat.everyday.models.RealDurationVsPlanned
import fr.shining_cat.everyday.models.SessionModel
import fr.shining_cat.everyday.testutils.dto.SessionDTOTestUtils
import fr.shining_cat.everyday.testutils.model.MoodModelTestUtils
import fr.shining_cat.everyday.testutils.model.SessionModelTestUtils

class SessionConverterTest {

    @Test
    fun convertIntToRealDurationVsPlanned(){
        assertEquals(RealDurationVsPlanned.EQUAL, SessionConverter.convertIntToRealDurationVsPlanned(0))
        assertEquals(RealDurationVsPlanned.REAL_SHORTER, SessionConverter.convertIntToRealDurationVsPlanned(-1))
        assertEquals(RealDurationVsPlanned.REAL_LONGER, SessionConverter.convertIntToRealDurationVsPlanned(1))
    }

    @Test
    fun convertRealDurationVsPlannedtoInt(){
        assertEquals(0, SessionConverter.convertRealDurationVsPlannedtoInt(RealDurationVsPlanned.EQUAL))
        assertEquals(-1, SessionConverter.convertRealDurationVsPlannedtoInt(RealDurationVsPlanned.REAL_SHORTER))
        assertEquals(1, SessionConverter.convertRealDurationVsPlannedtoInt(RealDurationVsPlanned.REAL_LONGER))
    }

    @Test
    fun convertMoodValueToInt(){
        assertEquals(-2, SessionConverter.convertMoodValueToInt(MoodValue.WORST))
        assertEquals(-1, SessionConverter.convertMoodValueToInt(MoodValue.BAD))
        assertEquals(0, SessionConverter.convertMoodValueToInt(MoodValue.NOT_SET))
        assertEquals(1, SessionConverter.convertMoodValueToInt(MoodValue.GOOD))
        assertEquals(2, SessionConverter.convertMoodValueToInt(MoodValue.BEST))
    }

    @Test
    fun convertIntToMoodValue(){
        assertEquals(MoodValue.WORST, SessionConverter.convertIntToMoodValue(-2))
        assertEquals(MoodValue.BAD, SessionConverter.convertIntToMoodValue(-1))
        assertEquals(MoodValue.NOT_SET, SessionConverter.convertIntToMoodValue(0))
        assertEquals(MoodValue.GOOD, SessionConverter.convertIntToMoodValue(1))
        assertEquals(MoodValue.BEST, SessionConverter.convertIntToMoodValue(2))
    }

    @Test
    fun convertModelToDTO() {
        //without ID
        val sessionModel = SessionModelTestUtils.generateSession(
            startMood = MoodModelTestUtils.generateMood(1980, 5, 2, 15, 27, 54, -2, 0, 1, 2),
            endMood =   MoodModelTestUtils.generateMood(1981,6,3,17,45,3,0, 1, -1, -2),
            notes = "convertModelToDTO testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToDTO testing guideMp3 string"
        )
        val sessionDto = SessionDTOTestUtils.generateSessionDTO(
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
            notes = "convertModelToDTO testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToDTO testing guideMp3 string"
        )
        val sessionDTOtranslated = SessionConverter.convertModelToDTO(sessionModel)
        compareTwoDTOsWithoutId(sessionDTOtranslated, sessionDto)
        //with ID
        val sessionModelWithId = SessionModelTestUtils.generateSession(
            id = 41,
            startMood = MoodModelTestUtils.generateMood(1980, 5, 2, 15, 27, 54, -2, 0, 1, 2),
            endMood =   MoodModelTestUtils.generateMood(1981,6,3,17,45,3,0, 1, -1, -2),
            notes = "convertModelToDTO testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToDTO testing guideMp3 string"
        )
        val sessionDtoWithId = SessionDTOTestUtils.generateSessionDTO(
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
            notes = "convertModelToDTO testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToDTO testing guideMp3 string"
        )
        val sessionDTOtranslatedWithId = SessionConverter.convertModelToDTO(sessionModelWithId)
        assertEquals(sessionDTOtranslatedWithId, sessionDtoWithId)
    }

    @Test
    fun convertDTOtoModel(){
        //with ID
        val sessionModelWithId = SessionModelTestUtils.generateSession(
            id = 41,
            startMood = MoodModelTestUtils.generateMood(1980, 5, 2, 15, 27, 54, -2, 0, 1, 2),
            endMood =   MoodModelTestUtils.generateMood(1981,6,3,17,45,3,0, 1, -1, -2),
            notes = "convertDTOtoModel testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertDTOtoModel testing guideMp3 string"
        )
        val sessionDtoWithId = SessionDTOTestUtils.generateSessionDTO(
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
            notes = "convertDTOtoModel testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertDTOtoModel testing guideMp3 string"
        )
        val sessionModelTranslatedWithId = SessionConverter.convertDTOtoModel(sessionDtoWithId)
        //here we check the ID field conversion
        assertEquals(sessionModelTranslatedWithId, sessionModelWithId)
    }

    @Test
    fun convertDTOtoModelToDTO(){
        //with ID
        val sessionDto = SessionDTOTestUtils.generateSessionDTO(
            desiredId = 76,
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
            notes = "convertDTOtoModelToDTO testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertDTOtoModelToDTO testing guideMp3 string"
        )
        val sessionModelTranslated = SessionConverter.convertDTOtoModel(sessionDto)
        val sessionDTOtranslated = SessionConverter.convertModelToDTO(sessionModelTranslated)
        compareTwoDTOsWithoutId(sessionDto, sessionDTOtranslated)
    }

    @Test
    fun convertModelToDTOToModel(){
        //without ID
        val sessionModel = SessionModelTestUtils.generateSession(
            startMood = MoodModelTestUtils.generateMood(1980, 5, 2, 15, 27, 54, -2, 0, 1, 2),
            endMood =   MoodModelTestUtils.generateMood(1981,6,3,17,45,3,0, 1, -1, -2),
            notes = "convertModelToDTOToModel testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToDTOToModel testing guideMp3 string"
        )
        val sessionDTOtranslated = SessionConverter.convertModelToDTO(sessionModel)
        val sessionModelTranslated = SessionConverter.convertDTOtoModel(sessionDTOtranslated)
        compareTwoModelsWithoutId(sessionModel, sessionModelTranslated)
        //with ID
        val sessionModelWithId = SessionModelTestUtils.generateSession(
            id = 94,
            startMood = MoodModelTestUtils.generateMood(1980, 5, 2, 15, 27, 54, -2, 0, 1, 2),
            endMood =   MoodModelTestUtils.generateMood(1981,6,3,17,45,3,0, 1, -1, -2),
            notes = "convertModelToDTOToModel testing notes string",
            realDuration = 1500000,
            pausesCount = 3,
            realDurationVsPlanned = -1,
            guideMp3 = "convertModelToDTOToModel testing guideMp3 string"
        )
        val sessionDTOtranslatedWithId = SessionConverter.convertModelToDTO(sessionModelWithId)
        val sessionModelTranslatedWithId = SessionConverter.convertDTOtoModel(sessionDTOtranslatedWithId)
        compareTwoModelsWithoutId(sessionModelWithId, sessionModelTranslatedWithId)
    }

    private fun compareTwoModelsWithoutId(sessionModel1: SessionModel, sessionModel2: SessionModel){
        assertEquals(sessionModel1.startMood, sessionModel2.startMood)
        assertEquals(sessionModel1.endMood, sessionModel2.endMood)
        assertEquals(sessionModel1.notes, sessionModel2.notes)
        assertEquals(sessionModel1.realDuration, sessionModel2.realDuration)
        assertEquals(sessionModel1.pausesCount, sessionModel2.pausesCount)
        assertEquals(sessionModel1.realDurationVsPlanned, sessionModel2.realDurationVsPlanned)
        assertEquals(sessionModel1.guideMp3, sessionModel2.guideMp3)
    }

    private fun compareTwoDTOsWithoutId(sessionDTO1: SessionDTO, sessionDTO2: SessionDTO){
        assertEquals(sessionDTO1.startTimeOfRecord, sessionDTO2.startTimeOfRecord)
        assertEquals(sessionDTO1.startBodyValue, sessionDTO2.startBodyValue)
        assertEquals(sessionDTO1.startThoughtsValue, sessionDTO2.startThoughtsValue)
        assertEquals(sessionDTO1.startFeelingsValue, sessionDTO2.startFeelingsValue)
        assertEquals(sessionDTO1.startGlobalValue, sessionDTO2.startGlobalValue)

        assertEquals(sessionDTO1.endTimeOfRecord, sessionDTO2.endTimeOfRecord)
        assertEquals(sessionDTO1.endBodyValue, sessionDTO2.endBodyValue)
        assertEquals(sessionDTO1.endThoughtsValue, sessionDTO2.endThoughtsValue)
        assertEquals(sessionDTO1.endFeelingsValue, sessionDTO2.endFeelingsValue)
        assertEquals(sessionDTO1.endGlobalValue, sessionDTO2.endGlobalValue)

        assertEquals(sessionDTO1.notes, sessionDTO2.notes)
        assertEquals(sessionDTO1.realDuration, sessionDTO2.realDuration)
        assertEquals(sessionDTO1.pausesCount, sessionDTO2.pausesCount)
        assertEquals(sessionDTO1.realDurationVsPlanned, sessionDTO2.realDurationVsPlanned)
        assertEquals(sessionDTO1.guideMp3, sessionDTO2.guideMp3)
    }
}