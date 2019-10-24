package fr.shining_cat.everyday.testutils.model

import fr.shining_cat.everyday.model.Mood
import fr.shining_cat.everyday.model.MoodValue
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertIntToMoodValue
import java.util.*

abstract class MoodModelTestUtils {
    companion object{
        fun generateMood(
            year: Int,
            month: Int,
            dayOfMonth: Int,
            hourOfDay: Int,
            minute: Int = 0,
            second: Int = 0,
            bodyValue: Int = 0,
            thoughtsValue: Int = 2,
            feelingsValue: Int = -1,
            globalValue: Int = 1
        ):Mood{
            return Mood(
                timeOfRecord = GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second).timeInMillis,
                bodyValue = convertIntToMoodValue(bodyValue),
                thoughtsValue = convertIntToMoodValue(thoughtsValue),
                feelingsValue = convertIntToMoodValue(feelingsValue),
                globalValue = convertIntToMoodValue(globalValue)
            )
        }

        val moodModelTestAStart = Mood(
            timeOfRecord = GregorianCalendar(2018, 5, 12, 7, 30, 10).timeInMillis,
            bodyValue = MoodValue.GOOD,
            thoughtsValue = MoodValue.WORST,
            feelingsValue = MoodValue.BAD,
            globalValue = MoodValue.NOT_SET
        )

        val moodModelTestAEnd = Mood(
            timeOfRecord = GregorianCalendar(2018, 5, 12, 7, 45, 30).timeInMillis,
            bodyValue = MoodValue.BEST,
            thoughtsValue = MoodValue.WORST,
            feelingsValue = MoodValue.BAD,
            globalValue = MoodValue.NOT_SET
        )
    }
}