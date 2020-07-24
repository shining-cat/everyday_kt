package fr.shining_cat.everyday.models.sessionrecord

import fr.shining_cat.everyday.models.sessionrecord.MoodValue
import fr.shining_cat.everyday.models.sessionrecord.MoodValue.Companion.fromKey
import org.junit.Assert.assertEquals
import org.junit.Test

class MoodValueTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            MoodValue.WORST, fromKey(
                MoodValue.WORST.key
            )
        )
        assertEquals(
            MoodValue.BAD, fromKey(
                MoodValue.BAD.key
            )
        )
        assertEquals(
            MoodValue.NOT_SET, fromKey(
                MoodValue.NOT_SET.key
            )
        )
        assertEquals(
            MoodValue.GOOD, fromKey(
                MoodValue.GOOD.key
            )
        )
        assertEquals(
            MoodValue.BEST, fromKey(
                MoodValue.BEST.key
            )
        )
    }
}