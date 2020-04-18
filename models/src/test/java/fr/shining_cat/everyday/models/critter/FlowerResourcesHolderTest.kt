package fr.shining_cat.everyday.models.critter

import org.junit.Assert.assertEquals
import org.junit.Test

class FlowerResourcesHolderTest {

    @Test
    fun testGetFromKey() {
        assertEquals(
            FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_1,
            FlowerResourcesHolder.FlowerDrawable.fromKey(
                FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_1.key
            )
        )
        assertEquals(
           FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_2,
            FlowerResourcesHolder.FlowerDrawable.fromKey(
                FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_2.key
            )
        )
        assertEquals(
           FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_3,
            FlowerResourcesHolder.FlowerDrawable.fromKey(
                FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_3.key
            )
        )
        assertEquals(
           FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_4,
            FlowerResourcesHolder.FlowerDrawable.fromKey(
                FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_4.key
            )
        )
        assertEquals(
           FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_5,
            FlowerResourcesHolder.FlowerDrawable.fromKey(
                FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_5.key
            )
        )
        assertEquals(
           FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_6,
            FlowerResourcesHolder.FlowerDrawable.fromKey(
                FlowerResourcesHolder.FlowerDrawable.FLOWER_PART_6.key
            )
        )
    }
}