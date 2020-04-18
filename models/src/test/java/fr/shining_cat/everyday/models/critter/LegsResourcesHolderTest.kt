package fr.shining_cat.everyday.models.critter

import org.junit.Assert.*
import org.junit.Test

class LegsResourcesHolderTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_OFF,
            LegsResourcesHolder.LegsDrawable.fromKey(
                29
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_OFF,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_OFF.key
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_1,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_1.key
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_2,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_2.key
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_3,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_3.key
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_4,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_4.key
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_5,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_5.key
            )
        )
        assertEquals(
            LegsResourcesHolder.LegsDrawable.LEGS_PART_6,
            LegsResourcesHolder.LegsDrawable.fromKey(
                LegsResourcesHolder.LegsDrawable.LEGS_PART_6.key
            )
        )
    }
}