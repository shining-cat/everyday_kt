package fr.shining_cat.everyday.testutils.dto

import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.model.Critter
import java.util.*

abstract class RewardDTOTestUtils {
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
                           desiredArmsColor: String = "#0000FF00"):RewardDTO{
            val critterLevel  = when (desiredLevel){
                5 -> Critter.Level.LEVEL_5
                4 -> Critter.Level.LEVEL_4
                3 -> Critter.Level.LEVEL_3
                2 -> Critter.Level.LEVEL_2
                else -> Critter.Level.LEVEL_1
            }
            val critterCode = if(desiredCode == Critter.NO_CODE) Critter.getRandomCritterCode(critterLevel) else  desiredCode
            if(desiredId == -1L){
                return RewardDTO(
                    code = critterCode,
                    level = desiredLevel,
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
                return RewardDTO(
                    id = desiredId,
                    code = critterCode,
                    level = desiredLevel,
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

        fun generateRewards(numberOfRewardsDto: Int = 1, desiredLevel: Int = 1, active:Boolean = true, escaped:Boolean = false):List<RewardDTO>{
            val returnList = mutableListOf<RewardDTO>()
            for(i in 0 until numberOfRewardsDto){
                returnList.add(generateReward(desiredLevel, active, escaped))
            }
            return returnList
        }

        val rewardDTO_4_1_6_2_0_0_ACTIVE_NOT_ESCAPED_WITH_ID = RewardDTO(
            id = 25,
            code = "4_1_6_2_0_0",
            level = 3,
            acquisitionDate = GregorianCalendar(2000, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2001, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

    }
}