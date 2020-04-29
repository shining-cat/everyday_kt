package fr.shining_cat.everyday.commons.ui.resourcesholders.critter

class EyesResourcesHolder : CritterPartResourcesHolder() {

    override val partsCount: Int = EyesDrawable.values().size
    override val firstItemIsOff = true

    override fun getDefaultPartResourceId(): Int = EyesDrawable.EYES_PART_OFF.resourceId

    override fun getResourceIdForKey(key: Int): Int = EyesDrawable.fromKey(key)

    private enum class EyesDrawable(val key: Int, val resourceId: Int) {
        EYES_PART_OFF(0, -1),//R.drawable.eyes_off),
        EYES_PART_1(1, -1),//R.drawable.eyes_1),
        EYES_PART_2(2, -1),//R.drawable.eyes_2),
        EYES_PART_3(3, -1),//R.drawable.eyes_3),
        EYES_PART_4(4, -1),//R.drawable.eyes_4),
        EYES_PART_5(5, -1),//R.drawable.eyes_5),
        EYES_PART_6(6, -1);//R.drawable.eyes_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                EYES_PART_OFF.key -> EYES_PART_OFF.resourceId
                EYES_PART_1.key -> EYES_PART_1.resourceId
                EYES_PART_2.key -> EYES_PART_2.resourceId
                EYES_PART_3.key -> EYES_PART_3.resourceId
                EYES_PART_4.key -> EYES_PART_4.resourceId
                EYES_PART_5.key -> EYES_PART_5.resourceId
                EYES_PART_6.key -> EYES_PART_6.resourceId
                else -> EYES_PART_OFF.resourceId
            }
        }
    }
}