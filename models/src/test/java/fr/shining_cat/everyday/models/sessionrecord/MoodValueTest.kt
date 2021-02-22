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

import fr.shining_cat.everyday.models.sessionrecord.MoodValue.Companion.fromKey
import org.junit.Assert.assertEquals
import org.junit.Test

class MoodValueTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            MoodValue.WORST,
            fromKey(
                MoodValue.WORST.key
            )
        )
        assertEquals(
            MoodValue.BAD,
            fromKey(
                MoodValue.BAD.key
            )
        )
        assertEquals(
            MoodValue.NOT_SET,
            fromKey(
                MoodValue.NOT_SET.key
            )
        )
        assertEquals(
            MoodValue.GOOD,
            fromKey(
                MoodValue.GOOD.key
            )
        )
        assertEquals(
            MoodValue.BEST,
            fromKey(
                MoodValue.BEST.key
            )
        )
    }
}
