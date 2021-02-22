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

package fr.shining_cat.everyday.models.reward

import fr.shining_cat.everyday.models.reward.RewardConstants.DEFAULT_REWARD_COLOR
import fr.shining_cat.everyday.models.reward.RewardConstants.NO_ACQUISITION_DATE
import fr.shining_cat.everyday.models.reward.RewardConstants.NO_ESCAPING_DATE
import fr.shining_cat.everyday.models.reward.RewardConstants.NO_NAME


////////////////////////////////////////
//ROOM entity for rewards storage

//upon creation, acquisition_date is set to 0, escape_date is set to 0, isActive is set to false, and isEscaped is set to false;
//On the first time the reward is obtained, isActive is set to true, and never re-set to false again.
//when a reward is obtained, then acquisition_date is set to the moment it happens; it will be modified only if reward is re-obtained,
//if a reward escapes, then isEscaped is set to true, and escape_date is set to the moment it happens, if it is obtained again, then isEscaped is re-set to false

data class Reward(
    var id: Long = -1,
    val flowerKey: Int,
    val mouthKey: Int,
    val legsKey: Int,
    val armsKey: Int,
    val eyesKey: Int,
    val hornsKey: Int,
    val level: Level,
    var acquisitionDate: Long = NO_ACQUISITION_DATE,
    var escapingDate: Long = NO_ESCAPING_DATE,
    var isActive: Boolean = false,
    var isEscaped: Boolean = true,
    var name: String = NO_NAME,
    var legsColor: String = DEFAULT_REWARD_COLOR,
    var bodyColor: String = DEFAULT_REWARD_COLOR,
    var armsColor: String = DEFAULT_REWARD_COLOR
)


object RewardConstants {
    const val NO_ACQUISITION_DATE: Long = 0
    const val NO_ESCAPING_DATE: Long = 0
    const val NO_NAME = ""
    const val DEFAULT_REWARD_COLOR = "#00000000"
}

//TODO: this should move to the Domain module when it is created:
object RewardThresholds {
    //length of session for each duration level => this determines the Reward level
    const val REWARD_DURATION_LEVEL_1 = 0
    const val REWARD_DURATION_LEVEL_2 = 300000 //5mn
    const val REWARD_DURATION_LEVEL_3 = 900000 //15mn
    const val REWARD_DURATION_LEVEL_4 = 1800000 //30mn
    const val REWARD_DURATION_LEVEL_5 = 3600000 //60mn

    //length of consecutive practice days for each streak level => this plays a role in determining how many rewards the user will get (see RewardChances)
    const val REWARD_STREAK_LEVEL_1 = 1
    const val REWARD_STREAK_LEVEL_2 = 7
    const val REWARD_STREAK_LEVEL_3 = 14
    const val REWARD_STREAK_LEVEL_4 = 21
    const val REWARD_STREAK_LEVEL_5 = 28
}

//TODO: this should move to the Domain module when it is created:
object RewardChances {
    //composition of a session reward as an array of probability of getting additional rewards (of the same level) (chance for 1, chance for a second, chance for a third, chance for a fourth)
    //so a user can earn up to 4 rewards for one session
    //this is based on the REWARD_STREAK_LEVEL
    val REWARD_CHANCE_LEVEL_1 = arrayOf(100, 0, 0, 0)
    val REWARD_CHANCE_LEVEL_2 = arrayOf(100, 20, 0, 0)
    val REWARD_CHANCE_LEVEL_3 = arrayOf(100, 30, 10, 0)
    val REWARD_CHANCE_LEVEL_4 = arrayOf(100, 40, 15, 5)
    val REWARD_CHANCE_LEVEL_5 = arrayOf(100, 50, 20, 10)
}

