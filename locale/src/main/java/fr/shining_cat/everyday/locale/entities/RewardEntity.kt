/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.locale.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ACTIVE_STATE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ARMS
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ARMS_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.BODY_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.CUSTOM_NAME
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.DATE_ACQUISITION
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.DATE_ESCAPING
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ESCAPED_STATE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.EYES
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.FLOWER
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.HORNS
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.LEGS
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.LEGS_COLOR
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.LEVEL
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.MOUTH
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.REWARD_ID
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
    @ColumnInfo(name = FLOWER)
    val flower: Int,
    @ColumnInfo(name = MOUTH)
    val mouth: Int,
    @ColumnInfo(name = LEGS)
    val legs: Int,
    @ColumnInfo(name = ARMS)
    val arms: Int,
    @ColumnInfo(name = EYES)
    val eyes: Int,
    @ColumnInfo(name = HORNS)
    val horns: Int,
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

    const val NO_ACQUISITION_DATE: Long = 0L
    const val NO_ESCAPING_DATE: Long = 0L
    const val NO_NAME = ""
    const val DEFAULT_REWARD_COLOR = "#00000000"
}

object RewardTable {

    const val REWARD_TABLE = "rewards_table"
}

object RewardEntityColumnNames {

    const val REWARD_ID = "id"
    const val FLOWER = "flower"
    const val MOUTH = "mouth"
    const val LEGS = "legs"
    const val ARMS = "arms"
    const val EYES = "eyes"
    const val HORNS = "horns"
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