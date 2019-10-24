package fr.shining_cat.everyday.testutils.dto

import fr.shining_cat.everyday.localdata.dto.SessionDTO
import java.util.*

abstract class SessionDTOTestUtils {
    companion object{

        fun generateSessions(numberOfSessions:Int = 1):List<SessionDTO>{
            val returnList = mutableListOf<SessionDTO>()
            var yearStartInc = 1980
            var yearEndInc = 1981
            for(i in 0 until numberOfSessions){
                returnList.add(generateSessionDTO(
                    yearstart = yearStartInc,
                    yearend =  yearEndInc
                ))
                yearStartInc++
                yearEndInc++
            }
            return returnList
        }

        fun generateSessionDTO(
            desiredId:Long = -1L,
            yearstart: Int = 1980,
            monthstart: Int = 5,
            dayOfMonthstart: Int = 2,
            hourOfDaystart: Int = 15,
            minutestart: Int = 27,
            secondstart: Int = 54,
            startBodyValue:Int = -2,
            startThoughtsValue:Int = 0,
            startFeelingsValue:Int = 1,
            startGlobalValue:Int = 2,
            yearend: Int = 1982,
            monthend: Int = 6,
            dayOfMonthend: Int = 3,
            hourOfDayend: Int = 17,
            minuteend: Int = 45,
            secondend: Int = 3,
            endBodyValue:Int = 0,
            endThoughtsValue:Int = 1,
            endFeelingsValue:Int = -1,
            endGlobalValue:Int = -2,
            notes:String = "generateSessionDTO default notes",
            realDuration:Long = 1590000,
            pausesCount:Int = 7,
            realDurationVsPlanned:Int = 0,
            guideMp3:String = "generateSessionDTO default guideMp3"
        ):SessionDTO{
            if(desiredId==-1L) {
                return SessionDTO(
                    startTimeOfRecord = GregorianCalendar(yearstart, monthstart, dayOfMonthstart, hourOfDaystart, minutestart, secondstart).timeInMillis,
                    startBodyValue = startBodyValue,
                    startThoughtsValue = startThoughtsValue,
                    startFeelingsValue = startFeelingsValue,
                    startGlobalValue = startGlobalValue,

                    endTimeOfRecord = GregorianCalendar(yearend, monthend, dayOfMonthend, hourOfDayend, minuteend, secondend).timeInMillis,
                    endBodyValue = endBodyValue,
                    endThoughtsValue = endThoughtsValue,
                    endFeelingsValue = endFeelingsValue,
                    endGlobalValue = endGlobalValue,

                    notes = notes,
                    realDuration = realDuration,
                    pausesCount = pausesCount,
                    realDurationVsPlanned = realDurationVsPlanned,
                    guideMp3 = guideMp3
                )
            }else{
                return SessionDTO(
                    id = desiredId,
                    startTimeOfRecord = GregorianCalendar(yearstart, monthstart, dayOfMonthstart, hourOfDaystart, minutestart, secondstart).timeInMillis,
                    startBodyValue = startBodyValue,
                    startThoughtsValue = startThoughtsValue,
                    startFeelingsValue = startFeelingsValue,
                    startGlobalValue = startGlobalValue,

                    endTimeOfRecord = GregorianCalendar(yearend, monthend, dayOfMonthend, hourOfDayend, minuteend, secondend).timeInMillis,
                    endBodyValue = endBodyValue,
                    endThoughtsValue = endThoughtsValue,
                    endFeelingsValue = endFeelingsValue,
                    endGlobalValue = endGlobalValue,

                    notes = notes,
                    realDuration = realDuration,
                    pausesCount = pausesCount,
                    realDurationVsPlanned = realDurationVsPlanned,
                    guideMp3 = guideMp3
                )
            }
        }
    }
}