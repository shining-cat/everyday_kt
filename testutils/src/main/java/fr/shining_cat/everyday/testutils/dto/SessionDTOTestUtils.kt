package fr.shining_cat.everyday.testutils.dto

import fr.shining_cat.everyday.localdata.dto.SessionDTO
import java.util.*

abstract class SessionDTOTestUtils {
    companion object{
        val sessionDTOTestA = SessionDTO(
            id = 74,

            startTimeOfRecord = GregorianCalendar(2018, 5, 12, 7, 30, 10).timeInMillis,
            startBodyValue = 1,
            startThoughtsValue = -2,
            startFeelingsValue = -1,
            startGlobalValue = 0,

            endTimeOfRecord = GregorianCalendar(2018, 5, 12, 7, 45, 30).timeInMillis,
            endBodyValue = 2,
            endThoughtsValue = -2,
            endFeelingsValue = -1,
            endGlobalValue = 0,

            notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non neque fringilla mauris maximus porttitor. Sed nec elit quis neque imperdiet feugiat eu id nunc.",
            realDuration = 900000,
            pausesCount = 7,
            realDurationVsPlanned = -1,
            guideMp3 = "Super fichier audio.mp3"
        )
    }
}