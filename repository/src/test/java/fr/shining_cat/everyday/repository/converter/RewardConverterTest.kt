package fr.shining_cat.everyday.repository.converter

import org.junit.Assert.assertEquals
import org.junit.Test

import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.testutils.dto.RewardDTOTestUtils
import fr.shining_cat.everyday.testutils.model.RewardModelTestUtils
import java.util.*

class RewardConverterTest{

    @Test
    fun convertModelToDTO(){
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(generateRewardModel())
        assertEqualTwoDTOsWithoutId(rewardDTOTranslated, generateRewardDto())
    }

    @Test
    fun convertDTOtoModel(){
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(generateRewardDto(25))
        //here we check the ID field conversion
        assertEquals(rewardModelTranslated, generateRewardModel(25))
    }

    @Test
    fun convertDTOtoModelToDTO(){
        val rewardDTO = generateRewardDto()
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTO)
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModelTranslated)
        assertEqualTwoDTOsWithoutId(rewardDTO, rewardDTOTranslated)
    }

    @Test
    fun convertModelToDTOToModel(){
        val rewardModel = generateRewardModel()
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModel)
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTOTranslated)
        assetEqualTwoModelsWithoutId(rewardModel, rewardModelTranslated)
    }

    private fun generateRewardDto(desiredId: Long = -1): RewardDTO{
        return RewardDTOTestUtils.generateReward(desiredLevel = 5,
            active = true,
            escaped = false,
            desiredId = desiredId,
            desiredCode = "5_1_4_5_5_0",
            yearAcquired = 1987,
            monthAcquired = 2,
            dayAcquired = 9,
            yearEscaped = 1995,
            monthEscaped = 9,
            dayEscaped = 11,
            desiredName = "convertDTOtoModel is my name",
            desiredLegsColor = "convertDTOtoModel legs color",
            desiredBodyColor = "convertDTOtoModel body color",
            desiredArmsColor = "convertDTOtoModel arms color"
        )
    }

    private fun generateRewardModel(desiredId: Long = -1): RewardModel{
        return RewardModelTestUtils.generateRewardModel(desiredLevel = 5,
                                                        active = true,
                                                        escaped = false,
                                                        desiredId = desiredId,
                                                        desiredCode = "5_1_4_5_5_0",
                                                        yearAcquired = 1987,
                                                        monthAcquired = 2,
                                                        dayAcquired = 9,
                                                        yearEscaped = 1995,
                                                        monthEscaped = 9,
                                                        dayEscaped = 11,
                                                        desiredName = "convertDTOtoModel is my name",
                                                        desiredLegsColor = "convertDTOtoModel legs color",
                                                        desiredBodyColor = "convertDTOtoModel body color",
                                                        desiredArmsColor = "convertDTOtoModel arms color")
    }

    private fun assetEqualTwoModelsWithoutId(rewardModel1: RewardModel, rewardModel2: RewardModel){
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

    private fun assertEqualTwoDTOsWithoutId(rewardDTO1: RewardDTO, rewardDTOl2: RewardDTO){
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