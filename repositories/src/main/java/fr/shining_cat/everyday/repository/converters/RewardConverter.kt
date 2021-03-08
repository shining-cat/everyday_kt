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
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants
import fr.shining_cat.everyday.models.reward.Level
import fr.shining_cat.everyday.models.reward.Reward
import fr.shining_cat.everyday.models.reward.RewardConstants

class RewardConverter(
    private val logger: Logger
) {

    suspend fun convertModelsToEntities(rewards: List<Reward>): List<RewardEntity> {
        return rewards.map { rewardModel -> convertModelToEntity(rewardModel) }
    }

    suspend fun convertModelToEntity(reward: Reward): RewardEntity {
        val level = reward.level.key
        val rewardEntity = RewardEntity(
            flower = reward.flowerKey,
            mouth = reward.mouthKey,
            legs = reward.legsKey,
            arms = reward.armsKey,
            eyes = reward.eyesKey,
            horns = reward.hornsKey,
            level = level
        )
        if (reward.id != -1L) {
            rewardEntity.id = reward.id
        }
        rewardEntity.acquisitionDate =
            if (reward.acquisitionDate == RewardConstants.NO_ACQUISITION_DATE) RewardEntityConstants.NO_ACQUISITION_DATE else reward.acquisitionDate
        rewardEntity.escapingDate =
            if (reward.escapingDate == RewardConstants.NO_ESCAPING_DATE) RewardEntityConstants.NO_ESCAPING_DATE else reward.escapingDate
        rewardEntity.name = if (reward.name == RewardConstants.NO_NAME) RewardEntityConstants.NO_NAME else reward.name
        rewardEntity.isActive = reward.isActive
        rewardEntity.isEscaped = reward.isEscaped
        rewardEntity.legsColor = reward.legsColor
        rewardEntity.bodyColor = reward.bodyColor
        rewardEntity.armsColor = reward.armsColor
        return rewardEntity
    }

    suspend fun convertEntitiesToModels(rewardEntities: List<RewardEntity>): List<Reward> {
        return rewardEntities.map { convertEntitytoModel(it) }
    }

    suspend fun convertEntitytoModel(rewardEntity: RewardEntity): Reward {
        val acquisitionDate = if (rewardEntity.acquisitionDate == RewardEntityConstants.NO_ACQUISITION_DATE) {
            RewardConstants.NO_ACQUISITION_DATE
        }
        else {
            rewardEntity.acquisitionDate
        }
        val escapingDate = if (rewardEntity.escapingDate == RewardEntityConstants.NO_ESCAPING_DATE) {
            RewardConstants.NO_ESCAPING_DATE
        }
        else {
            rewardEntity.escapingDate
        }
        val rewardModel = Reward(
            id = rewardEntity.id,
            flowerKey = rewardEntity.flower,
            mouthKey = rewardEntity.mouth,
            legsKey = rewardEntity.legs,
            armsKey = rewardEntity.arms,
            eyesKey = rewardEntity.eyes,
            hornsKey = rewardEntity.horns,
            level = Level.fromKey(rewardEntity.level)
        )
        rewardModel.acquisitionDate = acquisitionDate
        rewardModel.escapingDate = escapingDate
        rewardModel.name = if (rewardEntity.name == RewardEntityConstants.NO_NAME) RewardConstants.NO_NAME else rewardEntity.name
        rewardModel.isActive = rewardEntity.isActive
        rewardModel.isEscaped = rewardEntity.isEscaped
        rewardModel.legsColor = rewardEntity.legsColor
        rewardModel.bodyColor = rewardEntity.bodyColor
        rewardModel.armsColor = rewardEntity.armsColor
        return rewardModel
    }
}