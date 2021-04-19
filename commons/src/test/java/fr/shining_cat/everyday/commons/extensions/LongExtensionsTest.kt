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

package fr.shining_cat.everyday.commons.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class LongExtensionsTest {

    /*
    CASES to tests:
    5000L -> 5s / 05
    32000L -> 32s / 32
    180000L -> 3mn / 03:00
    780000L -> 13mn / 13:00
    124000L -> 2mn 04s / 02:04
    103000L -> 1mn 43s / 01:43
    3600000L -> 1h / 01:00:00
    57600000L -> 16h / 16:00:00
    7380000L -> 2h 03mn / 02:03:00
    13500000L-> 3h 45mn / 03:45:00
    11045000L -> 3h 04mn 05s / 03:04:05
    11096000L -> 3h 04mn 56s / 03:04:56
    443096000L -> 123h 04mn 56s / 123:04:56
     */
    private val formatStringHoursMinutesSeconds = "%1\$dh %2\$02dmn %3\$02ds"
    private val formatStringHoursMinutesNoSeconds = "%1\$dh %2\$02dmn"
    private val formatStringHoursNoMinutesNoSeconds = "%1\$dh"
    private val formatStringMinutesSeconds = "%1\$dmn %2\$02ds"
    private val formatStringMinutesNoSeconds = "%1\$dmn"
    private val formatStringSeconds = "%1\$ds"

    @Test
    fun `test 5s`() {
        val raw = 5000L
        val expectedFormatted = "5s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 5s default format string`() {
        val raw = 5000L
        val expectedFormatted = "05"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 32s`() {
        val raw = 32000L
        val expectedFormatted = "32s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 32s default format string`() {
        val raw = 32000L
        val expectedFormatted = "32"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3mn`() {
        val raw = 180000L
        val expectedFormatted = "3mn"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3mn default format string`() {
        val raw = 180000L
        val expectedFormatted = "03:00"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 13mn`() {
        val raw = 780000L
        val expectedFormatted = "13mn"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 13mn default format string`() {
        val raw = 780000L
        val expectedFormatted = "13:00"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 2mn04s`() {
        val raw = 124000L
        val expectedFormatted = "2mn 04s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 2mn04s default format string`() {
        val raw = 124000L
        val expectedFormatted = "02:04"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 1mn43s`() {
        val raw = 103000L
        val expectedFormatted = "1mn 43s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 1mn43s default format string`() {
        val raw = 103000L
        val expectedFormatted = "01:43"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 1h`() {
        val raw = 3600000L
        val expectedFormatted = "1h"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 1h default format string`() {
        val raw = 3600000L
        val expectedFormatted = "01:00:00"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 16h`() {
        val raw = 57600000L
        val expectedFormatted = "16h"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 16h default format string`() {
        val raw = 57600000L
        val expectedFormatted = "16:00:00"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 2h03mn`() {
        val raw = 7380000L
        val expectedFormatted = "2h 03mn"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 2h03mn default format string`() {
        val raw = 7380000L
        val expectedFormatted = "02:03:00"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3h45mn`() {
        val raw = 13500000L
        val expectedFormatted = "3h 45mn"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3h45mn default format string`() {
        val raw = 13500000L
        val expectedFormatted = "03:45:00"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3h04mn05s`() {
        val raw = 11045000L
        val expectedFormatted = "3h 04mn 05s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3h04mn05s default format string`() {
        val raw = 11045000L
        val expectedFormatted = "03:04:05"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3h04mn56s`() {
        val raw = 11096000L
        val expectedFormatted = "3h 04mn 56s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 3h04mn56s default format string`() {
        val raw = 11096000L
        val expectedFormatted = "03:04:56"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 123h04mn56s`() {
        val raw = 443096000L
        val expectedFormatted = "123h 04mn 56s"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString(
            formatStringHoursMinutesSeconds,
            formatStringHoursMinutesNoSeconds,
            formatStringHoursNoMinutesNoSeconds,
            formatStringMinutesSeconds,
            formatStringMinutesNoSeconds,
            formatStringSeconds
        )
        assertEquals(
            expectedFormatted,
            result
        )
    }

    @Test
    fun `test 123h04mn56s default format string`() {
        val raw = 443096000L
        val expectedFormatted = "123:04:56"
        val result = raw.autoFormatDurationMsAsSmallestHhMmSsString()
        assertEquals(
            expectedFormatted,
            result
        )
    }
}