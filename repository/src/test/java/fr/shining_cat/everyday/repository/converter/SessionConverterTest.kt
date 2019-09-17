package fr.shining_cat.everyday.repository.converter

import org.junit.Test
import org.junit.Assert.assertEquals

import fr.shining_cat.everyday.localdata.dto.SessionDTO
import fr.shining_cat.everyday.model.SessionModel
import fr.shining_cat.everyday.testutils.dto.SessionDTOTestUtils
import fr.shining_cat.everyday.testutils.model.SessionModelTestUtils

class SessionConverterTest {

    @Test
    fun convertModelToDTO() {
        val sessionModel = SessionModelTestUtils.sessionModelTestA
        val sessionDTOtranslated = SessionConverter.convertModelToDTO(sessionModel)
        compareTwoDTOsWithoutId(sessionDTOtranslated, SessionDTOTestUtils.sessionDTOTestA)
    }

    @Test
    fun convertDTOtoModel(){
        val sessionDTO = SessionDTOTestUtils.sessionDTOTestA
        val sessionModelTranslated = SessionConverter.convertDTOtoModel(sessionDTO)
        //here we check the ID field conversion
        assertEquals(sessionModelTranslated, SessionModelTestUtils.sessionModelTestA)
    }

    @Test
    fun convertDTOtoModelToDTO(){
        val sessionDTO = SessionDTOTestUtils.sessionDTOTestA
        val sessionModelTranslated = SessionConverter.convertDTOtoModel(sessionDTO)
        val sessionDTOtranslated = SessionConverter.convertModelToDTO(sessionModelTranslated)
        compareTwoDTOsWithoutId(sessionDTO, sessionDTOtranslated)
    }

    @Test
    fun convertModelToDTOToModel(){
        val sessionModel = SessionModelTestUtils.sessionModelTestA
        val sessionDTOtranslated = SessionConverter.convertModelToDTO(sessionModel)
        val sessionModelTranslated = SessionConverter.convertDTOtoModel(sessionDTOtranslated)
        compareTwoModelsWithoutId(sessionModel, sessionModelTranslated)
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