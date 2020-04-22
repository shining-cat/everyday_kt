package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionPresetEntity
import fr.shining_cat.everyday.models.SessionPreset

class SessionPresetConverter(
    private val logger: Logger
) {

    fun convertModelsToEntities(sessionPresets: List<SessionPreset>): List<SessionPresetEntity> {
        return sessionPresets.map { sessionPreset -> convertModelToEntity(sessionPreset) }
    }

    fun convertModelToEntity(sessionPreset: SessionPreset): SessionPresetEntity {
        return SessionPresetEntity(
            id = sessionPreset.id,
            duration = sessionPreset.duration,
            startAndEndSoundUri = sessionPreset.startAndEndSoundUri,
            intermediateIntervalLength = sessionPreset.intermediateIntervalLength,
            startCountdownLength = sessionPreset.startCountdownLength,
            intermediateIntervalRandom = sessionPreset.intermediateIntervalRandom,
            intermediateIntervalSoundUri = sessionPreset.intermediateIntervalSoundUri,
            audioGuideSoundUri = sessionPreset.audioGuideSoundUri,
            vibration = sessionPreset.vibration,
            lastEditTime = sessionPreset.lastEditDate
        )
    }

    fun convertEntitiesToModels(sessionPresetEntities: List<SessionPresetEntity>): List<SessionPreset> {
        return sessionPresetEntities.map { sessionEntity -> convertEntitytoModel(sessionEntity) }
    }

    fun convertEntitytoModel(sessionPresetEntity: SessionPresetEntity): SessionPreset {
        return SessionPreset(
            id = sessionPresetEntity.id,
            duration = sessionPresetEntity.duration,
            startAndEndSoundUri = sessionPresetEntity.startAndEndSoundUri,
            intermediateIntervalLength = sessionPresetEntity.intermediateIntervalLength,
            startCountdownLength = sessionPresetEntity.startCountdownLength,
            intermediateIntervalRandom = sessionPresetEntity.intermediateIntervalRandom,
            intermediateIntervalSoundUri = sessionPresetEntity.intermediateIntervalSoundUri,
            audioGuideSoundUri = sessionPresetEntity.audioGuideSoundUri,
            vibration = sessionPresetEntity.vibration,
            lastEditDate = sessionPresetEntity.lastEditTime
        )
    }
}