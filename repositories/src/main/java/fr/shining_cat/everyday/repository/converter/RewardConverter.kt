package fr.shining_cat.everyday.repository.converter

import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants
import fr.shining_cat.everyday.models.Level
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.models.RewardModelConstants
import fr.shining_cat.everyday.models.critter.*

class RewardConverter {

    fun convertModelsToEntities(rewards: List<Reward>): List<RewardEntity> {
        return rewards.map { rewardModel -> convertModelToEntity(rewardModel) }
    }

    fun convertModelToEntity(reward: Reward): RewardEntity {
        val level = reward.level.key
        val rewardEntity = RewardEntity(
            flower = reward.flower.key,
            mouth = reward.mouth.key,
            legs = reward.legs.key,
            arms = reward.arms.key,
            eyes = reward.eyes.key,
            horns = reward.horns.key,
            level = level
        )
        if (reward.id != -1L) {
            rewardEntity.id = reward.id
        }
        rewardEntity.acquisitionDate =
            if (reward.acquisitionDate == RewardModelConstants.NO_ACQUISITION_DATE) RewardEntityConstants.NO_ACQUISITION_DATE else reward.acquisitionDate
        rewardEntity.escapingDate =
            if (reward.escapingDate == RewardModelConstants.NO_ESCAPING_DATE) RewardEntityConstants.NO_ESCAPING_DATE else reward.escapingDate
        rewardEntity.name =
            if (reward.name == RewardModelConstants.NO_NAME) RewardEntityConstants.NO_NAME else reward.name
        rewardEntity.isActive = reward.isActive
        rewardEntity.isEscaped = reward.isEscaped
        rewardEntity.legsColor = reward.legsColor
        rewardEntity.bodyColor = reward.bodyColor
        rewardEntity.armsColor = reward.armsColor
        return rewardEntity
    }

    fun convertEntitiesToModels(rewardEntities: List<RewardEntity>): List<Reward> {
        return rewardEntities.map { convertEntitytoModel(it) }
    }


    fun convertEntitytoModel(rewardEntity: RewardEntity): Reward {
        val rewardModel = Reward(
            id = rewardEntity.id,
            flower = FlowerResourcesHolder.FlowerDrawable.fromKey(rewardEntity.flower),
            mouth = MouthResourcesHolder.MouthDrawable.fromKey(rewardEntity.mouth),
            legs = LegsResourcesHolder.LegsDrawable.fromKey(rewardEntity.legs),
            arms = ArmsResourcesHolder.ArmsDrawable.fromKey(rewardEntity.arms),
            eyes = EyesResourcesHolder.EyesDrawable.fromKey(rewardEntity.eyes),
            horns = HornsResourcesHolder.HornsDrawable.fromKey(rewardEntity.horns),
            level = Level.fromKey(rewardEntity.level)
        )
        rewardModel.acquisitionDate =
            if (rewardEntity.acquisitionDate == RewardEntityConstants.NO_ACQUISITION_DATE) RewardModelConstants.NO_ACQUISITION_DATE else rewardEntity.acquisitionDate
        rewardModel.escapingDate =
            if (rewardEntity.escapingDate == RewardEntityConstants.NO_ESCAPING_DATE) RewardModelConstants.NO_ESCAPING_DATE else rewardEntity.escapingDate
        rewardModel.name =
            if (rewardEntity.name == RewardEntityConstants.NO_NAME) RewardModelConstants.NO_NAME else rewardEntity.name
        rewardModel.isActive = rewardEntity.isActive
        rewardModel.isEscaped = rewardEntity.isEscaped
        rewardModel.legsColor = rewardEntity.legsColor
        rewardModel.bodyColor = rewardEntity.bodyColor
        rewardModel.armsColor = rewardEntity.armsColor
        return rewardModel
    }
}