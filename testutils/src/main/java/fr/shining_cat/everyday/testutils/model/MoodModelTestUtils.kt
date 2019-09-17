package fr.shining_cat.everyday.testutils.model

import fr.shining_cat.everyday.model.Mood
import fr.shining_cat.everyday.model.MoodValue
import java.util.*

abstract class MoodModelTestUtils {
    companion object{
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