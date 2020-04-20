package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.Level
import fr.shining_cat.everyday.models.Reward
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

    val reward = Reward(
        id = 29,
        flowerKey = 0,
        mouthKey = 1,
        legsKey = 2,
        armsKey = 3,
        eyesKey = 4,
        hornsKey = 5,
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
        id = 29,
        flower = 0,
        mouth = 1,
        legs = 2,
        arms = 3,
        eyes = 4,
        horns = 5,
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