package fr.shining_cat.everyday.testutils.dto

import fr.shining_cat.everyday.localdata.dto.RewardDTO
import java.util.*

abstract class RewardDTOTestUtils {
    companion object{
        val rewardDTO_4_1_6_2_0_0_WITH_ID = RewardDTO(
            id = 25,
            code = "4_1_6_2_0_0",
            level = 3,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_4_1_6_2_0_0_NO_ID = RewardDTO(
            code = "4_1_6_2_0_0",
            level = 3,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_2_0_0_ACTIVE = RewardDTO(
            code = "1_0_0_2_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_2_0_0_ESCAPED = RewardDTO(
            code = "1_0_0_2_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = true,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_5_0_0_0_0_ACTIVE = RewardDTO(
            code = "1_5_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_5_0_0_0_0_ESCAPED = RewardDTO(
            code = "1_5_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2018, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2019, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = true,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")
    }
}