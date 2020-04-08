package fr.shining_cat.everyday.models

import fr.shining_cat.everyday.models.MoodValue.Companion.fromKey
import org.junit.Assert.*
import org.junit.Test

class MoodValueTest{

    @Test
    fun testGetFromKey(){
        assertEquals(MoodValue.WORST, fromKey(MoodValue.WORST.key))
        assertEquals(MoodValue.BAD, fromKey(MoodValue.BAD.key))
        assertEquals(MoodValue.NOT_SET, fromKey(MoodValue.NOT_SET.key))
        assertEquals(MoodValue.GOOD, fromKey(MoodValue.GOOD.key))
        assertEquals(MoodValue.BEST, fromKey(MoodValue.BEST.key))
    }
}