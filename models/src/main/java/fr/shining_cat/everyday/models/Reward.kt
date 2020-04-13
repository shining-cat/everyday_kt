package fr.shining_cat.everyday.models

import fr.shining_cat.everyday.models.CritterConstants.ARMS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.models.CritterConstants.CRITTER_CODE_SEPARATOR
import fr.shining_cat.everyday.models.CritterConstants.EYES_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.models.CritterConstants.FLOWERS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.models.CritterConstants.HORNS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.models.CritterConstants.LEGS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.models.CritterConstants.MOUTH_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.models.RewardModelConstants.DEFAULT_REWARD_COLOR
import fr.shining_cat.everyday.models.RewardModelConstants.NO_ACQUISITION_DATE
import fr.shining_cat.everyday.models.RewardModelConstants.NO_ESCAPING_DATE
import fr.shining_cat.everyday.models.RewardModelConstants.NO_NAME


////////////////////////////////////////
//ROOM entity for rewards storage
//with some convenience getters : get individual body-parts codes

//reward_code is a String built this way :
//      [flower-part-code]_[mouth-part-code]_[legs-part-code]-[arms-part-code]_[eyes-part-code]_[horns-part-code]
//      each code is a 1-digit number from 0 to 6 (for now) linked to the index of the corresponding png resource
//      example : 1_3_5_0_2_2

//upon creation, acquisition_date is set to 0, escape_date is set to 0, isActive is set to false, and isEscaped is set to false;
//On the first time the reward is obtained, isActive is set to true, and never re-set to false again.
//when a reward is obtained, then acquisition_date is set to the moment it happens; it will be modified only if reward is re-obtained,
//if a reward escapes, then isEscaped is set to true, and escape_date is set to the moment it happens, if it is obtained again, then isEscaped is re-set to false

data class Reward(
    var id: Long = -1,
    val code: String,
    val critterLevel: CritterLevel,
    var acquisitionDate: Long = NO_ACQUISITION_DATE,
    var escapingDate: Long = NO_ESCAPING_DATE,
    var isActive: Boolean = false,
    var isEscaped: Boolean = true,
    var name: String = NO_NAME,
    var legsColor: String = DEFAULT_REWARD_COLOR,
    var bodyColor: String = DEFAULT_REWARD_COLOR,
    var armsColor: String = DEFAULT_REWARD_COLOR)
{

    private val splitRewardCode = code.split(CRITTER_CODE_SEPARATOR)

    ////////////////////////////////////////
    //convenience getters
    fun getFlowerCode() = Integer.valueOf(splitRewardCode[FLOWERS_CODE_INDEX_IN_CRITTER_CODE])
    fun getLegsCode() = Integer.valueOf(splitRewardCode[LEGS_CODE_INDEX_IN_CRITTER_CODE])
    fun getArmsCode() = Integer.valueOf(splitRewardCode[ARMS_CODE_INDEX_IN_CRITTER_CODE])
    fun getMouthCode() = Integer.valueOf(splitRewardCode[MOUTH_CODE_INDEX_IN_CRITTER_CODE])
    fun getEyesCode() = Integer.valueOf(splitRewardCode[EYES_CODE_INDEX_IN_CRITTER_CODE])
    fun getHornsCode() = Integer.valueOf(splitRewardCode[HORNS_CODE_INDEX_IN_CRITTER_CODE])


}

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

