/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.everyday.repository.converters

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionRecordEntity
import fr.shining_cat.everyday.models.sessionrecord.Mood
import fr.shining_cat.everyday.models.sessionrecord.MoodValue
import fr.shining_cat.everyday.models.sessionrecord.RealDurationVsPlanned
import fr.shining_cat.everyday.models.sessionrecord.SessionRecord
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class SessionRecordConverter(
    private val logger: Logger
) {

    suspend fun convertModelsToEntities(sessionRecords: List<SessionRecord>): List<SessionRecordEntity> {
        return sessionRecords.map { sessionRecord -> convertModelToEntity(sessionRecord) }
    }

    suspend fun convertModelToEntity(sessionRecord: SessionRecord): SessionRecordEntity {
        val startMoodRecord = sessionRecord.startMood
        val endMoodRecord = sessionRecord.endMood
        val realDurationVsPlanned = sessionRecord.realDurationVsPlanned.key

        return SessionRecordEntity(
            id = if (sessionRecord.id != -1L) sessionRecord.id else null,
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
            guideMp3 = sessionRecord.guideMp3,
            sessionTypeId = sessionRecord.sessionTypeId
        )
    }

    suspend fun convertEntitiesToModels(sessionRecordEntities: List<SessionRecordEntity>): List<SessionRecord> {
        return sessionRecordEntities.map { sessionEntity -> convertEntityToModel(sessionEntity) }
    }

    suspend fun convertEntityToModel(sessionRecordEntity: SessionRecordEntity): SessionRecord {
        val startMoodRecord = Mood(
            timeOfRecord = sessionRecordEntity.startTimeOfRecord,
            bodyValue = MoodValue.fromKey(
                sessionRecordEntity.startBodyValue
            ),
            thoughtsValue = MoodValue.fromKey(
                sessionRecordEntity.startThoughtsValue
            ),
            feelingsValue = MoodValue.fromKey(
                sessionRecordEntity.startFeelingsValue
            ),
            globalValue = MoodValue.fromKey(
                sessionRecordEntity.startGlobalValue
            )
        )
        val endMoodRecord = Mood(
            timeOfRecord = sessionRecordEntity.endTimeOfRecord,
            bodyValue = MoodValue.fromKey(
                sessionRecordEntity.endBodyValue
            ),
            thoughtsValue = MoodValue.fromKey(
                sessionRecordEntity.endThoughtsValue
            ),
            feelingsValue = MoodValue.fromKey(
                sessionRecordEntity.endFeelingsValue
            ),
            globalValue = MoodValue.fromKey(
                sessionRecordEntity.endGlobalValue
            )
        )
        val realDurationVsPlanned = RealDurationVsPlanned.fromKey(sessionRecordEntity.realDurationVsPlanned)

        return SessionRecord(
            id = sessionRecordEntity.id ?: -1L,
            startMood = startMoodRecord,
            endMood = endMoodRecord,
            notes = sessionRecordEntity.notes,
            realDuration = sessionRecordEntity.realDuration,
            pausesCount = sessionRecordEntity.pausesCount,
            realDurationVsPlanned = realDurationVsPlanned,
            guideMp3 = sessionRecordEntity.guideMp3,
            sessionTypeId = sessionRecordEntity.sessionTypeId
        )
    }

    // this is used for the csv export
    suspend fun convertModelToStringArray(sessionRecord: SessionRecord): Array<String> {
        val sdf = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        )
        // 0 is for NOT SET so export it as such
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