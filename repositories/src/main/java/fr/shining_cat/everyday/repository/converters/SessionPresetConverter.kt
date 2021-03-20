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
        return sessionPresets.map {sessionPreset -> convertModelToEntity(sessionPreset)}
    }

    suspend fun convertModelToEntity(sessionPreset: SessionPreset): SessionPresetEntity {
        return if (sessionPreset.id == -1L) {
            SessionPresetEntity(
                startCountdownLength = sessionPreset.startCountdownLength,
                startAndEndSoundUri = sessionPreset.startAndEndSoundUriString,
                startAndEndSoundName = sessionPreset.startAndEndSoundName,
                intermediateIntervalLength = sessionPreset.intermediateIntervalLength,
                intermediateIntervalRandom = sessionPreset.intermediateIntervalRandom,
                intermediateIntervalSoundUri = sessionPreset.intermediateIntervalSoundUriString,
                intermediateIntervalSoundName = sessionPreset.intermediateIntervalSoundName,
                duration = sessionPreset.duration,
                audioGuideSoundUri = sessionPreset.audioGuideSoundUriString,
                audioGuideSoundArtistName = sessionPreset.audioGuideSoundArtistName,
                audioGuideSoundAlbumName = sessionPreset.audioGuideSoundAlbumName,
                audioGuideSoundTitle = sessionPreset.audioGuideSoundTitle,
                vibration = sessionPreset.vibration,
                sessionTypeId = sessionPreset.sessionTypeId,
                lastEditTime = sessionPreset.lastEditTime,
            )
        }
        else {
            SessionPresetEntity(
                id = sessionPreset.id,
                startCountdownLength = sessionPreset.startCountdownLength,
                startAndEndSoundUri = sessionPreset.startAndEndSoundUriString,
                startAndEndSoundName = sessionPreset.startAndEndSoundName,
                intermediateIntervalLength = sessionPreset.intermediateIntervalLength,
                intermediateIntervalRandom = sessionPreset.intermediateIntervalRandom,
                intermediateIntervalSoundUri = sessionPreset.intermediateIntervalSoundUriString,
                intermediateIntervalSoundName = sessionPreset.intermediateIntervalSoundName,
                duration = sessionPreset.duration,
                audioGuideSoundUri = sessionPreset.audioGuideSoundUriString,
                audioGuideSoundArtistName = sessionPreset.audioGuideSoundArtistName,
                audioGuideSoundAlbumName = sessionPreset.audioGuideSoundAlbumName,
                audioGuideSoundTitle = sessionPreset.audioGuideSoundTitle,
                vibration = sessionPreset.vibration,
                sessionTypeId = sessionPreset.sessionTypeId,
                lastEditTime = sessionPreset.lastEditTime,
            )
        }
    }

    suspend fun convertEntitiesToModels(sessionPresetEntities: List<SessionPresetEntity>): List<SessionPreset> {
        return sessionPresetEntities.map {sessionEntity -> convertEntityToModel(sessionEntity)}
    }

    suspend fun convertEntityToModel(sessionPresetEntity: SessionPresetEntity): SessionPreset {
        return SessionPreset(
            id = sessionPresetEntity.id ?: -1L,
            startCountdownLength = sessionPresetEntity.startCountdownLength,
            startAndEndSoundUriString = sessionPresetEntity.startAndEndSoundUri,
            startAndEndSoundName = sessionPresetEntity.startAndEndSoundName,
            intermediateIntervalLength = sessionPresetEntity.intermediateIntervalLength,
            intermediateIntervalRandom = sessionPresetEntity.intermediateIntervalRandom,
            intermediateIntervalSoundUriString = sessionPresetEntity.intermediateIntervalSoundUri,
            intermediateIntervalSoundName = sessionPresetEntity.intermediateIntervalSoundName,
            duration = sessionPresetEntity.duration,
            audioGuideSoundUriString = sessionPresetEntity.audioGuideSoundUri,
            audioGuideSoundArtistName = sessionPresetEntity.audioGuideSoundArtistName,
            audioGuideSoundAlbumName = sessionPresetEntity.audioGuideSoundAlbumName,
            audioGuideSoundTitle = sessionPresetEntity.audioGuideSoundTitle,
            vibration = sessionPresetEntity.vibration,
            sessionTypeId = sessionPresetEntity.sessionTypeId,
            lastEditTime = sessionPresetEntity.lastEditTime,
        )
    }
}