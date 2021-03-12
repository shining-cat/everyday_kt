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
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset

class SessionPresetConverter(
    private val logger: Logger
) {

    suspend fun convertModelsToEntities(sessionPresets: List<SessionPreset>): List<SessionPresetEntity> {
        return sessionPresets.map { sessionPreset -> convertModelToEntity(sessionPreset) }
    }

    suspend fun convertModelToEntity(sessionPreset: SessionPreset): SessionPresetEntity {
        return if (sessionPreset.id == -1L) {
            SessionPresetEntity(
                duration = sessionPreset.duration,
                startAndEndSoundUri = sessionPreset.startAndEndSoundUri,
                intermediateIntervalLength = sessionPreset.intermediateIntervalLength,
                startCountdownLength = sessionPreset.startCountdownLength,
                intermediateIntervalRandom = sessionPreset.intermediateIntervalRandom,
                intermediateIntervalSoundUri = sessionPreset.intermediateIntervalSoundUri,
                audioGuideSoundUri = sessionPreset.audioGuideSoundUri,
                vibration = sessionPreset.vibration,
                lastEditTime = sessionPreset.lastEditTime,
                sessionTypeId = sessionPreset.sessionTypeId
            )
        }
        else {
            SessionPresetEntity(
                id = sessionPreset.id,
                duration = sessionPreset.duration,
                startAndEndSoundUri = sessionPreset.startAndEndSoundUri,
                intermediateIntervalLength = sessionPreset.intermediateIntervalLength,
                startCountdownLength = sessionPreset.startCountdownLength,
                intermediateIntervalRandom = sessionPreset.intermediateIntervalRandom,
                intermediateIntervalSoundUri = sessionPreset.intermediateIntervalSoundUri,
                audioGuideSoundUri = sessionPreset.audioGuideSoundUri,
                vibration = sessionPreset.vibration,
                lastEditTime = sessionPreset.lastEditTime,
                sessionTypeId = sessionPreset.sessionTypeId
            )
        }
    }

    suspend fun convertEntitiesToModels(sessionPresetEntities: List<SessionPresetEntity>): List<SessionPreset> {
        return sessionPresetEntities.map { sessionEntity -> convertEntitytoModel(sessionEntity) }
    }

    suspend fun convertEntitytoModel(sessionPresetEntity: SessionPresetEntity): SessionPreset {
        return SessionPreset(
            id = sessionPresetEntity.id ?: -1L,
            duration = sessionPresetEntity.duration,
            startAndEndSoundUri = sessionPresetEntity.startAndEndSoundUri,
            intermediateIntervalLength = sessionPresetEntity.intermediateIntervalLength,
            startCountdownLength = sessionPresetEntity.startCountdownLength,
            intermediateIntervalRandom = sessionPresetEntity.intermediateIntervalRandom,
            intermediateIntervalSoundUri = sessionPresetEntity.intermediateIntervalSoundUri,
            audioGuideSoundUri = sessionPresetEntity.audioGuideSoundUri,
            vibration = sessionPresetEntity.vibration,
            lastEditTime = sessionPresetEntity.lastEditTime,
            sessionTypeId = sessionPresetEntity.sessionTypeId
        )
    }
}