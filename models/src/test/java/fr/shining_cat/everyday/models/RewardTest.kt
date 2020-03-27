package fr.shining_cat.everyday.models

import org.junit.Assert
import org.junit.Test


class RewardTest {

    @Test
    fun getRewardFlowerCode() {
        val testReward = RewardModel(1, "1_2_3_4_5_6", CritterLevel.LEVEL_1)
        Assert.assertEquals(1, testReward.getFlowerCode())
    }

    @Test
    fun getRewardLegsCode() {
        val testReward = RewardModel(1, "1_2_3_4_5_6", CritterLevel.LEVEL_1)
        Assert.assertEquals(2, testReward.getLegsCode())
    }

    @Test
    fun getRewardArmsCode() {
        val testReward = RewardModel(1, "1_2_3_4_5_6", CritterLevel.LEVEL_1)
        Assert.assertEquals(3, testReward.getArmsCode())
    }

    @Test
    fun getRewardMouthCode() {
        val testReward = RewardModel(1, "1_2_3_4_5_6", CritterLevel.LEVEL_1)
        Assert.assertEquals(4, testReward.getMouthCode())
    }

    @Test
    fun getRewardEyesCode() {
        val testReward = RewardModel(1, "1_2_3_4_5_6", CritterLevel.LEVEL_1)
        Assert.assertEquals(5, testReward.getEyesCode())
    }

    @Test
    fun getRewardHornsCode() {
        val testReward = RewardModel(1, "1_2_3_4_5_6", CritterLevel.LEVEL_1)
        Assert.assertEquals(6, testReward.getHornsCode())
    }
}