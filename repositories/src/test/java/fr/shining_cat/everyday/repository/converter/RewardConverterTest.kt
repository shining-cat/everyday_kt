package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.Level
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.models.critter.*
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
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
//    private fun generateRewardEntity(
//        desiredFlower: Int = 1,
//        desiredMouth: Int = 2,
//        desiredLegs: Int = 3,
//        desiredArms: Int = 4,
//        desiredEyes: Int = 5,
//        desiredHorns: Int = 6,
//        desiredLevel: Int = 1,
//        active: Boolean = true,
//        escaped: Boolean = false,
//        desiredId: Long = -1,
//        yearAcquired: Int = 2000,
//        monthAcquired: Int = 5,
//        dayAcquired: Int = 13,
//        yearEscaped: Int = 2001,
//        monthEscaped: Int = 6,
//        dayEscaped: Int = 25,
//        desiredName: String = "this is my name",
//        desiredLegsColor: String = "#FF000000",
//        desiredBodyColor: String = "#00FF0000",
//        desiredArmsColor: String = "#0000FF00"
//    ): RewardEntity {
//        if (desiredId == -1L) {
//            return RewardEntity(
//                flower = desiredFlower,
//                mouth = desiredMouth,
//                legs = desiredLegs,
//                arms = desiredArms,
//                eyes = desiredEyes,
//                horns = desiredHorns,
//                level = desiredLevel,
//                acquisitionDate = GregorianCalendar(
//                    yearAcquired,
//                    monthAcquired,
//                    dayAcquired
//                ).timeInMillis,
//                escapingDate = GregorianCalendar(
//                    yearEscaped,
//                    monthEscaped,
//                    dayEscaped
//                ).timeInMillis,
//                isActive = active,
//                isEscaped = escaped,
//                name = desiredName,
//                legsColor = desiredLegsColor,
//                bodyColor = desiredBodyColor,
//                armsColor = desiredArmsColor
//            )
//        }
//        else {
//            return RewardEntity(
//                id = desiredId,
//                flower = desiredFlower,
//                mouth = desiredMouth,
//                legs = desiredLegs,
//                arms = desiredArms,
//                eyes = desiredEyes,
//                horns = desiredHorns,
//                level = desiredLevel,
//                acquisitionDate = GregorianCalendar(
//                    yearAcquired,
//                    monthAcquired,
//                    dayAcquired
//                ).timeInMillis,
//                escapingDate = GregorianCalendar(
//                    yearEscaped,
//                    monthEscaped,
//                    dayEscaped
//                ).timeInMillis,
//                isActive = active,
//                isEscaped = escaped,
//                name = desiredName,
//                legsColor = desiredLegsColor,
//                bodyColor = desiredBodyColor,
//                armsColor = desiredArmsColor
//            )
//        }
//    }
//
//    fun generateRewardModel(
//        desiredFlower: Int = 1,
//        desiredMouth: Int = 2,
//        desiredLegs: Int = 3,
//        desiredArms: Int = 4,
//        desiredEyes: Int = 5,
//        desiredHorns: Int = 6,
//        desiredLevel: Int = 1,
//        active: Boolean = true,
//        escaped: Boolean = false,
//        desiredId: Long = -1,
//        yearAcquired: Int = 2000,
//        monthAcquired: Int = 5,
//        dayAcquired: Int = 13,
//        yearEscaped: Int = 2001,
//        monthEscaped: Int = 6,
//        dayEscaped: Int = 25,
//        desiredName: String = "this is my name",
//        desiredLegsColor: String = "#FF000000",
//        desiredBodyColor: String = "#00FF0000",
//        desiredArmsColor: String = "#0000FF00"
//    ): Reward {
//        if (desiredId == -1L) {
//            return Reward(
//                flower = FlowerResourcesHolder.FlowerDrawable.fromKey(desiredFlower),
//                mouth = MouthResourcesHolder.MouthDrawable.fromKey(desiredMouth),
//                legs = LegsResourcesHolder.LegsDrawable.fromKey(desiredLegs),
//                arms = ArmsResourcesHolder.ArmsDrawable.fromKey(desiredArms),
//                eyes = EyesResourcesHolder.EyesDrawable.fromKey(desiredEyes),
//                horns = HornsResourcesHolder.HornsDrawable.fromKey(desiredHorns),
//                level = Level.fromKey(desiredLevel),
//                acquisitionDate = GregorianCalendar(
//                    yearAcquired,
//                    monthAcquired,
//                    dayAcquired
//                ).timeInMillis,
//                escapingDate = GregorianCalendar(
//                    yearEscaped,
//                    monthEscaped,
//                    dayEscaped
//                ).timeInMillis,
//                isActive = active,
//                isEscaped = escaped,
//                name = desiredName,
//                legsColor = desiredLegsColor,
//                bodyColor = desiredBodyColor,
//                armsColor = desiredArmsColor
//            )
//        }
//        else {
//            return Reward(
//                id = desiredId,
//                flower = FlowerResourcesHolder.FlowerDrawable.fromKey(desiredFlower),
//                mouth = MouthResourcesHolder.MouthDrawable.fromKey(desiredMouth),
//                legs = LegsResourcesHolder.LegsDrawable.fromKey(desiredLegs),
//                arms = ArmsResourcesHolder.ArmsDrawable.fromKey(desiredArms),
//                eyes = EyesResourcesHolder.EyesDrawable.fromKey(desiredEyes),
//                horns = HornsResourcesHolder.HornsDrawable.fromKey(desiredHorns),
//                level = Level.fromKey(desiredLevel),
//                acquisitionDate = GregorianCalendar(
//                    yearAcquired,
//                    monthAcquired,
//                    dayAcquired
//                ).timeInMillis,
//                escapingDate = GregorianCalendar(
//                    yearEscaped,
//                    monthEscaped,
//                    dayEscaped
//                ).timeInMillis,
//                isActive = active,
//                isEscaped = escaped,
//                name = desiredName,
//                legsColor = desiredLegsColor,
//                bodyColor = desiredBodyColor,
//                armsColor = desiredArmsColor
//            )
//        }
//    }
//
//    fun generateRewardModels(
//        numberOfRewardsDto: Int = 1,
//        desiredLevel: Int = 1,
//        active: Boolean = true,
//        escaped: Boolean = false
//    ): List<Reward> {
//        val returnList = mutableListOf<Reward>()
//        for (i in 0 until numberOfRewardsDto) {
//            returnList.add(generateRewardModel(
//                desiredLevel = desiredLevel,
//                active = active,
//                escaped = escaped))
//        }
//        return returnList
//    }
    ///////////////////////////////

    val reward = Reward(
        id=29,
        flower = FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_1,
        mouth = MouthResourcesHolder.MouthDrawable.MOUTH_PART_2,
        legs = LegsResourcesHolder.LegsDrawable.LEGS_PART_3,
        arms = ArmsResourcesHolder.ArmsDrawable.ARMS_PART_4,
        eyes = EyesResourcesHolder.EyesDrawable.EYES_PART_5,
        horns = HornsResourcesHolder.HornsDrawable.HORNS_PART_6,
        level = Level.fromKey(3),
        acquisitionDate = GregorianCalendar(
            2000,
            5,
            13
        ).timeInMillis,
        escapingDate = GregorianCalendar(
            2001,
            6,
            25
        ).timeInMillis,
        isActive = true,
        isEscaped = false,
        name = "this is my name",
        legsColor = "#FF000000",
        bodyColor = "#00FF0000",
        armsColor = "#0000FF00"
    )

    val rewardEntity = RewardEntity(
        id=29,
        flower = 1,
        mouth = 2,
        legs = 3,
        arms = 4,
        eyes = 5,
        horns = 6,
        level = 3,
        acquisitionDate = GregorianCalendar(
            2000,
            5,
            13
        ).timeInMillis,
        escapingDate = GregorianCalendar(
            2001,
            6,
            25
        ).timeInMillis,
        isActive = true,
        isEscaped = false,
        name = "this is my name",
        legsColor = "#FF000000",
        bodyColor = "#00FF0000",
        armsColor = "#0000FF00"
    )

    //////////////////////////////////

    @Test
    fun convertModelToEntity() {
        val convertedModel = rewardConverter.convertModelToEntity(reward)
        assertEquals(rewardEntity, convertedModel)
    }


    @Test
    fun convertEntitytoModel() {
        val convertedEntity = rewardConverter.convertEntitytoModel(rewardEntity)
        assertEquals(reward, convertedEntity)
    }

}