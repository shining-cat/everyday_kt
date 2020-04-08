package fr.shining_cat.everyday.repository.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.locale.entities.RewardEntityConstants
import fr.shining_cat.everyday.models.CritterHelper
import fr.shining_cat.everyday.models.CritterLevel
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.models.RewardModelConstants

class RewardConverter(
    private val critterHelper: CritterHelper
) {

        fun convertModelsToEntities(rewards: List<Reward>): List<RewardEntity> {
            return rewards.map { rewardModel ->  convertModelToEntity(rewardModel)}
        }

        fun convertModelToEntity(reward: Reward): RewardEntity {
            //TODO: use CritterLevel key param when implemented
            val level  = reward.critterLevel.key
            val rewardEntity = RewardEntity(
                code = reward.code,
                level = level
            )
            if(reward.id != -1L){
                rewardEntity.id = reward.id
            }
            rewardEntity.acquisitionDate = if (reward.acquisitionDate == RewardModelConstants.NO_ACQUISITION_DATE) RewardEntityConstants.NO_ACQUISITION_DATE else reward.acquisitionDate
            rewardEntity.escapingDate = if (reward.escapingDate == RewardModelConstants.NO_ESCAPING_DATE) RewardEntityConstants.NO_ESCAPING_DATE else reward.escapingDate
            rewardEntity.name =  if (reward.name == RewardModelConstants.NO_NAME) RewardEntityConstants.NO_NAME else reward.name
            rewardEntity.isActive = reward.isActive
            rewardEntity.isEscaped = reward.isEscaped
            rewardEntity.legsColor = reward.legsColor
            rewardEntity.bodyColor = reward.bodyColor
            rewardEntity.armsColor = reward.armsColor
            return rewardEntity
        }

        fun convertEntitiesToModels(rewardEntitiesLiveData: LiveData<List<RewardEntity>?>): LiveData<List<Reward>> {
            return Transformations.map(rewardEntitiesLiveData) {
                if(it == null || it.isEmpty()) {
                    emptyList()
                } else {
                    it.map { rewardEntity -> convertEntitytoModel(rewardEntity)}
                }
            }
        }

        fun convertEntitytoModel(rewardEntityLiveData: LiveData<RewardEntity?>): LiveData<Reward?> {
            return Transformations.map(rewardEntityLiveData) {
                if (it == null) {
                    null
                } else {
                    convertEntitytoModel(it)
                }
            }
        }

        fun convertEntitytoModel(rewardEntity: RewardEntity): Reward{
            val rewardCode = rewardEntity.code
            //TODO: use CritterLevel key param when implemented
            val level  = when (rewardEntity.level){
                5 -> CritterLevel.LEVEL_5
                4 -> CritterLevel.LEVEL_4
                3 -> CritterLevel.LEVEL_3
                2 -> CritterLevel.LEVEL_2
                1 -> CritterLevel.LEVEL_1
                else -> critterHelper.getLevel(rewardCode)
            }
            val rewardModel = Reward(id= rewardEntity.id, code = rewardEntity.code, critterLevel = level)
            rewardModel.acquisitionDate = if (rewardEntity.acquisitionDate == RewardEntityConstants.NO_ACQUISITION_DATE) RewardModelConstants.NO_ACQUISITION_DATE else rewardEntity.acquisitionDate
            rewardModel.escapingDate = if (rewardEntity.escapingDate == RewardEntityConstants.NO_ESCAPING_DATE) RewardModelConstants.NO_ESCAPING_DATE else rewardEntity.escapingDate
            rewardModel.name = if (rewardEntity.name == RewardEntityConstants.NO_NAME) RewardModelConstants.NO_NAME else rewardEntity.name
            rewardModel.isActive = rewardEntity.isActive
            rewardModel.isEscaped = rewardEntity.isEscaped
            rewardModel.legsColor = rewardEntity.legsColor
            rewardModel.bodyColor = rewardEntity.bodyColor
            rewardModel.armsColor = rewardEntity.armsColor
            return rewardModel
        }
}