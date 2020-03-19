package fr.shining_cat.everyday.testutils.model

import fr.shining_cat.everyday.models.Mood
import fr.shining_cat.everyday.models.SessionModel
import fr.shining_cat.everyday.repository.converter.SessionConverter.Companion.convertIntToRealDurationVsPlanned

abstract class SessionModelTestUtils {
    companion object{

        fun generateSessions(numberOfSessions:Int = 1):List<SessionModel>{
            val returnList = mutableListOf<SessionModel>()
            var yearStart = 1980
            var yearEnd = 1981
            for(i in 0 until numberOfSessions){
                returnList.add(generateSession(
                    startMood = MoodModelTestUtils.generateMood(yearStart, 5, 2, 15, 27, 54, -2, 0, 1, 2),
                    endMood =   MoodModelTestUtils.generateMood(yearEnd,6,3,17,45,3,0, 1, -1, -2)
                ))
                yearStart++
                yearEnd++
            }
            return returnList
        }

        fun generateSession(
            id: Long = -1L,
            startMood: Mood = MoodModelTestUtils.generateMood(1980, 5, 2, 15, 27, 54, -2, 0, 1, 2),
            endMood: Mood = MoodModelTestUtils.generateMood(1982,6,3,17,45,3,0, 1, -1, -2),
            notes: String = "generateSession default notes",
            realDuration: Long = 123456L,
            pausesCount: Int = 0,
            realDurationVsPlanned: Int = 0,
            guideMp3: String = "generateSession default guideMp3"
        ):SessionModel{
            if(id == -1L){
                return SessionModel(
                    startMood = startMood,
                    endMood = endMood,
                    notes = notes,
                    realDuration = realDuration,
                    pausesCount = pausesCount,
                    realDurationVsPlanned = convertIntToRealDurationVsPlanned(realDurationVsPlanned),
                    guideMp3 = guideMp3)
            }else{
                return SessionModel(
                    id = id,
                    startMood = startMood,
                    endMood = endMood,
                    notes = notes,
                    realDuration = realDuration,
                    pausesCount = pausesCount,
                    realDurationVsPlanned = convertIntToRealDurationVsPlanned(realDurationVsPlanned),
                    guideMp3 = guideMp3)
            }
        }
    }
}