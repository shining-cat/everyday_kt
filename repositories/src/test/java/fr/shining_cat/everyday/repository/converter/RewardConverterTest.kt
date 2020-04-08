package fr.shining_cat.everyday.repository.converter

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.CritterHelper
import fr.shining_cat.everyday.models.CritterLevel
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RewardConverterTest : AbstractBaseTest() {

    private lateinit var rewardConverter: RewardConverter

    @Before
    fun setUp() {
        rewardConverter = RewardConverter()
    }

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    ////////////////////////////
    private fun generateRewardEntity(
        desiredLevel: Int = 1,
        active: Boolean = true,
        escaped: Boolean = false,
        desiredId: Long = -1,
        desiredCode: String = "any code",
        yearAcquired: Int = 2000,
        monthAcquired: Int = 5,
        dayAcquired: Int = 13,
        yearEscaped: Int = 2001,
        monthEscaped: Int = 6,
        dayEscaped: Int = 25,
        desiredName: String = "this is my name",
        desiredLegsColor: String = "#FF000000",
        desiredBodyColor: String = "#00FF0000",
        desiredArmsColor: String = "#0000FF00"
    ): RewardEntity {
        if (desiredId == -1L) {
            return RewardEntity(
                code = desiredCode,
                level = desiredLevel,
                acquisitionDate = GregorianCalendar(
                    yearAcquired,
                    monthAcquired,
                    dayAcquired
                ).timeInMillis,
                escapingDate = GregorianCalendar(
                    yearEscaped,
                    monthEscaped,
                    dayEscaped
                ).timeInMillis,
                isActive = active,
                isEscaped = escaped,
                name = desiredName,
                legsColor = desiredLegsColor,
                bodyColor = desiredBodyColor,
                armsColor = desiredArmsColor
            )
        }
        else {
            return RewardEntity(
                id = desiredId,
                code = desiredCode,
                level = desiredLevel,
                acquisitionDate = GregorianCalendar(
                    yearAcquired,
                    monthAcquired,
                    dayAcquired
                ).timeInMillis,
                escapingDate = GregorianCalendar(
                    yearEscaped,
                    monthEscaped,
                    dayEscaped
                ).timeInMillis,
                isActive = active,
                isEscaped = escaped,
                name = desiredName,
                legsColor = desiredLegsColor,
                bodyColor = desiredBodyColor,
                armsColor = desiredArmsColor
            )
        }
    }

    fun generateRewardModel(
        desiredLevel: Int = 1,
        active: Boolean = true,
        escaped: Boolean = false,
        desiredId: Long = -1,
        desiredCode: String = "any code",
        yearAcquired: Int = 2000,
        monthAcquired: Int = 5,
        dayAcquired: Int = 13,
        yearEscaped: Int = 2001,
        monthEscaped: Int = 6,
        dayEscaped: Int = 25,
        desiredName: String = "this is my name",
        desiredLegsColor: String = "#FF000000",
        desiredBodyColor: String = "#00FF0000",
        desiredArmsColor: String = "#0000FF00"
    ): Reward {

        if (desiredId == -1L) {
            return Reward(
                code = desiredCode,
                critterLevel = CritterLevel.fromKey(desiredLevel),
                acquisitionDate = GregorianCalendar(
                    yearAcquired,
                    monthAcquired,
                    dayAcquired
                ).timeInMillis,
                escapingDate = GregorianCalendar(
                    yearEscaped,
                    monthEscaped,
                    dayEscaped
                ).timeInMillis,
                isActive = active,
                isEscaped = escaped,
                name = desiredName,
                legsColor = desiredLegsColor,
                bodyColor = desiredBodyColor,
                armsColor = desiredArmsColor
            )
        }
        else {
            return Reward(
                id = desiredId,
                code = desiredCode,
                critterLevel = CritterLevel.fromKey(desiredLevel),
                acquisitionDate = GregorianCalendar(
                    yearAcquired,
                    monthAcquired,
                    dayAcquired
                ).timeInMillis,
                escapingDate = GregorianCalendar(
                    yearEscaped,
                    monthEscaped,
                    dayEscaped
                ).timeInMillis,
                isActive = active,
                isEscaped = escaped,
                name = desiredName,
                legsColor = desiredLegsColor,
                bodyColor = desiredBodyColor,
                armsColor = desiredArmsColor
            )
        }
    }

    fun generateRewardModels(
        numberOfRewardsDto: Int = 1,
        desiredLevel: Int = 1,
        active: Boolean = true,
        escaped: Boolean = false
    ): List<Reward> {
        val returnList = mutableListOf<Reward>()
        for (i in 0 until numberOfRewardsDto) {
            returnList.add(generateRewardModel(desiredLevel, active, escaped))
        }
        return returnList
    }
    //////////////////////////////////

    @Test
    fun convertModelsToEntities() {
        val rewardModelsList = generateRewardModels(10)
        assertEquals(10, rewardModelsList.size)
        val rewardsTranslatedList = rewardConverter.convertModelsToEntities(rewardModelsList)
        assertEquals(10, rewardsTranslatedList.size)
    }

    @Test
    fun convertModelToEntity() {
        //without ID
        val rewardEntityTranslated = rewardConverter.convertModelToEntity(
            generateRewardModel(
                yearAcquired = 1930,
                monthAcquired = 2,
                dayAcquired = 5,
                yearEscaped = 1953,
                monthEscaped = 3,
                dayEscaped = 7
            )
        )
        val rewardEntityGenerated = generateRewardEntity(
            desiredLevel = rewardEntityTranslated.level,
            active = rewardEntityTranslated.isActive,
            escaped = rewardEntityTranslated.isEscaped,
            desiredId = rewardEntityTranslated.id,
            desiredCode = rewardEntityTranslated.code,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7,
            desiredName = rewardEntityTranslated.name,
            desiredLegsColor = rewardEntityTranslated.legsColor,
            desiredBodyColor = rewardEntityTranslated.bodyColor,
            desiredArmsColor = rewardEntityTranslated.armsColor
        )
        assertEqualTwoDTOsWithoutId(rewardEntityTranslated, rewardEntityGenerated)
        //with ID
        val rewardEntityTranslatedWithId = rewardConverter.convertModelToEntity(
            generateRewardModel(
                desiredId = 25,
                yearAcquired = 1930,
                monthAcquired = 2,
                dayAcquired = 5,
                yearEscaped = 1953,
                monthEscaped = 3,
                dayEscaped = 7
            )
        )
        val rewardEntityGeneratedWithId = generateRewardEntity(
            desiredLevel = rewardEntityTranslatedWithId.level,
            active = rewardEntityTranslatedWithId.isActive,
            escaped = rewardEntityTranslatedWithId.isEscaped,
            desiredId = rewardEntityTranslatedWithId.id,
            desiredCode = rewardEntityTranslatedWithId.code,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7,
            desiredName = rewardEntityTranslatedWithId.name,
            desiredLegsColor = rewardEntityTranslatedWithId.legsColor,
            desiredBodyColor = rewardEntityTranslatedWithId.bodyColor,
            desiredArmsColor = rewardEntityTranslatedWithId.armsColor
        )
        assertEquals(rewardEntityTranslatedWithId, rewardEntityGeneratedWithId)
    }


    @Test
    fun convertDTOtoModel() {
        //only with ID

        val rewardEntity = generateRewardEntity(
            desiredLevel = 2,
            active = true,
            escaped = false,
            desiredId = 25,
            desiredCode = "specific code",
            yearAcquired = 1983,
            monthAcquired = 3,
            dayAcquired = 16,
            yearEscaped = 1990,
            monthEscaped = 12,
            dayEscaped = 1,
            desiredName = "unique name",
            desiredLegsColor = "#FF000000",
            desiredBodyColor = "#00FF0000",
            desiredArmsColor = "#0000FF00"
        )
        assertEquals(2, rewardEntity.level)
        val rewardModel = generateRewardModel(
            desiredLevel = 2,
            active = true,
            escaped = false,
            desiredId = 25,
            desiredCode = "specific code",
            yearAcquired = 1983,
            monthAcquired = 3,
            dayAcquired = 16,
            yearEscaped = 1990,
            monthEscaped = 12,
            dayEscaped = 1,
            desiredName = "unique name",
            desiredLegsColor = "#FF000000",
            desiredBodyColor = "#00FF0000",
            desiredArmsColor = "#0000FF00"
        )
        assertEquals(CritterLevel.fromKey(2), rewardModel.critterLevel)
        val rewardModelTranslated = rewardConverter.convertEntitytoModel(rewardEntity)
        assertEquals(CritterLevel.fromKey(2), rewardModelTranslated.critterLevel)
//        assertEquals(rewardModelTranslated, rewardModel)
    }

    @Test
    fun convertDTOtoModelToDTO() {
        //without ID
        val rewardEntity = generateRewardEntity(
            desiredLevel = 3,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardModelTranslated = rewardConverter.convertEntitytoModel(rewardEntity)
        val rewardEntityTranslated = rewardConverter.convertModelToEntity(rewardModelTranslated)
        assertEqualTwoDTOsWithoutId(rewardEntity, rewardEntityTranslated)
        //with ID
        val rewardEntityWithId = generateRewardEntity(
            desiredLevel = 3,
            desiredId = 25,
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardModelTranslatedWithId = rewardConverter.convertEntitytoModel(rewardEntityWithId)
        val rewardEntityTranslatedWithId =
            rewardConverter.convertModelToEntity(rewardModelTranslatedWithId)
        assertEqualTwoDTOsWithoutId(rewardEntityWithId, rewardEntityTranslatedWithId)
    }

    @Test
    fun convertModelToDTOToModel() {
        //without ID
        val rewardModel = generateRewardModel(
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardEntityTranslated = rewardConverter.convertModelToEntity(rewardModel)
        val rewardModelTranslated = rewardConverter.convertEntitytoModel(rewardEntityTranslated)
        assertEqualTwoModelsWithoutId(rewardModel, rewardModelTranslated)
        //with ID
        val rewardModelWithId = generateRewardModel(
            yearAcquired = 1930,
            monthAcquired = 2,
            dayAcquired = 5,
            yearEscaped = 1953,
            monthEscaped = 3,
            dayEscaped = 7
        )
        val rewardEntityTranslatedWithId = rewardConverter.convertModelToEntity(rewardModelWithId)
        val rewardModelTranslatedWithId =
            rewardConverter.convertEntitytoModel(rewardEntityTranslatedWithId)
        assertEqualTwoModelsWithoutId(rewardModelWithId, rewardModelTranslatedWithId)
    }

    private fun assertEqualTwoModelsWithoutId(rewardModel1: Reward, rewardModel2: Reward) {
        assertEquals(rewardModel1.code, rewardModel2.code)
        assertEquals(rewardModel1.critterLevel, rewardModel2.critterLevel)
        assertEquals(rewardModel1.acquisitionDate, rewardModel2.acquisitionDate)
        assertEquals(rewardModel1.escapingDate, rewardModel2.escapingDate)
        assertEquals(rewardModel1.isActive, rewardModel2.isActive)
        assertEquals(rewardModel1.isEscaped, rewardModel2.isEscaped)
        assertEquals(rewardModel1.name, rewardModel2.name)
        assertEquals(rewardModel1.legsColor, rewardModel2.legsColor)
        assertEquals(rewardModel1.bodyColor, rewardModel2.bodyColor)
        assertEquals(rewardModel1.armsColor, rewardModel2.armsColor)
    }

    private fun assertEqualTwoDTOsWithoutId(
        rewardEntity1: RewardEntity,
        rewardEntityl2: RewardEntity
    ) {
        assertEquals(rewardEntity1.code, rewardEntityl2.code)
        assertEquals(rewardEntity1.level, rewardEntityl2.level)
        assertEquals(rewardEntity1.acquisitionDate, rewardEntityl2.acquisitionDate)
        assertEquals(rewardEntity1.escapingDate, rewardEntityl2.escapingDate)
        assertEquals(rewardEntity1.isActive, rewardEntityl2.isActive)
        assertEquals(rewardEntity1.isEscaped, rewardEntityl2.isEscaped)
        assertEquals(rewardEntity1.name, rewardEntityl2.name)
        assertEquals(rewardEntity1.legsColor, rewardEntityl2.legsColor)
        assertEquals(rewardEntity1.bodyColor, rewardEntityl2.bodyColor)
        assertEquals(rewardEntity1.armsColor, rewardEntityl2.armsColor)
    }


}