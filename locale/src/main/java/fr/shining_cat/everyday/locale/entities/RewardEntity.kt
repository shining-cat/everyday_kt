package fr.shining_cat.everyday.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ACTIVE_STATE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.DATE_ACQUISITION
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.DATE_ESCAPING
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ESCAPED_STATE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.CODE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ARMS_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.BODY_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.LEGS_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.CUSTOM_NAME
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.REWARD_ID
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.LEVEL
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants.DEFAULT_REWARD_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants.NO_ACQUISITION_DATE
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants.NO_ESCAPING_DATE
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants.NO_NAME
import fr.shining_cat.everyday.locale.entities.RewardTable.REWARD_TABLE


@Entity(tableName = REWARD_TABLE)
data class RewardEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = REWARD_ID)
    var id: Long = 0L,
    //
    @ColumnInfo(name = CODE)
    val code: String,
    @ColumnInfo(name = LEVEL)
    val level: Int,
    @ColumnInfo(name = DATE_ACQUISITION)
    var acquisitionDate: Long = NO_ACQUISITION_DATE,
    @ColumnInfo(name = DATE_ESCAPING)
    var escapingDate: Long = NO_ESCAPING_DATE,
    @ColumnInfo(name = ACTIVE_STATE)
    var isActive: Boolean = false,
    @ColumnInfo(name = ESCAPED_STATE)
    var isEscaped: Boolean = true,
    @ColumnInfo(name = CUSTOM_NAME)
    var name: String = NO_NAME,
    @ColumnInfo(name = LEGS_COLOR)
    var legsColor: String = DEFAULT_REWARD_COLOR,
    @ColumnInfo(name = BODY_COLOR)
    var bodyColor: String = DEFAULT_REWARD_COLOR,
    @ColumnInfo(name = ARMS_COLOR)
    var armsColor: String = DEFAULT_REWARD_COLOR
)


object RewardEntityConstants {
    const val NO_ACQUISITION_DATE: Long = 0
    const val NO_ESCAPING_DATE: Long = 0
    const val NO_NAME = ""
    const val DEFAULT_REWARD_COLOR = "#00000000"
}

object RewardTable {
    const val REWARD_TABLE = "rewards_table"
}

object RewardEntityColumnNames {
    const val REWARD_ID = "id"
    const val CODE = "code"
    const val LEVEL = "level"
    const val DATE_ACQUISITION = "acquisitionDate"
    const val DATE_ESCAPING = "escapingDate"
    const val ACTIVE_STATE = "isActive"
    const val ESCAPED_STATE = "isEscaped"
    const val CUSTOM_NAME = "name"
    const val LEGS_COLOR = "legsColor"
    const val BODY_COLOR = "bodyColor"
    const val ARMS_COLOR = "armsColor"
}