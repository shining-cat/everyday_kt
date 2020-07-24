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