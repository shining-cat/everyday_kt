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

import fr.shining_cat.everyday.models.sessionrecord.RealDurationVsPlanned
import fr.shining_cat.everyday.models.sessionrecord.RealDurationVsPlanned.Companion.fromKey
import org.junit.Assert.assertEquals
import org.junit.Test


class RealDurationVsPlannedTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            RealDurationVsPlanned.EQUAL, fromKey(
                RealDurationVsPlanned.EQUAL.key
            )
        )
        assertEquals(
            RealDurationVsPlanned.REAL_LONGER,
            fromKey(RealDurationVsPlanned.REAL_LONGER.key)
        )
        assertEquals(
            RealDurationVsPlanned.REAL_SHORTER,
            fromKey(RealDurationVsPlanned.REAL_SHORTER.key)
        )
    }
}