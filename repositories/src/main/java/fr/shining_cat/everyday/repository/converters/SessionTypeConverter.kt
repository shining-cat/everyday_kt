package fr.shining_cat.everyday.repository.converters

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType

class SessionTypeConverter(
    private val logger: Logger
) {

    suspend fun convertModelsToEntities(sessionTypes: List<SessionType>): List<SessionTypeEntity> {
        return sessionTypes.map { sessionType -> convertModelToEntity(sessionType) }
    }

    suspend fun convertModelToEntity(sessionType: SessionType): SessionTypeEntity {
        return SessionTypeEntity(
            id = sessionType.id,
            name = sessionType.name,
            description = sessionType.description,
            color = sessionType.color,
            lastEditTime = sessionType.lastEditTime
        )
    }

    suspend fun convertEntitiesToModels(sessionTypeEntities: List<SessionTypeEntity>): List<SessionType> {
        return sessionTypeEntities.map { sessionEntity -> convertEntitytoModel(sessionEntity) }
    }

    suspend fun convertEntitytoModel(sessionTypeEntity: SessionTypeEntity): SessionType {
        return SessionType(
            id = sessionTypeEntity.id,
            name = sessionTypeEntity.name,
            description = sessionTypeEntity.description,
            color = sessionTypeEntity.color,
            lastEditTime = sessionTypeEntity.lastEditTime
        )
    }
}