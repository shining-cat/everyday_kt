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

// TODO : we need 6 versions of FLOWER and MOUTH + 7 versions of LEGS, ARMS, and EYES (6 normal and 1 "off" version : legs or arms folded, eyes closed), the "off" version of HORNS is simply empty
// parts always "active" : flower, mouth
enum class Level(val key: Int) {

    LEVEL_1(0), // 0 to 5mn    : flower = X | mouth = X | legs = 0 | arms = 0 | eyes = 0 | horns = 0  => 36 combinations
    LEVEL_2(1), // 5 to 15mn   : flower = X | mouth = X | legs = X | arms = 0 | eyes = 0 | horns = 0  => 216 combinations
    LEVEL_3(2), // 15 to 30mn  : flower = X | mouth = X | legs = X | arms = X | eyes = 0 | horns = 0  => 1296 combinations
    LEVEL_4(3), // 30 to 60mn  : flower = X | mouth = X | legs = X | arms = X | eyes = X | horns = 0  => 7776 combinations
    LEVEL_5(4); // 60mn and + : flower = X | mouth = X | legs = X | arms = X | eyes = X | horns = X  => 46656 combinations
    // total combinations => 55980

    companion object {

        fun fromKey(key: Int?): Level {
            return when (key) {
                0 -> LEVEL_1
                1 -> LEVEL_2
                2 -> LEVEL_3
                3 -> LEVEL_4
                4 -> LEVEL_5
                else -> LEVEL_1
            }
        }
    }
}