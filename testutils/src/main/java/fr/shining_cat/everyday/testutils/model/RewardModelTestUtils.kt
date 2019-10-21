package fr.shining_cat.everyday.testutils.model

import fr.shining_cat.everyday.model.Critter
import fr.shining_cat.everyday.model.RewardModel
import java.util.*

abstract class RewardModelTestUtils {
    companion object{
        fun generateReward(desiredLevel: Int = 1,
                           active:Boolean = true,
                           escaped:Boolean = false,
                           desiredId: Long = -1,
                           desiredCode: String = Critter.NO_CODE,
                           yearAcquired: Int = 2000,
                           monthAcquired: Int = 5,
                           dayAcquired: Int = 13,
                           yearEscaped: Int = 2001,
                           monthEscaped: Int = 6,
                           dayEscaped: Int = 25,
                           desiredName: String = "this is my name",
                           desiredLegsColor: String = "#FF000000",
                           desiredBodyColor: String = "#00FF0000",
                           desiredArmsColor: String = "#0000FF00"
                            ):RewardModel{
            val critterLevel  = when (desiredLevel){
                5 -> Critter.Level.LEVEL_5
                4 -> Critter.Level.LEVEL_4
                3 -> Critter.Level.LEVEL_3
                2 -> Critter.Level.LEVEL_2
                else -> Critter.Level.LEVEL_1
            }
            val critterCode = if(desiredCode == Critter.NO_CODE) Critter.getRandomCritterCode(critterLevel) else  desiredCode
            if(desiredId == -1L) {
                return RewardModel(
                    code = critterCode,
                    level = critterLevel,
                    acquisitionDate = GregorianCalendar(yearAcquired, monthAcquired, dayAcquired).timeInMillis,
                    escapingDate = GregorianCalendar(yearEscaped, monthEscaped, dayEscaped).timeInMillis,
                    isActive = active,
                    isEscaped = escaped,
                    name = desiredName,
                    legsColor = desiredLegsColor,
                    bodyColor = desiredBodyColor,
                    armsColor = desiredArmsColor
                )
            }else {
                return RewardModel(
                    id = desiredId,
                    code = critterCode,
                    level = critterLevel,
                    acquisitionDate = GregorianCalendar(yearAcquired, monthAcquired, dayAcquired).timeInMillis,
                    escapingDate = GregorianCalendar(yearEscaped, monthEscaped, dayEscaped).timeInMillis,
                    isActive = active,
                    isEscaped = escaped,
                    name = desiredName,
                    legsColor = desiredLegsColor,
                    bodyColor = desiredBodyColor,
                    armsColor = desiredArmsColor
                )
            }
        }

        fun generateRewards(numberOfRewardsDto: Int = 1, desiredLevel: Int = 1, active:Boolean = true, escaped:Boolean = false):List<RewardModel>{
            val returnList = mutableListOf<RewardModel>()
            for(i in 0 until numberOfRewardsDto){
                returnList.add(generateReward(desiredLevel, active, escaped))
            }
            return returnList
        }
    }
}