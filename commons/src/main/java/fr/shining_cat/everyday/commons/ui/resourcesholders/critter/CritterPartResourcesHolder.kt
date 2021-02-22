/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.commons.ui.resourcesholders.critter

sealed class CritterPartResourcesHolder {

    abstract val partsCount: Int
    abstract val firstItemIsOff: Boolean

    abstract fun getDefaultPartResourceId(): Int
    abstract fun getResourceIdForKey(key: Int): Int

    class ArmsResourcesHolder : CritterPartResourcesHolder() {

        override val partsCount: Int = ArmsDrawable.values().size
        override val firstItemIsOff = true

        override fun getDefaultPartResourceId(): Int = ArmsDrawable.ARMS_PART_OFF.resourceId

        override fun getResourceIdForKey(key: Int): Int = ArmsDrawable.fromKey(key)

        private enum class ArmsDrawable(val key: Int, val resourceId: Int) {
            ARMS_PART_OFF(0, -1), // R.drawable.arms_off),
            ARMS_PART_1(1, -1), // R.drawable.arms_1),
            ARMS_PART_2(2, -1), // R.drawable.arms_2),
            ARMS_PART_3(3, -1), // R.drawable.arms_3),
            ARMS_PART_4(4, -1), // R.drawable.arms_4),
            ARMS_PART_5(5, -1), // R.drawable.arms_5),
            ARMS_PART_6(6, -1); // R.drawable.arms_6);

            companion object {

                fun fromKey(key: Int) = when (key) {
                    ARMS_PART_OFF.key -> ARMS_PART_OFF.resourceId
                    ARMS_PART_1.key -> ARMS_PART_1.resourceId
                    ARMS_PART_2.key -> ARMS_PART_2.resourceId
                    ARMS_PART_3.key -> ARMS_PART_3.resourceId
                    ARMS_PART_4.key -> ARMS_PART_4.resourceId
                    ARMS_PART_5.key -> ARMS_PART_5.resourceId
                    ARMS_PART_6.key -> ARMS_PART_6.resourceId
                    else -> ARMS_PART_OFF.resourceId
                }
            }
        }
    }

    class EyesResourcesHolder : CritterPartResourcesHolder() {

        override val partsCount: Int = EyesDrawable.values().size
        override val firstItemIsOff = true

        override fun getDefaultPartResourceId(): Int = EyesDrawable.EYES_PART_OFF.resourceId

        override fun getResourceIdForKey(key: Int): Int = EyesDrawable.fromKey(key)

        private enum class EyesDrawable(val key: Int, val resourceId: Int) {
            EYES_PART_OFF(0, -1), // R.drawable.eyes_off),
            EYES_PART_1(1, -1), // R.drawable.eyes_1),
            EYES_PART_2(2, -1), // R.drawable.eyes_2),
            EYES_PART_3(3, -1), // R.drawable.eyes_3),
            EYES_PART_4(4, -1), // R.drawable.eyes_4),
            EYES_PART_5(5, -1), // R.drawable.eyes_5),
            EYES_PART_6(6, -1); // R.drawable.eyes_6);

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

    class FlowerResourcesHolder : CritterPartResourcesHolder() {

        override val partsCount: Int = FlowerDrawable.values().size
        override val firstItemIsOff = true

        override fun getDefaultPartResourceId(): Int = FlowerDrawable.FLOWER_PART_1.resourceId

        override fun getResourceIdForKey(key: Int): Int = FlowerDrawable.fromKey(key)

        // There is no "OFF" part for Flower
        private enum class FlowerDrawable(val key: Int, val resourceId: Int) {

            FLOWER_PART_1(1, -1), // R.drawable.flower_1),
            FLOWER_PART_2(2, -1), // R.drawable.flower_2),
            FLOWER_PART_3(3, -1), // R.drawable.flower_3),
            FLOWER_PART_4(4, -1), // R.drawable.flower_4),
            FLOWER_PART_5(5, -1), // R.drawable.flower_5),
            FLOWER_PART_6(6, -1); // R.drawable.flower_6);

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

    class HornsResourcesHolder : CritterPartResourcesHolder() {

        override val partsCount: Int = HornsDrawable.values().size
        override val firstItemIsOff = true

        override fun getDefaultPartResourceId(): Int = HornsDrawable.HORNS_PART_OFF.resourceId

        override fun getResourceIdForKey(key: Int): Int = HornsDrawable.fromKey(key)

        private enum class HornsDrawable(val key: Int, val resourceId: Int) {
            HORNS_PART_OFF(0, -1), // R.drawable.horns_off),
            HORNS_PART_1(1, -1), // R.drawable.horns_1),
            HORNS_PART_2(2, -1), // R.drawable.horns_2),
            HORNS_PART_3(3, -1), // R.drawable.horns_3),
            HORNS_PART_4(4, -1), // R.drawable.horns_4),
            HORNS_PART_5(5, -1), // R.drawable.horns_5),
            HORNS_PART_6(6, -1); // R.drawable.horns_6);

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

    class LegsResourcesHolder : CritterPartResourcesHolder() {

        override val partsCount: Int = LegsDrawable.values().size
        override val firstItemIsOff = true

        override fun getDefaultPartResourceId(): Int = LegsDrawable.LEGS_PART_OFF.resourceId

        override fun getResourceIdForKey(key: Int): Int = LegsDrawable.fromKey(key)

        private enum class LegsDrawable(val key: Int, val resourceId: Int) {
            LEGS_PART_OFF(0, -1), // R.drawable.legs_off),
            LEGS_PART_1(1, -1), // R.drawable.legs_1),
            LEGS_PART_2(2, -1), // R.drawable.legs_2),
            LEGS_PART_3(3, -1), // R.drawable.legs_3),
            LEGS_PART_4(4, -1), // R.drawable.legs_4),
            LEGS_PART_5(5, -1), // R.drawable.legs_5),
            LEGS_PART_6(6, -1); // R.drawable.legs_6);

            companion object {

                fun fromKey(key: Int) = when (key) {
                    LEGS_PART_OFF.key -> LEGS_PART_OFF.resourceId
                    LEGS_PART_1.key -> LEGS_PART_1.resourceId
                    LEGS_PART_2.key -> LEGS_PART_2.resourceId
                    LEGS_PART_3.key -> LEGS_PART_3.resourceId
                    LEGS_PART_4.key -> LEGS_PART_4.resourceId
                    LEGS_PART_5.key -> LEGS_PART_5.resourceId
                    LEGS_PART_6.key -> LEGS_PART_6.resourceId
                    else -> LEGS_PART_OFF.resourceId
                }
            }
        }
    }

    class MouthResourcesHolder : CritterPartResourcesHolder() {

        override val partsCount: Int = MouthDrawable.values().size
        override val firstItemIsOff = false

        override fun getDefaultPartResourceId(): Int = MouthDrawable.MOUTH_PART_1.resourceId

        override fun getResourceIdForKey(key: Int): Int = MouthDrawable.fromKey(key)

        // There is no "OFF" part for mouth
        private enum class MouthDrawable(val key: Int, val resourceId: Int) {

            MOUTH_PART_1(1, -1), // R.drawable.mouth_1),
            MOUTH_PART_2(2, -1), // R.drawable.mouth_2),
            MOUTH_PART_3(3, -1), // R.drawable.mouth_3),
            MOUTH_PART_4(4, -1), // R.drawable.mouth_4),
            MOUTH_PART_5(5, -1), // R.drawable.mouth_5),
            MOUTH_PART_6(6, -1); // R.drawable.mouth_6);

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
}
