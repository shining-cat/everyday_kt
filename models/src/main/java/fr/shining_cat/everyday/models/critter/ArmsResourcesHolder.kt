package fr.shining_cat.everyday.models.critter

class ArmsResourcesHolder {

    val partsCount: Int = ArmsDrawable.values().size
    val firstItemIsOff = true

    enum class ArmsDrawable(val key: Int, val resourceId: Int) {
        ARMS_PART_OFF(0, -1),//R.drawable.arms_off),
        ARMS_PART_1(1, -1),//R.drawable.arms_1),
        ARMS_PART_2(2, -1),//R.drawable.arms_2),
        ARMS_PART_3(3, -1),//R.drawable.arms_3),
        ARMS_PART_4(4, -1),//R.drawable.arms_4),
        ARMS_PART_5(5, -1),//R.drawable.arms_5),
        ARMS_PART_6(6, -1);//R.drawable.arms_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                ARMS_PART_OFF.key -> ARMS_PART_OFF
                ARMS_PART_1.key -> ARMS_PART_1
                ARMS_PART_2.key -> ARMS_PART_2
                ARMS_PART_3.key -> ARMS_PART_3
                ARMS_PART_4.key -> ARMS_PART_4
                ARMS_PART_5.key -> ARMS_PART_5
                ARMS_PART_6.key -> ARMS_PART_6
                else -> ARMS_PART_OFF
            }
//            fun keyToResourceId(key: Int) = when (key) {
//                ARMS_PART_OFF.key -> -1//R.drawable.arms_off
//                ARMS_PART_1.key -> -1//R.drawable.arms_1
//                ARMS_PART_2.key -> -1//R.drawable.arms_2
//                ARMS_PART_3.key -> -1//R.drawable.arms_3
//                ARMS_PART_4.key -> -1//R.drawable.arms_4
//                ARMS_PART_5.key -> -1//R.drawable.arms_5
//                ARMS_PART_6.key -> -1//R.drawable.arms_6
//                else -> -1//R.drawable.arms_off
//            }
        }
    }
}