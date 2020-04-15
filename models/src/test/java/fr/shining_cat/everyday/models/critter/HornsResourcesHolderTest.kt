package fr.shining_cat.everyday.models.critter

import org.junit.Assert.*
import org.junit.Test

class HornsResourcesHolderTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_OFF,
            HornsResourcesHolder.HornsDrawable.fromKey(
                29
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_OFF,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_OFF.key
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_1,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_1.key
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_2,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_2.key
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_3,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_3.key
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_4,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_4.key
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_5,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_5.key
            )
        )
        assertEquals(
            HornsResourcesHolder.HornsDrawable.HORNS_PART_6,
            HornsResourcesHolder.HornsDrawable.fromKey(
                HornsResourcesHolder.HornsDrawable.HORNS_PART_6.key
            )
        )
    }
}