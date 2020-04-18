package fr.shining_cat.everyday.models.critter

class LegsResourcesHolder {

    val partsCount: Int = LegsDrawable.values().size
    val firstItemIsOff = true

    enum class LegsDrawable(val key: Int, val resourceId: Int) {
        LEGS_PART_OFF(0, -1),//R.drawable.legs_off),
        LEGS_PART_1(1, -1),//R.drawable.legs_1),
        LEGS_PART_2(2, -1),//R.drawable.legs_2),
        LEGS_PART_3(3, -1),//R.drawable.legs_3),
        LEGS_PART_4(4, -1),//R.drawable.legs_4),
        LEGS_PART_5(5, -1),//R.drawable.legs_5),
        LEGS_PART_6(6, -1);//R.drawable.legs_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                LEGS_PART_OFF.key -> LEGS_PART_OFF
                LEGS_PART_1.key -> LEGS_PART_1
                LEGS_PART_2.key -> LEGS_PART_2
                LEGS_PART_3.key -> LEGS_PART_3
                LEGS_PART_4.key -> LEGS_PART_4
                LEGS_PART_5.key -> LEGS_PART_5
                LEGS_PART_6.key -> LEGS_PART_6
                else -> LEGS_PART_OFF
            }
//            fun keyToResourceId(key: Int) = when (key) {
//                LEGS_PART_OFF.key -> -1//R.drawable.legs_off
//                LEGS_PART_1.key -> -1//R.drawable.legs_1
//                LEGS_PART_2.key -> -1//R.drawable.legs_2
//                LEGS_PART_3.key -> -1//R.drawable.legs_3
//                LEGS_PART_4.key -> -1//R.drawable.legs_4
//                LEGS_PART_5.key -> -1//R.drawable.legs_5
//                LEGS_PART_6.key -> -1//R.drawable.legs_6
//                else -> -1//R.drawable.legs_off
//            }
        }
    }
}