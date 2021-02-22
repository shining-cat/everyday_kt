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

// TODO: seems that CritterHelper will no longer be needed
class CritterHelper(
    private val flowerResourcesHolder: CritterPartResourcesHolder.FlowerResourcesHolder,
    private val mouthResourcesHolder: CritterPartResourcesHolder.MouthResourcesHolder,
    private val legsResourcesHolder: CritterPartResourcesHolder.LegsResourcesHolder,
    private val armsResourcesHolder: CritterPartResourcesHolder.ArmsResourcesHolder,
    private val eyesResourcesHolder: CritterPartResourcesHolder.EyesResourcesHolder,
    private val hornsResourcesHolder: CritterPartResourcesHolder.HornsResourcesHolder
) {
    // TODO: probably not needed anymore: the random selection will be done on a Reward object
//    fun getRandomCritterCode(level: Level): String {
//        val randomParts = when (level) {
//            Level.LEVEL_5 -> arrayOf(
//                Random.nextInt(
//                    0,
//                    flowerResourcesHolder.partsCount
//                ),//do not exclude index 0 for flower and mouth
//                Random.nextInt(
//                    0,
//                    mouthResourcesHolder.partsCount
//                ),//do not exclude index 0 for flower and mouth
//                Random.nextInt(
//                    1,
//                    legsResourcesHolder.partsCount
//                ),//exclude index 0 (means "OFF")
//                Random.nextInt(
//                    1,
//                    armsResourcesHolder.partsCount
//                ),//exclude index 0 (means "OFF")
//                Random.nextInt(
//                    1,
//                    eyesResourcesHolder.partsCount
//                ),//exclude index 0 (means "OFF")
//                Random.nextInt(
//                    1,
//                    hornsResourcesHolder.partsCount
//                )//exclude index 0 (means "OFF")
//            )
//            Level.LEVEL_4 -> arrayOf(
//                Random.nextInt(0, flowerResourcesHolder.partsCount),
//                Random.nextInt(0, mouthResourcesHolder.partsCount),
//                Random.nextInt(1, legsResourcesHolder.partsCount),
//                Random.nextInt(1, armsResourcesHolder.partsCount),
//                Random.nextInt(1, eyesResourcesHolder.partsCount),
//                0
//            )
//            Level.LEVEL_3 -> arrayOf(
//                Random.nextInt(0, flowerResourcesHolder.partsCount),
//                Random.nextInt(0, mouthResourcesHolder.partsCount),
//                Random.nextInt(1, legsResourcesHolder.partsCount),
//                Random.nextInt(1, armsResourcesHolder.partsCount),
//                0,
//                0
//            )
//            Level.LEVEL_2 -> arrayOf(
//                Random.nextInt(0, flowerResourcesHolder.partsCount),
//                Random.nextInt(0, mouthResourcesHolder.partsCount),
//                Random.nextInt(1, legsResourcesHolder.partsCount),
//                0,
//                0,
//                0
//            )
//            Level.LEVEL_1 -> arrayOf(
//                Random.nextInt(0, flowerResourcesHolder.partsCount),
//                Random.nextInt(0, mouthResourcesHolder.partsCount),
//                0,
//                0,
//                0,
//                0
//            )
//        }
//        val critterCode = randomParts.joinToString(CODE_SEPARATOR)
//        return critterCode
//    }

    // TODO: check if really needed
//    fun getAllPossibleCritterCodesCount() = (getNumberOfCritterPossible(Level.LEVEL_1)
//            + getNumberOfCritterPossible(Level.LEVEL_2)
//            + getNumberOfCritterPossible(Level.LEVEL_3)
//            + getNumberOfCritterPossible(Level.LEVEL_4)
//            + getNumberOfCritterPossible(Level.LEVEL_4)
//            )
//
//
//    fun getNumberOfCritterPossible(level: Level): Int {
//        return when (level) {
//            Level.LEVEL_5 -> flowerResourcesHolder.partsCount * mouthResourcesHolder.partsCount * legsResourcesHolder.partsCount * armsResourcesHolder.partsCount * eyesResourcesHolder.partsCount * hornsResourcesHolder.partsCount
//            Level.LEVEL_4 -> flowerResourcesHolder.partsCount * mouthResourcesHolder.partsCount * legsResourcesHolder.partsCount * armsResourcesHolder.partsCount * eyesResourcesHolder.partsCount
//            Level.LEVEL_3 -> flowerResourcesHolder.partsCount * mouthResourcesHolder.partsCount * legsResourcesHolder.partsCount * armsResourcesHolder.partsCount
//            Level.LEVEL_2 -> flowerResourcesHolder.partsCount * mouthResourcesHolder.partsCount * legsResourcesHolder.partsCount
//            Level.LEVEL_1 -> flowerResourcesHolder.partsCount * mouthResourcesHolder.partsCount
//        }
//    }

    // TODO: this mechanism will be part of a usecase
//    fun getAllPossibleCritterCodes(): ImmutableSet<String> {
//        val allPossibleCritterCodes: MutableSet<String> = HashSet()
//        for (i in 1..hornsResourcesHolder.partsCount) { // do not include horn = 0
// //////////////// ALL PARTS EXCLUDE index 0 => REWARD_LEVEL_5
//            if (i != 0) {
//                for (j in 1..eyesResourcesHolder.partsCount) { // do not include eye = 0
//                    for (k in 0..mouthResourcesHolder.partsCount) {// mouth has no "off" level
//                        for (l in 1..armsResourcesHolder.partsCount) {// do not include arms = 0
//                            for (m in 1..legsResourcesHolder.partsCount) {// do not include legs = 0
//                                for (n in 0..flowerResourcesHolder.partsCount) {// flower has no "off" level
//                                    val parts = arrayOf(
//                                        n.toString(),
//                                        m.toString(),
//                                        l.toString(),
//                                        k.toString(),
//                                        j.toString(),
//                                        i.toString()
//                                    )
//                                    allPossibleCritterCodes.add(
//                                        parts.joinToString(
//                                            CODE_SEPARATOR
//                                        )
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                for (j in 0..eyesResourcesHolder.partsCount) {
// //////////////// HORN_PART is HORNS_PART_OFF, ALL OTHER PARTS EXCLUDE index 0 => REWARD_LEVEL_4
//                    if (j != 0) {
//                        for (k in 0..mouthResourcesHolder.partsCount) {// mouth has no "off" level
//                            for (l in 1..armsResourcesHolder.partsCount) {// do not include arms = 0
//                                for (m in 1..legsResourcesHolder.partsCount) {// do not include legs = 0
//                                    for (n in 0..flowerResourcesHolder.partsCount) {// flower has no "off" level
//                                        val parts = arrayOf(
//                                            n.toString(),
//                                            m.toString(),
//                                            l.toString(),
//                                            k.toString(),
//                                            j.toString(),
//                                            i.toString()
//                                        )
//                                        allPossibleCritterCodes.add(
//                                            parts.joinToString(
//                                                CODE_SEPARATOR
//                                            )
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        for (k in 0..mouthResourcesHolder.partsCount) {// mouth has no "off" level
//                            for (l in 0..armsResourcesHolder.partsCount) {
// //////////////// HORN_PART is HORNS_PART_OFF, EYES_PARTS is EYES_PART_OFF, ALL OTHER PARTS EXCLUDE index 0 => REWARD_LEVEL_3
//                                if (l != 0) {
//                                    for (m in 1..legsResourcesHolder.partsCount) {// do not include legs = 0
//                                        for (n in 0..flowerResourcesHolder.partsCount) {// flower has no "off" level
//                                            val parts = arrayOf(
//                                                n.toString(),
//                                                m.toString(),
//                                                l.toString(),
//                                                k.toString(),
//                                                j.toString(),
//                                                i.toString()
//                                            )
//                                            allPossibleCritterCodes.add(
//                                                parts.joinToString(
//                                                    CODE_SEPARATOR
//                                                )
//                                            )
//                                        }
//                                    }
//                                } else {
//                                    for (m in 0..legsResourcesHolder.partsCount) {
// //////////////// HORN_PART is HORNS_PART_OFF, EYES_PARTS is EYES_PART_OFF, ARMS_PARTS is ARMS_PART_OFF, ALL OTHER PARTS EXCLUDE index 0 => REWARD_LEVEL_2
//                                        if (m != 0) {//REWARD_LEVEL_2
//                                            for (n in 0..flowerResourcesHolder.partsCount) {// flower has no "off" level
//                                                val parts = arrayOf(
//                                                    n.toString(),
//                                                    m.toString(),
//                                                    l.toString(),
//                                                    k.toString(),
//                                                    j.toString(),
//                                                    i.toString()
//                                                )
//                                                allPossibleCritterCodes.add(
//                                                    parts.joinToString(
//                                                        CODE_SEPARATOR
//                                                    )
//                                                )
//                                            }
//                                        } else {//REWARD_LEVEL_1
// //////////////// HORN_PART is HORNS_PART_OFF, EYES_PARTS is EYES_PART_OFF, ARMS_PARTS is ARMS_PART_OFF, LEGS_PARTS is LEGS_PART_OFF=> REWARD_LEVEL_1
//                                            for (n in 0..flowerResourcesHolder.partsCount) {// flower has no "off" level
//                                                val parts = arrayOf(
//                                                    n.toString(),
//                                                    m.toString(),
//                                                    l.toString(),
//                                                    k.toString(),
//                                                    j.toString(),
//                                                    i.toString()
//                                                )
//                                                allPossibleCritterCodes.add(
//                                                    parts.joinToString(
//                                                        CODE_SEPARATOR
//                                                    )
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return ImmutableSet.copyOf(allPossibleCritterCodes)
//    }

    // TODO: this should not be needed anymore: Reward holds the level as Level
//    fun getLevel(critterCode: String): Level {
//        val splitCritterCode = critterCode.split(CODE_SEPARATOR)
//        return when {
//            splitCritterCode[HORNS_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0 -> Level.LEVEL_5
//            splitCritterCode[EYES_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0 -> Level.LEVEL_4
//            splitCritterCode[ARMS_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0 -> Level.LEVEL_3
//            splitCritterCode[LEGS_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0 -> Level.LEVEL_2
//            else -> Level.LEVEL_1
//        }
//    }
}
