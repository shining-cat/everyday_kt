package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.localdata.dao.RewardDao
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.repository.converter.RewardConverter.Companion.convertDTOsToModels
import fr.shining_cat.everyday.repository.converter.RewardConverter.Companion.convertModelToDTO
import fr.shining_cat.everyday.repository.converter.RewardConverter.Companion.convertModelsToDTOs

class RewardRepository(private val rewardDao: RewardDao) {
    suspend fun insert(reward: RewardModel): Long = rewardDao.insert(convertModelToDTO(reward))
    suspend fun insert(rewards: List<RewardModel>): Array<Long> = rewardDao.insert(convertModelsToDTOs(rewards))

    suspend fun updateReward(reward: RewardModel): Int = rewardDao.updateReward(convertModelToDTO(reward))
    suspend fun updateRewards(rewards: List<RewardModel>): Int = rewardDao.updateRewards(convertModelsToDTOs(rewards))

    suspend fun deleteReward(reward: RewardModel): Int = rewardDao.deleteReward(convertModelToDTO(reward))
    suspend fun deleteReward(rewards: List<RewardModel>): Int = rewardDao.deleteReward(convertModelsToDTOs(rewards))
    suspend fun deleteAllRewards(): Int = rewardDao.deleteAllRewards()

    //rewards active
    suspend fun rewardsActiveAcquisitionDateAsc(): LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveAcquisitionDateAsc())
    suspend fun rewardsActiveAcquisitionDateDesc(): LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveAcquisitionDateDesc())
    suspend fun rewardsActiveLevelAsc(): LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveLevelAsc())
    suspend fun rewardsActiveLevelDesc(): LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveLevelDesc())

    //ACTIVE and NOT LOST rewards :
    suspend fun rewardsNotEscapedAcquisitionDateDesc(): LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc())

    //ACTIVE and LOST rewards :
    suspend fun rewardsEscapedAcquisitionDateDesc(): LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsEscapedAcquisitionDateDesc())

    //NON ACTIVE rewards for specific LEVEL:
    suspend fun rewardsOfSPecificLevelNotActive(level: Int):  LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsOfSPecificLevelNotActive(level))

    //NON ACTIVE or ACTIVE and ESCAPED rewards for specific LEVEL:
    suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(level))

    //COUNTS :
    suspend fun allRewardsCount(): Int = rewardDao.getNumberOfRows()
    suspend fun activeNotEscapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(level)
    suspend fun escapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfEscapedRewardsForLevel(level)
}
