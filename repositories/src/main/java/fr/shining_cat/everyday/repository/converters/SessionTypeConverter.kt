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
import fr.shining_cat.everyday.locale.entities.SessionTypeEntity
import fr.shining_cat.everyday.models.SessionType

class SessionTypeConverter(
    private val logger: Logger
) {

    suspend fun convertModelsToEntities(sessionTypes: List<SessionType>): List<SessionTypeEntity> {
        return sessionTypes.map {sessionType -> convertModelToEntity(sessionType)}
    }

    suspend fun convertModelToEntity(sessionType: SessionType): SessionTypeEntity {
        return SessionTypeEntity(
            id = if (sessionType.id != -1L) sessionType.id else null,
            name = sessionType.name,
            description = sessionType.description,
            color = sessionType.color,
            lastEditTime = sessionType.lastEditTime
        )
    }

    suspend fun convertEntitiesToModels(sessionTypeEntities: List<SessionTypeEntity>): List<SessionType> {
        return sessionTypeEntities.map {sessionEntity -> convertEntitytoModel(sessionEntity)}
    }

    suspend fun convertEntitytoModel(sessionTypeEntity: SessionTypeEntity): SessionType {
        return SessionType(
            id = sessionTypeEntity.id ?: -1L,
            name = sessionTypeEntity.name,
            description = sessionTypeEntity.description,
            color = sessionTypeEntity.color,
            lastEditTime = sessionTypeEntity.lastEditTime
        )
    }
}