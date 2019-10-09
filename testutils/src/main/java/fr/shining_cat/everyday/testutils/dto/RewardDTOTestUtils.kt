package fr.shining_cat.everyday.testutils.dto

import fr.shining_cat.everyday.localdata.dto.RewardDTO
import java.util.*

abstract class RewardDTOTestUtils {
    companion object{
        val rewardDTO_4_1_6_2_0_0_WITH_ID = RewardDTO(
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

        val rewardDTO_4_1_6_2_0_0_NO_ID = RewardDTO(
            code = "4_1_6_2_0_0",
            level = 3,
            acquisitionDate = GregorianCalendar(2002, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2003, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_2_0_0_ACTIVE_ID = RewardDTO(
            id = 35,
            code = "1_0_0_2_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2004, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2005, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_2_0_0_ACTIVE_NO_ID = RewardDTO(
            code = "1_0_0_2_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2006, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2007, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_2_0_0_ESCAPED_ID = RewardDTO(
            id = 45,
            code = "1_0_0_2_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2008, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2009, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = true,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_2_0_0_ESCAPED_NO_ID = RewardDTO(
            code = "1_0_0_2_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2010, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2011, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = true,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_5_0_0_0_0_ACTIVE_ID = RewardDTO(
            id = 55,
            code = "1_5_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2012, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2013, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_5_0_0_0_0_ACTIVE_NO_ID = RewardDTO(
            code = "1_5_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2014, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2015, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_5_0_0_0_0_ESCAPED_ID = RewardDTO(
            id = 65,
            code = "1_5_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2016, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2017, 3, 15).timeInMillis,
            isActive = true,
            isEscaped = true,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_5_0_0_0_0_ESCAPED_NO_ID = RewardDTO(
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

        val rewardDTO_2_3_1_3_0_0_INACTIVE_ID = RewardDTO(
            id = 75,
            code = "2_3_1_3_0_0",
            level = 3,
            acquisitionDate = GregorianCalendar(2019, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2020, 3, 15).timeInMillis,
            isActive = false,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_2_3_1_3_0_0_INACTIVE_NO_ID = RewardDTO(
            code = "2_3_1_3_0_0",
            level = 3,
            acquisitionDate = GregorianCalendar(2020, 5, 12).timeInMillis,
            escapingDate = GregorianCalendar(2021, 3, 15).timeInMillis,
            isActive = false,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_5_0_0_INACTIVE_ID = RewardDTO(
            id = 85,
            code = "1_0_0_5_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2022, 6, 12).timeInMillis,
            escapingDate = GregorianCalendar(2023, 3, 15).timeInMillis,
            isActive = false,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_1_0_0_5_0_0_INACTIVE_NO_ID = RewardDTO(
            code = "1_0_0_5_0_0",
            level = 1,
            acquisitionDate = GregorianCalendar(2024, 6, 12).timeInMillis,
            escapingDate = GregorianCalendar(2025, 3, 15).timeInMillis,
            isActive = false,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_4_2_0_0_0_0_INACTIVE_ID = RewardDTO(
            id = 95,
            code = "4_2_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2026, 8, 12).timeInMillis,
            escapingDate = GregorianCalendar(2027, 3, 15).timeInMillis,
            isActive = false,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")

        val rewardDTO_4_2_0_0_0_0_INACTIVE_NO_ID = RewardDTO(
            code = "4_2_0_0_0_0",
            level = 2,
            acquisitionDate = GregorianCalendar(2028, 8, 12).timeInMillis,
            escapingDate = GregorianCalendar(2029, 3, 15).timeInMillis,
            isActive = false,
            isEscaped = false,
            name = "this is my name",
            legsColor = "#FF000000",
            bodyColor = "#00FF0000",
            armsColor = "#0000FF00")
    }
}