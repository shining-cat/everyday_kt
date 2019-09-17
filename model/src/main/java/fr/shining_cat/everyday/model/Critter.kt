package fr.shining_cat.everyday.model

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import fr.shining_cat.everyday.utils.extensions.logD
import fr.shining_cat.everyday.utils.extensions.logE
import kotlin.random.Random

abstract class Critter{

    // TODO : we need 6 versions of FLOWER and MOUTH + 7 versions of LEGS, ARMS, and EYES (6 normal and 1 "off" version : legs or arms folded, eyes closed), the "off" version of HORNS is simply empty
    //parts always "active" : flower, mouth
    enum class Level {
        LEVEL_1, // 0 to 5mn    : flower = X | legs = 0 | arms = 0 | mouth = X | eyes = 0 | horns = 0  => 36 combinations
        LEVEL_2, // 5 to 15mn   : flower = X | legs = X | arms = 0 | mouth = X | eyes = 0 | horns = 0  => 216 combinations
        LEVEL_3, // 15 to 30mn  : flower = X | legs = X | arms = X | mouth = X | eyes = 0 | horns = 0  => 1296 combinations
        LEVEL_4, // 30 to 60mn  : flower = X | legs = X | arms = X | mouth = X | eyes = X | horns = 0  => 7776 combinations
        LEVEL_5 // 60mn and +  : flower = X | legs = X | arms = X | mouth = X | eyes = X | horns = X  => 46656 combinations
        //total combinations => 55980
    }

    companion object {
        const val NUMBER_OF_PARTS = 6

        const val CRITTER_CODE_SEPARATOR = "_"
        const val FLOWERS_CODE_INDEX_IN_CRITTER_CODE = 0
        const val LEGS_CODE_INDEX_IN_CRITTER_CODE = 1
        const val ARMS_CODE_INDEX_IN_CRITTER_CODE = 2
        const val MOUTH_CODE_INDEX_IN_CRITTER_CODE = 3
        const val EYES_CODE_INDEX_IN_CRITTER_CODE = 4
        const val HORNS_CODE_INDEX_IN_CRITTER_CODE = 5

        //TODO: vectorize body parts in two files each : one with only black lines and shadows, the other full white for colorization
        //TODO: the lines one will be put on top of the other, which will be altered via setColorFilter according to user set color
        //TODO: beware when importing future body parts as vectors, seems that from API 24 and above, too long paths will break everything, while the vector will work on lower APIs because then it uses the support library...
        const val LEGS_PART_OFF  =  -1//R.drawable.legs_0
        const val ARMS_PART_OFF  =  -1//R.drawable.arms_0
        const val EYES_PART_OFF  =  -1//R.drawable.eyes_0
        const val HORNS_PART_OFF =  -1//R.drawable.horns_0 // = empty picture

        val FLOWER_PARTS = arrayOf(-1, -1, -1, -1, -1, -1)
        val LEGS_PARTS = arrayOf(-1, -1, -1, -1, -1, -1, -1)
        val ARMS_PARTS = arrayOf(-1, -1, -1, -1, -1, -1, -1)
        val MOUTH_PARTS = arrayOf(-1, -1, -1, -1, -1, -1)
        val EYES_PARTS = arrayOf(-1, -1, -1, -1, -1, -1, -1)
        val HORNS_PARTS = arrayOf(-1, -1, -1, -1, -1, -1, -1)
//        val FLOWER_PARTS = arrayOf(R.drawable.flower_1, R.drawable.flower_2, R.drawable.flower_3, R.drawable.flower_4, R.drawable.flower_5, R.drawable.flower_6)
//        val LEGS_PARTS = arrayOf(LEGS_PART_OFF, R.drawable.legs_1, R.drawable.legs_2, R.drawable.legs_3, R.drawable.legs_4, R.drawable.legs_5, R.drawable.legs_6)
//        val ARMS_PARTS = arrayOf(ARMS_PART_OFF, R.drawable.arms_1, R.drawable.arms_2, R.drawable.arms_3, R.drawable.arms_4, R.drawable.arms_5, R.drawable.arms_6)
//        val MOUTH_PARTS = arrayOf(R.drawable.mouth_1, R.drawable.mouth_2, R.drawable.mouth_3, R.drawable.mouth_4, R.drawable.mouth_5, R.drawable.mouth_6)
//        val EYES_PARTS = arrayOf(EYES_PART_OFF, R.drawable.eyes_1, R.drawable.eyes_2, R.drawable.eyes_3, R.drawable.eyes_4, R.drawable.eyes_5, R.drawable.eyes_6)
//        val HORNS_PARTS = arrayOf(HORNS_PART_OFF, R.drawable.horns_1, R.drawable.horns_2, R.drawable.horns_3, R.drawable.horns_4, R.drawable.horns_5, R.drawable.horns_6)

        fun getRandomCritterCode(critterLevel: Level): String {
            val randomParts = when (critterLevel) {
                Level.LEVEL_5 -> arrayOf(Random.nextInt(FLOWER_PARTS.size), Random.nextInt(MOUTH_PARTS.size), Random.nextInt(LEGS_PARTS.size), Random.nextInt(ARMS_PARTS.size), Random.nextInt(EYES_PARTS.size), Random.nextInt(HORNS_PARTS.size))
                Level.LEVEL_4 -> arrayOf(Random.nextInt(FLOWER_PARTS.size), Random.nextInt(MOUTH_PARTS.size), Random.nextInt(LEGS_PARTS.size), Random.nextInt(ARMS_PARTS.size), Random.nextInt(EYES_PARTS.size), 0)
                Level.LEVEL_3 -> arrayOf(Random.nextInt(FLOWER_PARTS.size), Random.nextInt(MOUTH_PARTS.size), Random.nextInt(LEGS_PARTS.size), Random.nextInt(ARMS_PARTS.size), 0, 0)
                Level.LEVEL_2 -> arrayOf(Random.nextInt(FLOWER_PARTS.size), Random.nextInt(MOUTH_PARTS.size), Random.nextInt(LEGS_PARTS.size), 0, 0, 0)
                Level.LEVEL_1 -> arrayOf(Random.nextInt(FLOWER_PARTS.size), Random.nextInt(MOUTH_PARTS.size), 0, 0, 0, 0)
            }
            val critterCode = randomParts.joinToString(CRITTER_CODE_SEPARATOR)
            logD("LOGGING::CRITTER", "getRandomCritterCode:: code = $critterCode")
            return critterCode
        }

        fun getAllPossibleCritterCodesCount() = getNumberOfCritterPossible(Level.LEVEL_1) + getNumberOfCritterPossible(Level.LEVEL_2) + getNumberOfCritterPossible(Level.LEVEL_3) + getNumberOfCritterPossible(Level.LEVEL_4) + getNumberOfCritterPossible(Level.LEVEL_4)

        fun getNumberOfCritterPossible(critterLevel: Level): Int{
            return when (critterLevel) {
                Level.LEVEL_5 -> FLOWER_PARTS.size * MOUTH_PARTS.size * LEGS_PARTS.size * ARMS_PARTS.size * EYES_PARTS.size * HORNS_PARTS.size
                Level.LEVEL_4 -> FLOWER_PARTS.size * MOUTH_PARTS.size * LEGS_PARTS.size * ARMS_PARTS.size * EYES_PARTS.size
                Level.LEVEL_3 -> FLOWER_PARTS.size * MOUTH_PARTS.size * LEGS_PARTS.size * ARMS_PARTS.size
                Level.LEVEL_2 -> FLOWER_PARTS.size * MOUTH_PARTS.size * LEGS_PARTS.size
                Level.LEVEL_1 -> FLOWER_PARTS.size * MOUTH_PARTS.size
            }
        }

        fun getAllPossibleCritterCodes(): ImmutableSet<String>{
            val allPossibleCritterCodes: MutableSet<String> = HashSet()
            for(i in 1..HORNS_PARTS.size) { // do not include eye = 0
//////////////// ALL PARTS EXCLUDE index 0 => REWARD_LEVEL_5
                if(i!=0){
                    for (j in 1..EYES_PARTS.size) { // do not include eye = 0
                        for (k in 0..MOUTH_PARTS.size) {// mouth has no "off" level
                            for (l in 1..ARMS_PARTS.size) {// do not include arms = 0
                                for (m in 1..LEGS_PARTS.size) {// do not include legs = 0
                                    for (n in 0..FLOWER_PARTS.size) {// flower has no "off" level
                                        val parts = arrayOf(n.toString(), m.toString(), l.toString(), k.toString(), j.toString(), i.toString())
                                        allPossibleCritterCodes.add(parts.joinToString(CRITTER_CODE_SEPARATOR))
                                    }
                                }
                            }
                        }
                    }
                }else{
                    for (j in 0..EYES_PARTS.size) {
//////////////// HORN_PART is HORNS_PART_OFF, ALL OTHER PARTS EXCLUDE index 0 => REWARD_LEVEL_4
                        if(j != 0) {
                            for (k in 0..MOUTH_PARTS.size) {// mouth has no "off" level
                                for (l in 1..ARMS_PARTS.size) {// do not include arms = 0
                                    for (m in 1..LEGS_PARTS.size) {// do not include legs = 0
                                        for (n in 0..FLOWER_PARTS.size) {// flower has no "off" level
                                            val parts = arrayOf(n.toString(), m.toString(), l.toString(), k.toString(), j.toString(), i.toString())
                                            allPossibleCritterCodes.add(parts.joinToString(CRITTER_CODE_SEPARATOR))
                                        }
                                    }
                                }
                            }
                        }else{
                            for (k in 0..MOUTH_PARTS.size) {// mouth has no "off" level
                                for (l in 0..ARMS_PARTS.size) {
//////////////// HORN_PART is HORNS_PART_OFF, EYES_PARTS is EYES_PART_OFF, ALL OTHER PARTS EXCLUDE index 0 => REWARD_LEVEL_3
                                    if(l != 0) {
                                        for (m in 1..LEGS_PARTS.size) {// do not include legs = 0
                                            for (n in 0..FLOWER_PARTS.size) {// flower has no "off" level
                                                val parts = arrayOf(n.toString(), m.toString(), l.toString(), k.toString(), j.toString(), i.toString())
                                                allPossibleCritterCodes.add(parts.joinToString(CRITTER_CODE_SEPARATOR))
                                            }
                                        }
                                    }else{
                                        for (m in 0..LEGS_PARTS.size) {
//////////////// HORN_PART is HORNS_PART_OFF, EYES_PARTS is EYES_PART_OFF, ARMS_PARTS is ARMS_PART_OFF, ALL OTHER PARTS EXCLUDE index 0 => REWARD_LEVEL_2
                                            if(m != 0) {//REWARD_LEVEL_2
                                                for (n in 0..FLOWER_PARTS.size) {// flower has no "off" level
                                                    val parts = arrayOf(n.toString(), m.toString(), l.toString(), k.toString(), j.toString(), i.toString())
                                                    allPossibleCritterCodes.add(parts.joinToString(CRITTER_CODE_SEPARATOR))
                                                }
                                            }else{//REWARD_LEVEL_1
//////////////// HORN_PART is HORNS_PART_OFF, EYES_PARTS is EYES_PART_OFF, ARMS_PARTS is ARMS_PART_OFF, LEGS_PARTS is LEGS_PART_OFF=> REWARD_LEVEL_1
                                                for (n in 0..FLOWER_PARTS.size) {// flower has no "off" level
                                                    val parts = arrayOf(n.toString(), m.toString(), l.toString(), k.toString(), j.toString(), i.toString())
                                                    allPossibleCritterCodes.add(parts.joinToString(CRITTER_CODE_SEPARATOR))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return ImmutableSet.copyOf(allPossibleCritterCodes)
        }

        fun getLevel(critterCode: String):Level {
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            return if (splitCritterCode[HORNS_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0) Level.LEVEL_5
            else if (splitCritterCode[EYES_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0) Level.LEVEL_4
            else if (splitCritterCode[ARMS_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0) Level.LEVEL_3
            else if (splitCritterCode[LEGS_CODE_INDEX_IN_CRITTER_CODE].toInt() != 0) Level.LEVEL_2
            else Level.LEVEL_1
        }

        fun getFlowerDrawableResource(critterCode: String): Int{
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            val flowerResourceIndexWanted = splitCritterCode[FLOWERS_CODE_INDEX_IN_CRITTER_CODE].toInt()
            return getDrawableInArrayDefaultToFirstItem(flowerResourceIndexWanted, FLOWER_PARTS)
        }

        fun getLegsDrawableResource(critterCode: String): Int{
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            val legsResourceIndexWanted = splitCritterCode[LEGS_CODE_INDEX_IN_CRITTER_CODE].toInt()
            return getDrawableInArrayDefaultToFirstItem(legsResourceIndexWanted, LEGS_PARTS)
        }

        fun getArmsDrawableResource(critterCode: String): Int{
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            val armsResourceIndexWanted = splitCritterCode[ARMS_CODE_INDEX_IN_CRITTER_CODE].toInt()
            return getDrawableInArrayDefaultToFirstItem(armsResourceIndexWanted, ARMS_PARTS)
        }

        fun getMouthDrawableResource(critterCode: String): Int{
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            val mouthResourceIndexWanted = splitCritterCode[MOUTH_CODE_INDEX_IN_CRITTER_CODE].toInt()
            return getDrawableInArrayDefaultToFirstItem(mouthResourceIndexWanted, MOUTH_PARTS)
        }

        fun getEyesDrawableResource(critterCode: String): Int{
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            val eyesResourceIndexWanted = splitCritterCode[EYES_CODE_INDEX_IN_CRITTER_CODE].toInt()
            return getDrawableInArrayDefaultToFirstItem(eyesResourceIndexWanted, EYES_PARTS)
        }

        fun getHornsDrawableResource(critterCode: String): Int{
            val splitCritterCode = critterCode.split(CRITTER_CODE_SEPARATOR)
            val hornsResourceIndexWanted = splitCritterCode[HORNS_CODE_INDEX_IN_CRITTER_CODE].toInt()
            return getDrawableInArrayDefaultToFirstItem(hornsResourceIndexWanted, HORNS_PARTS)
        }

        fun getDrawableInArrayDefaultToFirstItem(index: Int, resourcesArray: Array<Int>): Int{
            //safety check in case code stored in DB is not correlated with available drawable resources anymore :
            return if (index > resourcesArray.size - 1) {
                logE("LOGGING::CRITTER", "getDrawableInArrayDefaultToFirstItem::WANTED INDEX IS NOT AVAILABLE !! switching to first one")
                resourcesArray[0]
            } else {
                resourcesArray[index]
            }
        }
    }
}