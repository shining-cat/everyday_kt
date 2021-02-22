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

package fr.shining_cat.everyday.models.sessionrecord

data class Mood(
    val timeOfRecord: Long,
    //values range from 1 (WORST) to 5 (BEST), 0 is for NOT SET
    val bodyValue: MoodValue,
    val thoughtsValue: MoodValue,
    val feelingsValue: MoodValue,
    val globalValue: MoodValue
) {
    override fun toString() = "MOOD : timeStamp = " + timeOfRecord +
            "\n\tBody = " + bodyValue +
            "\n\tThoughts = " + thoughtsValue +
            "\n\tFeelings = " + feelingsValue +
            "\n\tGlobal = " + globalValue
}

enum class MoodValue(val key: Int) {
    WORST(-2),
    BAD(-1),
    NOT_SET(0),
    GOOD(1),
    BEST(2);

    companion object {
        fun fromKey(key: Int?): MoodValue {
            return when (key) {
                -2 -> WORST
                -1 -> BAD
                1 -> GOOD
                2 -> BEST
                else -> NOT_SET
            }
        }
    }
}