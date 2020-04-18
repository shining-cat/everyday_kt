package fr.shining_cat.everyday.models.critter

import org.junit.Assert.assertEquals
import org.junit.Test

class ArmsResourcesHolderTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_OFF,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                29
            )
        )
         assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_OFF,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_OFF.key
            )
        )
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_1,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_1.key
            )
        )
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_2,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_2.key
            )
        )
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_3,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_3.key
            )
        )
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_4,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_4.key
            )
        )
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_5,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_5.key
            )
        )
        assertEquals(
            ArmsResourcesHolder.ArmsDrawable.ARMS_PART_6,
            ArmsResourcesHolder.ArmsDrawable.fromKey(
                ArmsResourcesHolder.ArmsDrawable.ARMS_PART_6.key
            )
        )
    }



}