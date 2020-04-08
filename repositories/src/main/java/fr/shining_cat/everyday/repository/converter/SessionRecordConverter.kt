package fr.shining_cat.everyday.repository.converter

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.Mood
import fr.shining_cat.everyday.models.MoodValue
import fr.shining_cat.everyday.models.RealDurationVsPlanned
import fr.shining_cat.everyday.models.SessionRecord


class SessionRecordConverter {

        fun convertModelsToEntities(sessionRecords: List<SessionRecord>): List<SessionRecordEntity> {
            return sessionRecords.map { sessionModel ->  convertModelToEntity(sessionModel)}
        }

        fun convertModelToEntity(sessionRecord: SessionRecord): SessionRecordEntity {
            val startMoodRecord = sessionRecord.startMood
            val endMoodRecord = sessionRecord.endMood
            val realDurationVsPlanned  = sessionRecord.realDurationVsPlanned.key

            return SessionRecordEntity(
                id = sessionRecord.id,
                startTimeOfRecord = startMoodRecord.timeOfRecord,
                startBodyValue = startMoodRecord.bodyValue.key,
                startThoughtsValue = startMoodRecord.thoughtsValue.key,
                startFeelingsValue = startMoodRecord.feelingsValue.key,
                startGlobalValue = startMoodRecord.globalValue.key,
                //
                endTimeOfRecord = endMoodRecord.timeOfRecord,
                endBodyValue = endMoodRecord.bodyValue.key,
                endThoughtsValue = endMoodRecord.thoughtsValue.key,
                endFeelingsValue = endMoodRecord.feelingsValue.key,
                endGlobalValue = endMoodRecord.globalValue.key,
                //
                notes = sessionRecord.notes,
                realDuration = sessionRecord.realDuration,
                pausesCount = sessionRecord.pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = sessionRecord.guideMp3
            )
        }
    
        fun convertEntitiesToModels(sessionEntities: LiveData<List<SessionRecordEntity>?>): LiveData<List<SessionRecord>> {
            return Transformations.map(sessionEntities){
                if(it == null || it.isEmpty()){
                    emptyList()
                }else{
                    it.map { sessionEntity ->  convertEntitytoModel(sessionEntity)}
                }
            }
        }

        fun convertEntitiesToModels(sessionRecordEntities: List<SessionRecordEntity>?): List<SessionRecord> {
            if(sessionRecordEntities == null || sessionRecordEntities.isEmpty()){
                return emptyList()
            }else {
                return sessionRecordEntities.map { sessionEntity -> convertEntitytoModel(sessionEntity) }
            }
        }

        fun convertEntitytoModel(sessionRecordEntity: SessionRecordEntity): SessionRecord{
            val startMoodRecord = Mood(
                timeOfRecord = sessionRecordEntity.startTimeOfRecord,
                bodyValue = MoodValue.fromKey(sessionRecordEntity.startBodyValue),
                thoughtsValue = MoodValue.fromKey(sessionRecordEntity.startThoughtsValue),
                feelingsValue = MoodValue.fromKey(sessionRecordEntity.startFeelingsValue),
                globalValue = MoodValue.fromKey(sessionRecordEntity.startGlobalValue)
            )
            val endMoodRecord = Mood(
                timeOfRecord = sessionRecordEntity.endTimeOfRecord,
                bodyValue = MoodValue.fromKey(sessionRecordEntity.endBodyValue),
                thoughtsValue = MoodValue.fromKey(sessionRecordEntity.endThoughtsValue),
                feelingsValue = MoodValue.fromKey(sessionRecordEntity.endFeelingsValue),
                globalValue = MoodValue.fromKey(sessionRecordEntity.endGlobalValue)
            )
            val realDurationVsPlanned  = RealDurationVsPlanned.fromKey(sessionRecordEntity.realDurationVsPlanned)

            return SessionRecord(
                id = sessionRecordEntity.id,
                startMood = startMoodRecord,
                endMood = endMoodRecord,
                notes = sessionRecordEntity.notes,
                realDuration = sessionRecordEntity.realDuration,
                pausesCount = sessionRecordEntity.pausesCount,
                realDurationVsPlanned = realDurationVsPlanned,
                guideMp3 = sessionRecordEntity.guideMp3
            )
        }

        fun convertModelToStringArray(sessionRecord: SessionRecord): Array<String> {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            //0 is for NOT SET so export it as such
            val startMoodRecord = sessionRecord.startMood
            val startBodyValue = startMoodRecord.bodyValue.name
            val startThoughtsValue = startMoodRecord.thoughtsValue.name
            val startFeelingsValue = startMoodRecord.feelingsValue.name
            val startGlobalValue = startMoodRecord.globalValue.name
            //
            val endMoodRecord = sessionRecord.endMood
            val endBodyValue = endMoodRecord.bodyValue.name
            val endThoughtsValue = endMoodRecord.thoughtsValue.name
            val endFeelingsValue = endMoodRecord.feelingsValue.name
            val endGlobalValue = endMoodRecord.globalValue.name
            //
            return arrayOf(
                sdf.format(startMoodRecord.timeOfRecord),
                sdf.format(endMoodRecord.timeOfRecord),
                (TimeUnit.MILLISECONDS.toMinutes(sessionRecord.realDuration)).toString() + "mn",
                sessionRecord.notes,
                startBodyValue,
                startThoughtsValue,
                startFeelingsValue,
                startGlobalValue,
                endBodyValue,
                endThoughtsValue,
                endFeelingsValue,
                endGlobalValue,
                sessionRecord.pausesCount.toString(),
                sessionRecord.realDurationVsPlanned.name,
                sessionRecord.guideMp3
            )
        }
}