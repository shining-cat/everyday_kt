package fr.shining_cat.everyday.testutils.dto

import fr.shining_cat.everyday.localdata.dto.SessionDTO
import java.util.*

abstract class SessionDTOTestUtils {
    companion object{

        fun generateSessionDTO(
                                id:Long = -1L,
                                yearstart: Int,
                                monthstart: Int,
                                dayOfMonthstart: Int,
                                hourOfDaystart: Int,
                                minutestart: Int,
                                secondstart: Int,
                                startBodyValue:Int,
                                startThoughtsValue:Int,
                                startFeelingsValue:Int,
                                startGlobalValue:Int,
                                yearend: Int,
                                monthend: Int,
                                dayOfMonthend: Int,
                                hourOfDayend: Int,
                                minuteend: Int,
                                secondend: Int,
                                endBodyValue:Int,
                                endThoughtsValue:Int,
                                endFeelingsValue:Int,
                                endGlobalValue:Int,
                                notes:String,
                                realDuration:Long,
                                pausesCount:Int,
                                realDurationVsPlanned:Int,
                                guideMp3:String
        ):SessionDTO{
            if(id==-1L) {
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
                    id = id,
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