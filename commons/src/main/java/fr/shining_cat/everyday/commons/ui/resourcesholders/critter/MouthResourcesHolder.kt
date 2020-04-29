package fr.shining_cat.everyday.commons.ui.resourcesholders.critter

class MouthResourcesHolder : CritterPartResourcesHolder() {

    override val partsCount: Int = MouthDrawable.values().size
    override val firstItemIsOff = false

    override fun getDefaultPartResourceId(): Int = MouthDrawable.MOUTH_PART_1.resourceId

    override fun getResourceIdForKey(key: Int): Int = MouthDrawable.fromKey(key)

    //There is no "OFF" part for mouth
    private enum class MouthDrawable(val key: Int, val resourceId: Int) {
        MOUTH_PART_1(1, -1),//R.drawable.mouth_1),
        MOUTH_PART_2(2, -1),//R.drawable.mouth_2),
        MOUTH_PART_3(3, -1),//R.drawable.mouth_3),
        MOUTH_PART_4(4, -1),//R.drawable.mouth_4),
        MOUTH_PART_5(5, -1),//R.drawable.mouth_5),
        MOUTH_PART_6(6, -1);//R.drawable.mouth_6);

        companion object {
            fun fromKey(key: Int) = when (key) {
                MOUTH_PART_1.key -> MOUTH_PART_1.resourceId
                MOUTH_PART_2.key -> MOUTH_PART_2.resourceId
                MOUTH_PART_3.key -> MOUTH_PART_3.resourceId
                MOUTH_PART_4.key -> MOUTH_PART_4.resourceId
                MOUTH_PART_5.key -> MOUTH_PART_5.resourceId
                MOUTH_PART_6.key -> MOUTH_PART_6.resourceId
                else -> MOUTH_PART_1.resourceId
            }

        }
    }
}