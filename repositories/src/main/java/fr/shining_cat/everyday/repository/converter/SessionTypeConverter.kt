package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType

class SessionTypeConverter(
    private val logger: Logger
) {

    fun convertModelsToEntities(sessionTypes: List<SessionType>): List<SessionTypeEntity> {
        return sessionTypes.map { sessionType -> convertModelToEntity(sessionType) }
    }

    fun convertModelToEntity(sessionType: SessionType): SessionTypeEntity {
        return SessionTypeEntity(
            id = sessionType.id,
            name = sessionType.name,
            description = sessionType.description,
            color = sessionType.color,
            lastEditTime = sessionType.lastEditTime
        )
    }

    fun convertEntitiesToModels(sessionTypeEntities: List<SessionTypeEntity>): List<SessionType> {
        return sessionTypeEntities.map { sessionEntity -> convertEntitytoModel(sessionEntity) }
    }

    fun convertEntitytoModel(sessionTypeEntity: SessionTypeEntity): SessionType {
        return SessionType(
            id = sessionTypeEntity.id,
            name = sessionTypeEntity.name,
            description = sessionTypeEntity.description,
            color = sessionTypeEntity.color,
            lastEditTime = sessionTypeEntity.lastEditTime
        )
    }
}