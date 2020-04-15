package fr.shining_cat.everyday.models.critter

import org.junit.Assert.*
import org.junit.Test

class MouthResourcesHolderTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            MouthResourcesHolder.MouthDrawable.MOUTH_PART_1,
            MouthResourcesHolder.MouthDrawable.fromKey(
                MouthResourcesHolder.MouthDrawable.MOUTH_PART_1.key
            )
        )
        assertEquals(
            MouthResourcesHolder.MouthDrawable.MOUTH_PART_2,
            MouthResourcesHolder.MouthDrawable.fromKey(
                MouthResourcesHolder.MouthDrawable.MOUTH_PART_2.key
            )
        )
        assertEquals(
            MouthResourcesHolder.MouthDrawable.MOUTH_PART_3,
            MouthResourcesHolder.MouthDrawable.fromKey(
                MouthResourcesHolder.MouthDrawable.MOUTH_PART_3.key
            )
        )
        assertEquals(
            MouthResourcesHolder.MouthDrawable.MOUTH_PART_4,
            MouthResourcesHolder.MouthDrawable.fromKey(
                MouthResourcesHolder.MouthDrawable.MOUTH_PART_4.key
            )
        )
        assertEquals(
            MouthResourcesHolder.MouthDrawable.MOUTH_PART_5,
            MouthResourcesHolder.MouthDrawable.fromKey(
                MouthResourcesHolder.MouthDrawable.MOUTH_PART_5.key
            )
        )
        assertEquals(
            MouthResourcesHolder.MouthDrawable.MOUTH_PART_6,
            MouthResourcesHolder.MouthDrawable.fromKey(
                MouthResourcesHolder.MouthDrawable.MOUTH_PART_6.key
            )
        )
    }
}