package fr.shining_cat.everyday.repository.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.localdata.dto.SessionDTO
import fr.shining_cat.everyday.model.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SessionConverter {
    companion object{

        fun convertModelsToDTOs(sessionModels: List<SessionModel>): List<SessionDTO> {
            return sessionModels.map {sessionModel ->  convertModelToDTO(sessionModel)}
        }

        fun convertModelToDTO(sessionModel: SessionModel): SessionDTO {
            val startMoodRecord = sessionModel.startMood
            val endMoodRecord = sessionModel.endMood
            val realDurationVsPlanned  = when (sessionModel.realDurationVsPlanned) {
                RealDurationVsPlanned.EQUAL -> 0
                RealDurationVsPlanned.REAL_SHORTER -> -1
                RealDurationVsPlanned.REAL_LONGER -> 1
            }

            return SessionDTO(
                id = sessionModel.id,
                startTimeOfRecord = startMoodRecord.timeOfRecord,
                startBodyValue = startMoodRecord.bodyValue,
                startThoughtsValue = startMoodRecord.thoughtsValue,
                startFeelingsValue = startMoodRecord.feelingsValue,
                startGlobalValue = startMoodRecord.globalValue,
                //
                endTimeOfRecord = endMoodRecord.timeOfRecord,
                endBodyValue = endMoodRecord.bodyValue,
                endThoughtsValue = endMoodRecord.thoughtsValue,
                endFeelingsValue = endMoodRecord.feelingsValue,
                endGlobalValue = endMoodRecord.globalValue,
                //
                notes = sessionModel.notes,
                realDuration = sessionModel.realDuration,
                pausesCount = sessionModel.pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = sessionModel.guideMp3
            )
        }

        fun convertDTOsToModels(sessionDTOs: LiveData<List<SessionDTO>>): LiveData<List<SessionModel>> {
            return Transformations.map(sessionDTOs){it.map { sessionDTO ->  convertDTOtoModel(sessionDTO)}}
        }
        fun convertDTOsToModels(sessionDTOs: List<SessionDTO>): List<SessionModel> {
            return sessionDTOs.map{ sessionDTO ->  convertDTOtoModel(sessionDTO)}
        }

        fun convertDTOtoModel(sessionDTO: SessionDTO): SessionModel{
            val startMoodRecord = Mood(
                timeOfRecord = sessionDTO.startTimeOfRecord,
                bodyValue = sessionDTO.startBodyValue,
                thoughtsValue = sessionDTO.startThoughtsValue,
                feelingsValue = sessionDTO.startFeelingsValue,
                globalValue = sessionDTO.startGlobalValue
            )
            val endMoodRecord = Mood(
                timeOfRecord = sessionDTO.endTimeOfRecord,
                bodyValue = sessionDTO.endBodyValue,
                thoughtsValue = sessionDTO.endThoughtsValue,
                feelingsValue = sessionDTO.endFeelingsValue,
                globalValue = sessionDTO.endGlobalValue
            )
            val realDurationVsPlanned  = when{
                sessionDTO.realDurationVsPlanned < 0 -> RealDurationVsPlanned.REAL_SHORTER
                sessionDTO.realDurationVsPlanned > 0 -> RealDurationVsPlanned.REAL_LONGER
                else -> RealDurationVsPlanned.EQUAL
            }
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

        fun convertModelToStringArray(sessionModel: SessionModel): Array<String> {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            //0 is for NOT SET so export it as such
            val startMoodRecord = sessionModel.startMood
            val startBodyValue = if (startMoodRecord.bodyValue == MoodConstants.NO_VALUE_SET) "NOT SET" else startMoodRecord.bodyValue.toString()
            val startThoughtsValue = if (startMoodRecord.thoughtsValue == MoodConstants.NO_VALUE_SET) "NOT SET" else startMoodRecord.thoughtsValue.toString()
            val startFeelingsValue = if (startMoodRecord.feelingsValue== MoodConstants.NO_VALUE_SET) "NOT SET" else startMoodRecord.feelingsValue.toString()
            val startGlobalValue = if (startMoodRecord.globalValue == MoodConstants.NO_VALUE_SET) "NOT SET" else startMoodRecord.globalValue.toString()
            //
            val endMoodRecord = sessionModel.endMood
            val endBodyValue = if (endMoodRecord.bodyValue == MoodConstants.NO_VALUE_SET) "NOT SET" else endMoodRecord.bodyValue.toString()
            val endThoughtsValue = if (endMoodRecord.thoughtsValue == MoodConstants.NO_VALUE_SET) "NOT SET" else endMoodRecord.thoughtsValue.toString()
            val endFeelingsValue = if (endMoodRecord.feelingsValue == MoodConstants.NO_VALUE_SET) "NOT SET" else endMoodRecord.feelingsValue.toString()
            val endGlobalValue = if (endMoodRecord.globalValue == MoodConstants.NO_VALUE_SET) "NOT SET" else endMoodRecord.globalValue.toString()
            //
            return arrayOf(
                sdf.format(startMoodRecord.timeOfRecord),
                sdf.format(endMoodRecord.timeOfRecord),
                (TimeUnit.MILLISECONDS.toMinutes(sessionModel.realDuration)).toString(),
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
