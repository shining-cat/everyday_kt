package fr.shining_cat.everyday.testutils.model

import fr.shining_cat.everyday.model.Critter
import fr.shining_cat.everyday.model.RewardModel
import java.util.*

abstract class RewardModelTestUtils {
    companion object{
        val rewardModel_4_1_6_2_0_0 = RewardModel(
            id = 25,
            code = "4_1_6_2_0_0",
            level = Critter.Level.LEVEL_3,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")
    }
}