package fr.shining_cat.everyday.models.critter

class FlowerResourcesHolder: CritterPartResourcesHolder(){

    override val partsCount: Int = FlowerDrawable.values().size
    override val firstItemIsOff = true

    override fun getDefaultPartResourceId(): Int = FlowerDrawable.FLOWER_PART_1.resourceId

    override fun getResourceIdForKey(key: Int): Int = FlowerDrawable.fromKey(key)

    //There is no "OFF" part for Flower
    private enum class FlowerDrawable(val key: Int, val resourceId: Int) {
        FLOWER_PART_1(1, -1),//R.drawable.flower_1),
        FLOWER_PART_2(2, -1),//R.drawable.flower_2),
        FLOWER_PART_3(3, -1),//R.drawable.flower_3),
        FLOWER_PART_4(4, -1),//R.drawable.flower_4),
        FLOWER_PART_5(5, -1),//R.drawable.flower_5),
        FLOWER_PART_6(6, -1);//R.drawable.flower_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                FLOWER_PART_1.key -> FLOWER_PART_1.resourceId
                FLOWER_PART_2.key -> FLOWER_PART_2.resourceId
                FLOWER_PART_3.key -> FLOWER_PART_3.resourceId
                FLOWER_PART_4.key -> FLOWER_PART_4.resourceId
                FLOWER_PART_5.key -> FLOWER_PART_5.resourceId
                FLOWER_PART_6.key -> FLOWER_PART_6.resourceId
                else -> FLOWER_PART_1.resourceId
            }

        }
    }
}