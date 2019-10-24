package fr.shining_cat.everyday.repository.converter

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals
import org.junit.Test

import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.model.Critter
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.repository.AbstractBaseTest
import fr.shining_cat.everyday.testutils.dto.RewardDTOTestUtils
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import fr.shining_cat.everyday.testutils.model.RewardModelTestUtils
import org.junit.Assert
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class RewardConverterTest: AbstractBaseTest(){

    @Mock
    lateinit var rewardDTOsLive: LiveData<List<RewardDTO>?>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Assert.assertNotNull(rewardDTOsLive)
    }

    @Test
    fun convertModelsToDTOs(){
        val rewardModelsList = RewardModelTestUtils.generateRewards(10)
        assertEquals(10, rewardModelsList.size)
        val rewardsTranslatedList = RewardConverter.convertModelsToDTOs(rewardModelsList)
        assertEquals(10, rewardsTranslatedList.size)
    }

    @Test
    fun convertModelToDTO(){
        //without ID
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(RewardModelTestUtils.generateReward(
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        ))
        val rewardDTOGenerated = RewardDTOTestUtils.generateReward(
            desiredLevel = rewardDTOTranslated.level,
            active =rewardDTOTranslated.isActive,
            escaped =rewardDTOTranslated.isEscaped,
            desiredId =rewardDTOTranslated.id,
            desiredCode = rewardDTOTranslated.code,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7,
            desiredName = rewardDTOTranslated.name,
            desiredLegsColor = rewardDTOTranslated.legsColor,
            desiredBodyColor = rewardDTOTranslated.bodyColor,
            desiredArmsColor = rewardDTOTranslated.armsColor
            )
        assertEqualTwoDTOsWithoutId(rewardDTOTranslated, rewardDTOGenerated)
        //with ID
        val rewardDTOTranslatedWithId = RewardConverter.convertModelToDTO(RewardModelTestUtils.generateReward(
            desiredId = 25,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        ))
        val rewardDTOGeneratedWithId = RewardDTOTestUtils.generateReward(
            desiredLevel = rewardDTOTranslatedWithId.level,
            active =rewardDTOTranslatedWithId.isActive,
            escaped =rewardDTOTranslatedWithId.isEscaped,
            desiredId =rewardDTOTranslatedWithId.id,
            desiredCode = rewardDTOTranslatedWithId.code,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7,
            desiredName = rewardDTOTranslatedWithId.name,
            desiredLegsColor = rewardDTOTranslatedWithId.legsColor,
            desiredBodyColor = rewardDTOTranslatedWithId.bodyColor,
            desiredArmsColor = rewardDTOTranslatedWithId.armsColor
            )
        assertEquals(rewardDTOTranslatedWithId, rewardDTOGeneratedWithId)
    }


    @Test
    fun convertDTOtoModel(){
        //only with ID
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(RewardDTOTestUtils.generateReward(
            desiredLevel = 3,
            desiredId = 25,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        ))
        assertEquals(rewardModelTranslated, RewardModelTestUtils.generateReward(
            desiredLevel = 3,
            active =rewardModelTranslated.isActive,
            escaped =rewardModelTranslated.isEscaped,
            desiredId =rewardModelTranslated.id,
            desiredCode = rewardModelTranslated.code,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7,
            desiredName = rewardModelTranslated.name,
            desiredLegsColor = rewardModelTranslated.legsColor,
            desiredBodyColor = rewardModelTranslated.bodyColor,
            desiredArmsColor = rewardModelTranslated.armsColor
        ))
    }

    @Test
    fun convertDTOtoModelToDTO(){
        //without ID
        val rewardDTO = RewardDTOTestUtils.generateReward(
            desiredLevel = 3,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTO)
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModelTranslated)
        assertEqualTwoDTOsWithoutId(rewardDTO, rewardDTOTranslated)
        //with ID
        val rewardDTOWithId = RewardDTOTestUtils.generateReward(
            desiredLevel = 3,
            desiredId = 25,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardModelTranslatedWithId = RewardConverter.convertDTOtoModel(rewardDTOWithId)
        val rewardDTOTranslatedWithId = RewardConverter.convertModelToDTO(rewardModelTranslatedWithId)
        assertEqualTwoDTOsWithoutId(rewardDTOWithId, rewardDTOTranslatedWithId)
    }

    @Test
    fun convertModelToDTOToModel(){
        //without ID
        val rewardModel = RewardModelTestUtils.generateReward(
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardDTOTranslated = RewardConverter.convertModelToDTO(rewardModel)
        val rewardModelTranslated = RewardConverter.convertDTOtoModel(rewardDTOTranslated)
        assertEqualTwoModelsWithoutId(rewardModel, rewardModelTranslated)
        //with ID
        val rewardModelWithId = RewardModelTestUtils.generateReward(
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardDTOTranslatedWithId = RewardConverter.convertModelToDTO(rewardModelWithId)
        val rewardModelTranslatedWithId = RewardConverter.convertDTOtoModel(rewardDTOTranslatedWithId)
        assertEqualTwoModelsWithoutId(rewardModelWithId, rewardModelTranslatedWithId)
    }

    private fun assertEqualTwoModelsWithoutId(rewardModel1: RewardModel, rewardModel2: RewardModel){
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