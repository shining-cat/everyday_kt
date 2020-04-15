package fr.shining_cat.everyday.models.critter

import org.junit.Assert.*
import org.junit.Test

class EyesResourcesHolderTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_OFF,
            EyesResourcesHolder.EyesDrawable.fromKey(
                29
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_OFF,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_OFF.key
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_1,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_1.key
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_2,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_2.key
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_3,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_3.key
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_4,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_4.key
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_5,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_5.key
            )
        )
        assertEquals(
            EyesResourcesHolder.EyesDrawable.EYES_PART_6,
            EyesResourcesHolder.EyesDrawable.fromKey(
                EyesResourcesHolder.EyesDrawable.EYES_PART_6.key
            )
        )
    }
}