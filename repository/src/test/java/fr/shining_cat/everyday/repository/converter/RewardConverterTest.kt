package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.testutils.dto.RewardDTOTestUtils
import fr.shining_cat.everyday.testutils.model.RewardModelTestUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class RewardConverterTest{

    @Test
    fun convertModelToDTO(){
        val rewardModel = RewardModelTestUtils.rewardModel_4_1_6_2_0_0
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModel)
        compareTwoDTOsWithoutId(rewardDTOTranslated, RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0)
    }

    @Test
    fun convertDTOtoModel(){
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTO)
        //here we check the ID field conversion
        assertEquals(rewardModelTranslated, RewardModelTestUtils.rewardModel_4_1_6_2_0_0)
    }

    @Test
    fun convertDTOtoModelToDTO(){
        val rewardDTO = RewardDTOTestUtils.rewardDTO_4_1_6_2_0_0
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTO)
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModelTranslated)
        compareTwoDTOsWithoutId(rewardDTO, rewardDTOTranslated)
    }

    @Test
    fun convertModelToDTOToModel(){
        val rewardModel = RewardModelTestUtils.rewardModel_4_1_6_2_0_0
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModel)
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTOTranslated)
        compareTwoModelsWithoutId(rewardModel, rewardModelTranslated)
    }

    private fun compareTwoModelsWithoutId(rewardModel1: RewardModel, rewardModel2: RewardModel){
        assertEquals(rewardModel1.code, rewardModel2.code)
        assertEquals(rewardModel1.level, rewardModel2.level)
        assertEquals(rewardModel1.acquisitionDate, rewardModel2.acquisitionDate)
        assertEquals(rewardModel1.escapingDate, rewardModel2.escapingDate)
        assertEquals(rewardModel1.isActive, rewardModel2.isActive)
        assertEquals(rewardModel1.isEscaped, rewardModel2.isEscaped)
        assertEquals(rewardModel1.name, rewardModel2.name)
        assertEquals(rewardModel1.legsColor, rewardModel2.legsColor)
        assertEquals(rewardModel1.bodyColor, rewardModel2.bodyColor)
        assertEquals(rewardModel1.armsColor, rewardModel2.armsColor)
    }

    private fun compareTwoDTOsWithoutId(rewardDTO1: RewardDTO, rewardDTOl2: RewardDTO){
        assertEquals(rewardDTO1.code,  rewardDTOl2.code)
        assertEquals(rewardDTO1.level,  rewardDTOl2.level)
        assertEquals(rewardDTO1.acquisitionDate,  rewardDTOl2.acquisitionDate)
        assertEquals(rewardDTO1.escapingDate,  rewardDTOl2.escapingDate)
        assertEquals(rewardDTO1.isActive, rewardDTOl2.isActive)
        assertEquals(rewardDTO1.isEscaped, rewardDTOl2.isEscaped)
        assertEquals(rewardDTO1.name,  rewardDTOl2.name)
        assertEquals(rewardDTO1.legsColor,  rewardDTOl2.legsColor)
        assertEquals(rewardDTO1.bodyColor,  rewardDTOl2.bodyColor)
        assertEquals(rewardDTO1.armsColor,  rewardDTOl2.armsColor)
    }











}