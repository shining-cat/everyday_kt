package fr.shining_cat.everyday.repository.converter

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

import fr.shining_cat.everyday.locale.entities.SessionDTO
import fr.shining_cat.everyday.models.Mood
import fr.shining_cat.everyday.models.MoodValue
import fr.shining_cat.everyday.models.RealDurationVsPlanned
import fr.shining_cat.everyday.models.SessionModel


class SessionConverter {
    companion object{

        fun convertModelsToDTOs(sessionModels: List<SessionModel>): List<SessionDTO> {
            return sessionModels.map {sessionModel ->  convertModelToDTO(sessionModel)}
        }

        fun convertModelToDTO(sessionModel: SessionModel): SessionDTO {
            val startMoodRecord = sessionModel.startMood
            val endMoodRecord = sessionModel.endMood
            val realDurationVsPlanned  = convertRealDurationVsPlannedtoInt(sessionModel.realDurationVsPlanned)

            return SessionDTO(
                id = sessionModel.id,
                startTimeOfRecord = startMoodRecord.timeOfRecord,
                startBodyValue = convertMoodValueToInt(startMoodRecord.bodyValue),
                startThoughtsValue = convertMoodValueToInt(startMoodRecord.thoughtsValue),
                startFeelingsValue = convertMoodValueToInt(startMoodRecord.feelingsValue),
                startGlobalValue = convertMoodValueToInt(startMoodRecord.globalValue),
                //
                endTimeOfRecord = endMoodRecord.timeOfRecord,
                endBodyValue = convertMoodValueToInt(endMoodRecord.bodyValue),
                endThoughtsValue = convertMoodValueToInt(endMoodRecord.thoughtsValue),
                endFeelingsValue = convertMoodValueToInt(endMoodRecord.feelingsValue),
                endGlobalValue = convertMoodValueToInt(endMoodRecord.globalValue),
                //
                notes = sessionModel.notes,
                realDuration = sessionModel.realDuration,
                pausesCount = sessionModel.pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = sessionModel.guideMp3
            )
        }

        fun convertRealDurationVsPlannedtoInt(realDurationVsPlanned: RealDurationVsPlanned) = when(realDurationVsPlanned){
            RealDurationVsPlanned.EQUAL -> 0
            RealDurationVsPlanned.REAL_SHORTER -> -1
            RealDurationVsPlanned.REAL_LONGER -> 1
        }

        fun convertMoodValueToInt(moodValue: MoodValue) = when(moodValue){
            MoodValue.WORST -> -2
            MoodValue.BAD -> -1
            MoodValue.NOT_SET -> 0
            MoodValue.GOOD -> 1
            MoodValue.BEST -> 2
        }

        fun convertDTOsToModels(sessionDTOs: LiveData<List<SessionDTO>?>): LiveData<List<SessionModel>> {
            return Transformations.map(sessionDTOs){
                if(it == null || it.isEmpty()){
                    emptyList()
                }else{
                    it.map { sessionDTO ->  convertDTOtoModel(sessionDTO)}
                }
            }
        }

        fun convertDTOsToModels(sessionDTOs: List<SessionDTO>?): List<SessionModel> {
            if(sessionDTOs == null || sessionDTOs.isEmpty()){
                return emptyList()
            }else {
                return sessionDTOs.map { sessionDTO -> convertDTOtoModel(sessionDTO) }
            }
        }

        fun convertDTOtoModel(sessionDTO: SessionDTO): SessionModel{
            val startMoodRecord = Mood(
                timeOfRecord = sessionDTO.startTimeOfRecord,
                bodyValue = convertIntToMoodValue(sessionDTO.startBodyValue),
                thoughtsValue = convertIntToMoodValue(sessionDTO.startThoughtsValue),
                feelingsValue = convertIntToMoodValue(sessionDTO.startFeelingsValue),
                globalValue = convertIntToMoodValue(sessionDTO.startGlobalValue)
            )
            val endMoodRecord = Mood(
                timeOfRecord = sessionDTO.endTimeOfRecord,
                bodyValue = convertIntToMoodValue(sessionDTO.endBodyValue),
                thoughtsValue = convertIntToMoodValue(sessionDTO.endThoughtsValue),
                feelingsValue = convertIntToMoodValue(sessionDTO.endFeelingsValue),
                globalValue = convertIntToMoodValue(sessionDTO.endGlobalValue)
            )
            val realDurationVsPlanned  = convertIntToRealDurationVsPlanned(sessionDTO.realDurationVsPlanned)

            return SessionModel(
                id = sessionDTO.id,
                startMood = startMoodRecord,
                endMood = endMoodRecord,
                notes = sessionDTO.notes,
                realDuration = sessionDTO.realDuration,
                pausesCount = sessionDTO.pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = sessionDTO.guideMp3
            )
        }

        fun convertIntToRealDurationVsPlanned(realDurationVsPlannedAsInt: Int) = when{
            realDurationVsPlannedAsInt < 0 -> RealDurationVsPlanned.REAL_SHORTER
            realDurationVsPlannedAsInt > 0 -> RealDurationVsPlanned.REAL_LONGER
            else -> RealDurationVsPlanned.EQUAL
         }

        fun convertIntToMoodValue(moodValueAsInt: Int) = when(moodValueAsInt){
            -2 -> MoodValue.WORST
            -1 -> MoodValue.BAD
            1 -> MoodValue.GOOD
            2 -> MoodValue.BEST
            else -> MoodValue.NOT_SET
        }

        fun convertModelToStringArray(sessionModel: SessionModel): Array<String> {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            //0 is for NOT SET so export it as such
            val startMoodRecord = sessionModel.startMood
            val startBodyValue = startMoodRecord.bodyValue.name
            val startThoughtsValue = startMoodRecord.thoughtsValue.name
            val startFeelingsValue = startMoodRecord.feelingsValue.name
            val startGlobalValue = startMoodRecord.globalValue.name
            //
            val endMoodRecord = sessionModel.endMood
            val endBodyValue = endMoodRecord.bodyValue.name
            val endThoughtsValue = endMoodRecord.thoughtsValue.name
            val endFeelingsValue = endMoodRecord.feelingsValue.name
            val endGlobalValue = endMoodRecord.globalValue.name
            //
            return arrayOf(
                sdf.format(startMoodRecord.timeOfRecord),
                sdf.format(endMoodRecord.timeOfRecord),
                (TimeUnit.MILLISECONDS.toMinutes(sessionModel.realDuration)).toString() + "mn",
                sessionModel.notes,
                startBodyValue,
                startThoughtsValue,
                startFeelingsValue,
                startGlobalValue,
                endBodyValue,
                endThoughtsValue,
                endFeelingsValue,
                endGlobalValue,
                sessionModel.pausesCount.toString(),
                sessionModel.realDurationVsPlanned.name,
                sessionModel.guideMp3
            )
        }
    }
}
