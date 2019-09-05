package fr.shining_cat.everyday.model

import org.junit.Assert
import org.junit.Test


class RewardTest {

    @Test
    fun getRewardFlowerCode() {
        val testReward = Reward(1, "1_2_3_4_5_6", Critter.Level.LEVEL_1)
        Assert.assertEquals(1, testReward.getRewardFlowerCode())
    }

    @Test
    fun getRewardLegsCode() {
        val testReward = Reward(1, "1_2_3_4_5_6", Critter.Level.LEVEL_1)
        Assert.assertEquals(2, testReward.getRewardLegsCode())
    }

    @Test
    fun getRewardArmsCode() {
        val testReward = Reward(1, "1_2_3_4_5_6", Critter.Level.LEVEL_1)
        Assert.assertEquals(3, testReward.getRewardArmsCode())
    }

    @Test
    fun getRewardMouthCode() {
        val testReward = Reward(1, "1_2_3_4_5_6", Critter.Level.LEVEL_1)
        Assert.assertEquals(4, testReward.getRewardMouthCode())
    }

    @Test
    fun getRewardEyesCode() {
        val testReward = Reward(1, "1_2_3_4_5_6", Critter.Level.LEVEL_1)
        Assert.assertEquals(5, testReward.getRewardEyesCode())
    }

    @Test
    fun getRewardHornsCode() {
        val testReward = Reward(1, "1_2_3_4_5_6", Critter.Level.LEVEL_1)
        Assert.assertEquals(6, testReward.getRewardHornsCode())
    }
}