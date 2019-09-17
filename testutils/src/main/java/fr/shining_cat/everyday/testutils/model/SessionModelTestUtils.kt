package fr.shining_cat.everyday.testutils.model

import fr.shining_cat.everyday.model.Mood
import fr.shining_cat.everyday.model.RealDurationVsPlanned
import fr.shining_cat.everyday.model.SessionModel

abstract class SessionModelTestUtils {
    companion object{
        val sessionModelTestA = SessionModel(
            id = 74,
            startMood = MoodModelTestUtils.moodModelTestAStart,
            endMood = MoodModelTestUtils.moodModelTestAEnd,
            notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non neque fringilla mauris maximus porttitor. Sed nec elit quis neque imperdiet feugiat eu id nunc.",
            realDuration = 900000,
            pausesCount = 7,
            realDurationVsPlanned = RealDurationVsPlanned.REAL_SHORTER,
            guideMp3 = "Super fichier audio.mp3"
        )
    }
}