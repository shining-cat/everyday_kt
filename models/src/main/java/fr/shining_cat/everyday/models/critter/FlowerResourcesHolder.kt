package fr.shining_cat.everyday.models.critter

class FlowerResourcesHolder {

    val partsCount: Int = FlowerDrawable.values().size
    val firstItemIsOff = false

    //There is no "OFF" part for Flower
    enum class FlowerDrawable(val key: Int, val resourceId: Int) {
        FLOWER_PART_1(1, -1),//R.drawable.flower_1),
        FLOWER_PART_2(2, -1),//R.drawable.flower_2),
        FLOWER_PART_3(3, -1),//R.drawable.flower_3),
        FLOWER_PART_4(4, -1),//R.drawable.flower_4),
        FLOWER_PART_5(5, -1),//R.drawable.flower_5),
        FLOWER_PART_6(6, -1);//R.drawable.flower_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                FLOWER_PART_1.key -> FLOWER_PART_1
                FLOWER_PART_2.key -> FLOWER_PART_2
                FLOWER_PART_3.key -> FLOWER_PART_3
                FLOWER_PART_4.key -> FLOWER_PART_4
                FLOWER_PART_5.key -> FLOWER_PART_5
                FLOWER_PART_6.key -> FLOWER_PART_6
                else -> FLOWER_PART_1
            }
//            fun keyToResourceId(key: Int) = when (key) {
//                FLOWER_PART_1.key -> -1//R.drawable.flower_1
//                FLOWER_PART_2.key -> -1//R.drawable.flower_2
//                FLOWER_PART_3.key -> -1//R.drawable.flower_3
//                FLOWER_PART_4.key -> -1//R.drawable.flower_4
//                FLOWER_PART_5.key -> -1//R.drawable.flower_5
//                FLOWER_PART_6.key -> -1//R.drawable.flower_6
//                else -> -1//R.drawable.flower_1
//            }
        }
    }
}