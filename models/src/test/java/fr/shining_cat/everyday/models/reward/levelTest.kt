package fr.shining_cat.everyday.models.reward

import fr.shining_cat.everyday.models.reward.Level
import fr.shining_cat.everyday.models.reward.Level.Companion.fromKey
import org.junit.Assert.assertEquals
import org.junit.Test

class levelTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            Level.LEVEL_1, fromKey(
                Level.LEVEL_1.key
            )
        )
        assertEquals(
            Level.LEVEL_2, fromKey(
                Level.LEVEL_2.key
            )
        )
        assertEquals(
            Level.LEVEL_3, fromKey(
                Level.LEVEL_3.key
            )
        )
        assertEquals(
            Level.LEVEL_4, fromKey(
                Level.LEVEL_4.key
            )
        )
        assertEquals(
            Level.LEVEL_5, fromKey(
                Level.LEVEL_5.key
            )
        )
    }
}