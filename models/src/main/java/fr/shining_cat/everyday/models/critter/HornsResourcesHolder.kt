package fr.shining_cat.everyday.models.critter

class HornsResourcesHolder {

    val partsCount: Int = HornsDrawable.values().size
    val firstItemIsOff = true

    enum class HornsDrawable(val key: Int, val resourceId: Int) {
        HORNS_PART_OFF(0, -1),//R.drawable.horns_off),
        HORNS_PART_1(1, -1),//R.drawable.horns_1),
        HORNS_PART_2(2, -1),//R.drawable.horns_2),
        HORNS_PART_3(3, -1),//R.drawable.horns_3),
        HORNS_PART_4(4, -1),//R.drawable.horns_4),
        HORNS_PART_5(5, -1),//R.drawable.horns_5),
        HORNS_PART_6(6, -1);//R.drawable.horns_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                HORNS_PART_OFF.key -> HORNS_PART_OFF
                HORNS_PART_1.key -> HORNS_PART_1
                HORNS_PART_2.key -> HORNS_PART_2
                HORNS_PART_3.key -> HORNS_PART_3
                HORNS_PART_4.key -> HORNS_PART_4
                HORNS_PART_5.key -> HORNS_PART_5
                HORNS_PART_6.key -> HORNS_PART_6
                else -> HORNS_PART_OFF
            }
//            fun keyToResourceId(key: Int) = when (key) {
//                HORNS_PART_OFF.key -> -1//R.drawable.horns_off
//                HORNS_PART_1.key -> -1//R.drawable.horns_1
//                HORNS_PART_2.key -> -1//R.drawable.horns_2
//                HORNS_PART_3.key -> -1//R.drawable.horns_3
//                HORNS_PART_4.key -> -1//R.drawable.horns_4
//                HORNS_PART_5.key -> -1//R.drawable.horns_5
//                HORNS_PART_6.key -> -1//R.drawable.horns_6
//                else -> -1//R.drawable.horns_off
//            }
        }
    }
}