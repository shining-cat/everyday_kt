package fr.shining_cat.everyday.repository.converter

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.CritterHelper
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RewardConverterTest: AbstractBaseTest(){

    @Mock
    lateinit var mockRewardEntitiesLive: LiveData<List<RewardEntity>?>
    @Mock
    lateinit var mockCritterHelper: CritterHelper
    
    private lateinit var rewardConverter: RewardConverter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Assert.assertNotNull(mockRewardEntitiesLive)
        Assert.assertNotNull(mockCritterHelper)
        rewardConverter = RewardConverter(
            mockCritterHelper
        )
    }
    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun tests(){
        fail("TODO: write correct tests")
    }
//    @Test
//    fun convertModelsToEntities(){
//        val rewardModelsList = RewardModelTestUtils.generateRewards(10)
//        assertEquals(10, rewardModelsList.size)
//        val rewardsTranslatedList = rewardConverter.convertModelsToEntities(rewardModelsList)
//        assertEquals(10, rewardsTranslatedList.size)
//    }
//
//    @Test
//    fun convertModelToEntity(){
//        //without ID
//        val rewardDTOTranslated = rewardConverter.convertModelToEntity(RewardModelTestUtils.generateReward(
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        ))
//        val rewardDTOGenerated = RewardEntityTestUtils.generateReward(
//            desiredLevel = rewardDTOTranslated.level,
//            active =rewardDTOTranslated.isActive,
//            escaped =rewardDTOTranslated.isEscaped,
//            desiredId =rewardDTOTranslated.id,
//            desiredCode = rewardDTOTranslated.code,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7,
//            desiredName = rewardDTOTranslated.name,
//            desiredLegsColor = rewardDTOTranslated.legsColor,
//            desiredBodyColor = rewardDTOTranslated.bodyColor,
//            desiredArmsColor = rewardDTOTranslated.armsColor
//            )
//        assertEqualTwoDTOsWithoutId(rewardDTOTranslated, rewardDTOGenerated)
//        //with ID
//        val rewardDTOTranslatedWithId = rewardConverter.convertModelToEntity(RewardModelTestUtils.generateReward(
//            desiredId = 25,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        ))
//        val rewardDTOGeneratedWithId = RewardEntityTestUtils.generateReward(
//            desiredLevel = rewardDTOTranslatedWithId.level,
//            active =rewardDTOTranslatedWithId.isActive,
//            escaped =rewardDTOTranslatedWithId.isEscaped,
//            desiredId =rewardDTOTranslatedWithId.id,
//            desiredCode = rewardDTOTranslatedWithId.code,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7,
//            desiredName = rewardDTOTranslatedWithId.name,
//            desiredLegsColor = rewardDTOTranslatedWithId.legsColor,
//            desiredBodyColor = rewardDTOTranslatedWithId.bodyColor,
//            desiredArmsColor = rewardDTOTranslatedWithId.armsColor
//            )
//        assertEquals(rewardDTOTranslatedWithId, rewardDTOGeneratedWithId)
//    }
//
//
//    @Test
//    fun convertDTOtoModel(){
//        //only with ID
//        val rewardModelTranslated = rewardConverter.convertEntitytoModel(RewardEntityTestUtils.generateReward(
//            desiredLevel = 3,
//            desiredId = 25,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        ))
//        assertEquals(rewardModelTranslated, RewardModelTestUtils.generateReward(
//            desiredLevel = 3,
//            active =rewardModelTranslated.isActive,
//            escaped =rewardModelTranslated.isEscaped,
//            desiredId =rewardModelTranslated.id,
//            desiredCode = rewardModelTranslated.code,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7,
//            desiredName = rewardModelTranslated.name,
//            desiredLegsColor = rewardModelTranslated.legsColor,
//            desiredBodyColor = rewardModelTranslated.bodyColor,
//            desiredArmsColor = rewardModelTranslated.armsColor
//        ))
//    }
//
//    @Test
//    fun convertDTOtoModelToDTO(){
//        //without ID
//        val rewardDTO = RewardEntityTestUtils.generateReward(
//            desiredLevel = 3,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        )
//        val rewardModelTranslated = rewardConverter.convertEntitytoModel(rewardDTO)
//        val rewardDTOTranslated = rewardConverter.convertModelToEntity(rewardModelTranslated)
//        assertEqualTwoDTOsWithoutId(rewardDTO, rewardDTOTranslated)
//        //with ID
//        val rewardDTOWithId = RewardEntityTestUtils.generateReward(
//            desiredLevel = 3,
//            desiredId = 25,
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        )
//        val rewardModelTranslatedWithId = rewardConverter.convertEntitytoModel(rewardDTOWithId)
//        val rewardDTOTranslatedWithId = rewardConverter.convertModelToEntity(rewardModelTranslatedWithId)
//        assertEqualTwoDTOsWithoutId(rewardDTOWithId, rewardDTOTranslatedWithId)
//    }
//
//    @Test
//    fun convertModelToDTOToModel(){
//        //without ID
//        val rewardModel = RewardModelTestUtils.generateReward(
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        )
//        val rewardDTOTranslated = rewardConverter.convertModelToEntity(rewardModel)
//        val rewardModelTranslated = rewardConverter.convertEntitytoModel(rewardDTOTranslated)
//        assertEqualTwoModelsWithoutId(rewardModel, rewardModelTranslated)
//        //with ID
//        val rewardModelWithId = RewardModelTestUtils.generateReward(
//            yearAcquired = 1930,
//            monthAcquired = 2,
//            dayAcquired = 5,
//            yearEscaped = 1953,
//            monthEscaped = 3,
//            dayEscaped = 7
//        )
//        val rewardDTOTranslatedWithId = rewardConverter.convertModelToEntity(rewardModelWithId)
//        val rewardModelTranslatedWithId = rewardConverter.convertEntitytoModel(rewardDTOTranslatedWithId)
//        assertEqualTwoModelsWithoutId(rewardModelWithId, rewardModelTranslatedWithId)
//    }
//
//    private fun assertEqualTwoModelsWithoutId(rewardModel1: RewardModel, rewardModel2: RewardModel){
//        assertEquals(rewardModel1.code, rewardModel2.code)
//        assertEquals(rewardModel1.critterLevel, rewardModel2.critterLevel)
//        assertEquals(rewardModel1.acquisitionDate, rewardModel2.acquisitionDate)
//        assertEquals(rewardModel1.escapingDate, rewardModel2.escapingDate)
//        assertEquals(rewardModel1.isActive, rewardModel2.isActive)
//        assertEquals(rewardModel1.isEscaped, rewardModel2.isEscaped)
//        assertEquals(rewardModel1.name, rewardModel2.name)
//        assertEquals(rewardModel1.legsColor, rewardModel2.legsColor)
//        assertEquals(rewardModel1.bodyColor, rewardModel2.bodyColor)
//        assertEquals(rewardModel1.armsColor, rewardModel2.armsColor)
//    }
//
//    private fun assertEqualTwoDTOsWithoutId(rewardEntity1: RewardEntity, rewardDTOl2: RewardEntity){
//        assertEquals(rewardEntity1.code,  rewardDTOl2.code)
//        assertEquals(rewardEntity1.level,  rewardDTOl2.level)
//        assertEquals(rewardEntity1.acquisitionDate,  rewardDTOl2.acquisitionDate)
//        assertEquals(rewardEntity1.escapingDate,  rewardDTOl2.escapingDate)
//        assertEquals(rewardEntity1.isActive, rewardDTOl2.isActive)
//        assertEquals(rewardEntity1.isEscaped, rewardDTOl2.isEscaped)
//        assertEquals(rewardEntity1.name,  rewardDTOl2.name)
//        assertEquals(rewardEntity1.legsColor,  rewardDTOl2.legsColor)
//        assertEquals(rewardEntity1.bodyColor,  rewardDTOl2.bodyColor)
//        assertEquals(rewardEntity1.armsColor,  rewardDTOl2.armsColor)
//    }











}