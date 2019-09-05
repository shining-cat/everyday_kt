package fr.shining_cat.everyday.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.model.Critter.Companion.ARMS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.model.Critter.Companion.CRITTER_CODE_SEPARATOR
import fr.shining_cat.everyday.model.Critter.Companion.EYES_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.model.Critter.Companion.FLOWERS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.model.Critter.Companion.HORNS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.model.Critter.Companion.LEGS_CODE_INDEX_IN_CRITTER_CODE
import fr.shining_cat.everyday.model.Critter.Companion.MOUTH_CODE_INDEX_IN_CRITTER_CODE

import fr.shining_cat.everyday.model.RewardConstants.DEFAULT_REWARD_COLOR
import fr.shining_cat.everyday.model.RewardConstants.NO_ACQUISITION_DATE
import fr.shining_cat.everyday.model.RewardConstants.NO_ESCAPING_DATE
import fr.shining_cat.everyday.model.RewardConstants.NO_NAME


////////////////////////////////////////
//ROOM entity for rewards storage
//with some convenience getters : get individual body-parts codes

//reward_code is a String built this way :
//      [flower-part-code]_[legs-part-code]-[arms-part-code]_[mouth-part-code]_[eyes-part-code]_[horns-part-code]
//      each code is a 1-digit number from 0 to 6 (for now) linked to the index of the corresponding png resource
//      example : 1_3_5_0_2_2

//upon creation, acquisition_date is set to 0, escape_date is set to 0, isActive is set to false, and isEscaped is set to false;
//On the first time the reward is obtained, isActive is set to true, and never re-set to false again.
//when a reward is obtained, then acquisition_date is set to the moment it happens; it will be modified only if reward is re-obtained,
//if a reward escapes, then isEscaped is set to true, and escape_date is set to the moment it happens, if it is obtained again, then isEscaped is re-set to false

@Entity(tableName = "rewards_table")
data class Reward(
    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0,
    //
    @ColumnInfo(name = RewardColumnNames.REWARD_CODE_COLUMN_NAME)
    private val rewardCode: String,
    @ColumnInfo(name = RewardColumnNames.REWARD_LEVEL_COLUMN_NAME)
    private val rewardLevel: Critter.Level,
    @ColumnInfo(name = RewardColumnNames.DATE_ACQUISITION_COLUMN_NAME)
    private var acquisitionDate: Long = NO_ACQUISITION_DATE,
    @ColumnInfo(name = RewardColumnNames.DATE_ESCAPING_COLUMN_NAME)
    private var escapingDate: Long = NO_ESCAPING_DATE,
    @ColumnInfo(name = RewardColumnNames.ACTIVE_STATE_COLUMN_NAME)
    private var isActive: Boolean = false,
    @ColumnInfo(name = RewardColumnNames.ESCAPED_STATE_COLUMN_NAME)
    private var isEscaped: Boolean = true,
    @ColumnInfo(name = RewardColumnNames.REWARD_CUSTOM_NAME_COLUMN_NAME)
    private var rewardName: String = NO_NAME,
    @ColumnInfo(name = RewardColumnNames.REWARD_CUSTOM_LEGS_COLOR_COLUMN_NAME)
    private var rewardLegsColor: String = DEFAULT_REWARD_COLOR,
    @ColumnInfo(name = RewardColumnNames.REWARD_CUSTOM_BODY_COLOR_COLUMN_NAME)
    private var rewardBodyColor: String = DEFAULT_REWARD_COLOR,
    @ColumnInfo(name = RewardColumnNames.REWARD_CUSTOM_ARMS_COLOR_COLUMN_NAME)
    private var rewardArmsColor: String = DEFAULT_REWARD_COLOR)
{

    private val splitRewardCode = rewardCode.split(CRITTER_CODE_SEPARATOR)

    ////////////////////////////////////////
    //convenience getters
    fun getRewardFlowerCode() = Integer.valueOf(splitRewardCode[FLOWERS_CODE_INDEX_IN_CRITTER_CODE])

    fun getRewardLegsCode() = Integer.valueOf(splitRewardCode[LEGS_CODE_INDEX_IN_CRITTER_CODE])

    fun getRewardArmsCode() = Integer.valueOf(splitRewardCode[ARMS_CODE_INDEX_IN_CRITTER_CODE])

    fun getRewardMouthCode() = Integer.valueOf(splitRewardCode[MOUTH_CODE_INDEX_IN_CRITTER_CODE])

    fun getRewardEyesCode() = Integer.valueOf(splitRewardCode[EYES_CODE_INDEX_IN_CRITTER_CODE])

    fun getRewardHornsCode() = Integer.valueOf(splitRewardCode[HORNS_CODE_INDEX_IN_CRITTER_CODE])

}

object RewardChances{
    val REWARD_CHANCE_LEVEL_1 = arrayOf(100,  0,  0,  0)
    val REWARD_CHANCE_LEVEL_2 = arrayOf(100, 20,  0,  0)
    val REWARD_CHANCE_LEVEL_3 = arrayOf(100, 30, 10,  0)
    val REWARD_CHANCE_LEVEL_4 = arrayOf(100, 40, 15,  5)
    val REWARD_CHANCE_LEVEL_5 = arrayOf(100, 50, 20, 10)
}

object RewardThresholds{
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

object RewardColumnNames{
    const val REWARD_CODE_COLUMN_NAME              = "rewardCode"
    const val REWARD_LEVEL_COLUMN_NAME             = "rewardLevel"
    const val DATE_ACQUISITION_COLUMN_NAME         = "acquisitionDate"
    const val DATE_ESCAPING_COLUMN_NAME            = "escapingDate"
    const val ACTIVE_STATE_COLUMN_NAME             = "isActive"
    const val ESCAPED_STATE_COLUMN_NAME            = "isEscaped"
    const val REWARD_CUSTOM_NAME_COLUMN_NAME       = "rewardName"
    const val REWARD_CUSTOM_LEGS_COLOR_COLUMN_NAME = "rewardLegsColor"
    const val REWARD_CUSTOM_BODY_COLOR_COLUMN_NAME = "rewardBodyColor"
    const val REWARD_CUSTOM_ARMS_COLOR_COLUMN_NAME = "rewardArmsColor"
}

object RewardConstants{
    const val NO_ACQUISITION_DATE: Long = 0
    const val NO_ESCAPING_DATE: Long = 0
    const val NO_NAME = ""
    const val DEFAULT_REWARD_COLOR = "#00000000"
}