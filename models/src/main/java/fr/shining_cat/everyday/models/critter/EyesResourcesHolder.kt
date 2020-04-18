package fr.shining_cat.everyday.models.critter

class EyesResourcesHolder {


    val partsCount: Int = EyesDrawable.values().size
    val firstItemIsOff = true

    enum class EyesDrawable(val key: Int, val resourceId: Int) {
        EYES_PART_OFF(0, -1),//R.drawable.eyes_off),
        EYES_PART_1(1, -1),//R.drawable.eyes_1),
        EYES_PART_2(2, -1),//R.drawable.eyes_2),
        EYES_PART_3(3, -1),//R.drawable.eyes_3),
        EYES_PART_4(4, -1),//R.drawable.eyes_4),
        EYES_PART_5(5, -1),//R.drawable.eyes_5),
        EYES_PART_6(6, -1);//R.drawable.eyes_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                EYES_PART_OFF.key -> EYES_PART_OFF
                EYES_PART_1.key -> EYES_PART_1
                EYES_PART_2.key -> EYES_PART_2
                EYES_PART_3.key -> EYES_PART_3
                EYES_PART_4.key -> EYES_PART_4
                EYES_PART_5.key -> EYES_PART_5
                EYES_PART_6.key -> EYES_PART_6
                else -> EYES_PART_OFF
            }
//            fun keyToResourceId(key: Int) = when (key) {
//                EYES_PART_OFF.key -> -1//R.drawable.eyes_off
//                EYES_PART_1.key -> -1//R.drawable.eyes_1
//                EYES_PART_2.key -> -1//R.drawable.eyes_2
//                EYES_PART_3.key -> -1//R.drawable.eyes_3
//                EYES_PART_4.key -> -1//R.drawable.eyes_4
//                EYES_PART_5.key -> -1//R.drawable.eyes_5
//                EYES_PART_6.key -> -1//R.drawable.eyes_6
//                else -> -1//R.drawable.eyes_off
//            }
        }
    }
}