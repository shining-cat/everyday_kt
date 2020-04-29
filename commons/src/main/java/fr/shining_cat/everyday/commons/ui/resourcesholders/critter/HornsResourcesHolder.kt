package fr.shining_cat.everyday.commons.ui.resourcesholders.critter

class HornsResourcesHolder : CritterPartResourcesHolder() {

    override val partsCount: Int = HornsDrawable.values().size
    override val firstItemIsOff = true

    override fun getDefaultPartResourceId(): Int = HornsDrawable.HORNS_PART_OFF.resourceId

    override fun getResourceIdForKey(key: Int): Int = HornsDrawable.fromKey(key)


    private enum class HornsDrawable(val key: Int, val resourceId: Int) {
        HORNS_PART_OFF(0, -1),//R.drawable.horns_off),
        HORNS_PART_1(1, -1),//R.drawable.horns_1),
        HORNS_PART_2(2, -1),//R.drawable.horns_2),
        HORNS_PART_3(3, -1),//R.drawable.horns_3),
        HORNS_PART_4(4, -1),//R.drawable.horns_4),
        HORNS_PART_5(5, -1),//R.drawable.horns_5),
        HORNS_PART_6(6, -1);//R.drawable.horns_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                HORNS_PART_OFF.key -> HORNS_PART_OFF.resourceId
                HORNS_PART_1.key -> HORNS_PART_1.resourceId
                HORNS_PART_2.key -> HORNS_PART_2.resourceId
                HORNS_PART_3.key -> HORNS_PART_3.resourceId
                HORNS_PART_4.key -> HORNS_PART_4.resourceId
                HORNS_PART_5.key -> HORNS_PART_5.resourceId
                HORNS_PART_6.key -> HORNS_PART_6.resourceId
                else -> HORNS_PART_OFF.resourceId
            }

        }
    }
}