package fr.shining_cat.everyday.models

import fr.shining_cat.everyday.models.CritterLevel.Companion.fromKey
import org.junit.Assert.*
import org.junit.Test

class CritterLevelTest{

    @Test
    fun testGetFromKey(){
        assertEquals(CritterLevel.LEVEL_1, fromKey(CritterLevel.LEVEL_1.key))
        assertEquals(CritterLevel.LEVEL_2, fromKey(CritterLevel.LEVEL_2.key))
        assertEquals(CritterLevel.LEVEL_3, fromKey(CritterLevel.LEVEL_3.key))
        assertEquals(CritterLevel.LEVEL_4, fromKey(CritterLevel.LEVEL_4.key))
        assertEquals(CritterLevel.LEVEL_5, fromKey(CritterLevel.LEVEL_5.key))
    }
}