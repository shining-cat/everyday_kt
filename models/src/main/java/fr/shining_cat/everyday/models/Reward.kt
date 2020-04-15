package fr.shining_cat.everyday.models

import fr.shining_cat.everyday.models.RewardModelConstants.DEFAULT_REWARD_COLOR
import fr.shining_cat.everyday.models.RewardModelConstants.NO_ACQUISITION_DATE
import fr.shining_cat.everyday.models.RewardModelConstants.NO_ESCAPING_DATE
import fr.shining_cat.everyday.models.RewardModelConstants.NO_NAME
import fr.shining_cat.everyday.models.critter.*


////////////////////////////////////////
//ROOM entity for rewards storage

//upon creation, acquisition_date is set to 0, escape_date is set to 0, isActive is set to false, and isEscaped is set to false;
//On the first time the reward is obtained, isActive is set to true, and never re-set to false again.
//when a reward is obtained, then acquisition_date is set to the moment it happens; it will be modified only if reward is re-obtained,
//if a reward escapes, then isEscaped is set to true, and escape_date is set to the moment it happens, if it is obtained again, then isEscaped is re-set to false

data class Reward(
    var id: Long = -1,
    val flower: FlowerResourcesHolder.FlowerDrawable,
    val mouth: MouthResourcesHolder.MouthDrawable,
    val legs: LegsResourcesHolder.LegsDrawable,
    val arms: ArmsResourcesHolder.ArmsDrawable,
    val eyes: EyesResourcesHolder.EyesDrawable,
    val horns: HornsResourcesHolder.HornsDrawable,
    val level: Level,
    var acquisitionDate: Long = NO_ACQUISITION_DATE,
    var escapingDate: Long = NO_ESCAPING_DATE,
    var isActive: Boolean = false,
    var isEscaped: Boolean = true,
    var name: String = NO_NAME,
    var legsColor: String = DEFAULT_REWARD_COLOR,
    var bodyColor: String = DEFAULT_REWARD_COLOR,
    var armsColor: String = DEFAULT_REWARD_COLOR)


object RewardModelConstants{
    const val NO_ACQUISITION_DATE: Long = 0
    const val NO_ESCAPING_DATE: Long = 0
    const val NO_NAME = ""
    const val DEFAULT_REWARD_COLOR = "#00000000"
}

object RewardModelChances{
    val REWARD_CHANCE_LEVEL_1 = arrayOf(100,  0,  0,  0)
    val REWARD_CHANCE_LEVEL_2 = arrayOf(100, 20,  0,  0)
    val REWARD_CHANCE_LEVEL_3 = arrayOf(100, 30, 10,  0)
    val REWARD_CHANCE_LEVEL_4 = arrayOf(100, 40, 15,  5)
    val REWARD_CHANCE_LEVEL_5 = arrayOf(100, 50, 20, 10)
}

object RewardModelThresholds{
    const val REWARD_DURATION_LEVEL_1 = 0
    const val REWARD_DURATION_LEVEL_2 = 300000 //5mn
    const val REWARD_DURATION_LEVEL_3 = 900000 //15mn
    const val REWARD_DURATION_LEVEL_4 = 1800000 //30mn
    const val REWARD_DURATION_LEVEL_5 = 3600000 //60mn

    const val REWARD_STREAK_LEVEL_1 = 1
    const val REWARD_STREAK_LEVEL_2 = 7
    const val REWARD_STREAK_LEVEL_3 = 14
    const val REWARD_STREAK_LEVEL_4 = 21
    const val REWARD_STREAK_LEVEL_5 = 28
}

