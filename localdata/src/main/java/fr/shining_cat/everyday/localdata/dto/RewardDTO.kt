package fr.shining_cat.everyday.localdata.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.localdata.dto.RewardDTOConstants.DEFAULT_REWARD_COLOR
import fr.shining_cat.everyday.localdata.dto.RewardDTOConstants.NO_ACQUISITION_DATE
import fr.shining_cat.everyday.localdata.dto.RewardDTOConstants.NO_ESCAPING_DATE
import fr.shining_cat.everyday.localdata.dto.RewardDTOConstants.NO_NAME


@Entity(tableName = "rewards_table")
data class RewardDTO(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    //
    @ColumnInfo(name = RewardDTOColumnNames.REWARD_CODE_COLUMN_NAME)
    val code: String,
    @ColumnInfo(name = RewardDTOColumnNames.REWARD_LEVEL_COLUMN_NAME)
    val level: Int,
    @ColumnInfo(name = RewardDTOColumnNames.DATE_ACQUISITION_COLUMN_NAME)
    var acquisitionDate: Long = NO_ACQUISITION_DATE,
    @ColumnInfo(name = RewardDTOColumnNames.DATE_ESCAPING_COLUMN_NAME)
    var escapingDate: Long = NO_ESCAPING_DATE,
    @ColumnInfo(name = RewardDTOColumnNames.ACTIVE_STATE_COLUMN_NAME)
    var isActive: Boolean = false,
    @ColumnInfo(name = RewardDTOColumnNames.ESCAPED_STATE_COLUMN_NAME)
    var isEscaped: Boolean = true,
    @ColumnInfo(name = RewardDTOColumnNames.REWARD_CUSTOM_NAME_COLUMN_NAME)
    var name: String = NO_NAME,
    @ColumnInfo(name = RewardDTOColumnNames.REWARD_CUSTOM_LEGS_COLOR_COLUMN_NAME)
    var legsColor: String = DEFAULT_REWARD_COLOR,
    @ColumnInfo(name = RewardDTOColumnNames.REWARD_CUSTOM_BODY_COLOR_COLUMN_NAME)
    var bodyColor: String = DEFAULT_REWARD_COLOR,
    @ColumnInfo(name = RewardDTOColumnNames.REWARD_CUSTOM_ARMS_COLOR_COLUMN_NAME)
    var armsColor: String = DEFAULT_REWARD_COLOR)


object RewardDTOConstants{
    const val NO_ACQUISITION_DATE: Long = 0
    const val NO_ESCAPING_DATE: Long = 0
    const val NO_NAME = ""
    const val DEFAULT_REWARD_COLOR = "#00000000"
}

object RewardDTOColumnNames{
    const val REWARD_CODE_COLUMN_NAME              = "code"
    const val REWARD_LEVEL_COLUMN_NAME             = "level"
    const val DATE_ACQUISITION_COLUMN_NAME         = "acquisitionDate"
    const val DATE_ESCAPING_COLUMN_NAME            = "escapingDate"
    const val ACTIVE_STATE_COLUMN_NAME             = "isActive"
    const val ESCAPED_STATE_COLUMN_NAME            = "isEscaped"
    const val REWARD_CUSTOM_NAME_COLUMN_NAME       = "name"
    const val REWARD_CUSTOM_LEGS_COLOR_COLUMN_NAME = "legsColor"
    const val REWARD_CUSTOM_BODY_COLOR_COLUMN_NAME = "bodyColor"
    const val REWARD_CUSTOM_ARMS_COLOR_COLUMN_NAME = "armsColor"
}