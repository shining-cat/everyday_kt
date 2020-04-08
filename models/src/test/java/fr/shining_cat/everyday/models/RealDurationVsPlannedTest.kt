package fr.shining_cat.everyday.models

import fr.shining_cat.everyday.models.RealDurationVsPlanned.Companion.fromKey
import org.junit.Assert.*
import org.junit.Test


class RealDurationVsPlannedTest{

    @Test
    fun testGetFromKey(){
        assertEquals(RealDurationVsPlanned.EQUAL, fromKey(RealDurationVsPlanned.EQUAL.key))
        assertEquals(RealDurationVsPlanned.REAL_LONGER, fromKey(RealDurationVsPlanned.REAL_LONGER.key))
        assertEquals(RealDurationVsPlanned.REAL_SHORTER, fromKey(RealDurationVsPlanned.REAL_SHORTER.key))
    }
}