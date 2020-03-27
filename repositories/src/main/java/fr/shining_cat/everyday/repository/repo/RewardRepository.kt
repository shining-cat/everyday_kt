package fr.shining_cat.everyday.repository.repo

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.models.RewardModel
import fr.shining_cat.everyday.repository.converter.RewardConverter

interface RewardRepository{
    suspend fun insert(reward: RewardModel): Long
    suspend fun insert(rewards: List<RewardModel>): Array<Long>
    suspend fun updateReward(reward: RewardModel): Int
    suspend fun updateRewards(rewards: List<RewardModel>): Int
    suspend fun deleteReward(reward: RewardModel): Int
    suspend fun deleteReward(rewards: List<RewardModel>): Int
    suspend fun deleteAllRewards(): Int
    fun getRewardLive(rewardId: Long): LiveData<RewardModel?>
    fun rewardsActiveAcquisitionDateAsc(): LiveData<List<RewardModel>>
    fun rewardsActiveAcquisitionDateDesc(): LiveData<List<RewardModel>>
    fun rewardsActiveLevelAsc(): LiveData<List<RewardModel>>
    fun rewardsActiveLevelDesc(): LiveData<List<RewardModel>>
    fun rewardsNotEscapedAcquisitionDateDesc(): LiveData<List<RewardModel>>
    fun rewardsEscapedAcquisitionDateDesc(): LiveData<List<RewardModel>>
    fun rewardsOfSPecificLevelNotActive(level: Int):  LiveData<List<RewardModel>>
    fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  LiveData<List<RewardModel>>
    suspend fun allRewardsCount(): Int
    suspend fun activeNotEscapedRewardsForLevel(level: Int):  Int
    suspend fun escapedRewardsForLevel(level: Int):  Int
}

class RewardRepositoryImpl(
    private val rewardDao: RewardDao,
    private val rewardConverter: RewardConverter
): RewardRepository {

    override suspend fun insert(reward: RewardModel): Long = rewardDao.insert(rewardConverter.convertModelToEntity(reward))
    override suspend fun insert(rewards: List<RewardModel>): Array<Long> = rewardDao.insert(rewardConverter.convertModelsToEntities(rewards))

    override suspend fun updateReward(reward: RewardModel): Int = rewardDao.updateReward(rewardConverter.convertModelToEntity(reward))
    override suspend fun updateRewards(rewards: List<RewardModel>): Int = rewardDao.updateRewards(rewardConverter.convertModelsToEntities(rewards))

    override suspend fun deleteReward(reward: RewardModel): Int = rewardDao.deleteReward(rewardConverter.convertModelToEntity(reward))
    override suspend fun deleteReward(rewards: List<RewardModel>): Int = rewardDao.deleteReward(rewardConverter.convertModelsToEntities(rewards))
    override suspend fun deleteAllRewards(): Int = rewardDao.deleteAllRewards()

    override fun getRewardLive(rewardId: Long): LiveData<RewardModel?> = rewardConverter.convertEntitytoModel(rewardDao.getRewardLive(rewardId))
    //rewards active
    override fun rewardsActiveAcquisitionDateAsc(): LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveAcquisitionDateAsc())
    override fun rewardsActiveAcquisitionDateDesc(): LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveAcquisitionDateDesc())
    override fun rewardsActiveLevelAsc(): LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveLevelAsc())
    override fun rewardsActiveLevelDesc(): LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveLevelDesc())

    //ACTIVE and NOT-LOST rewards :
    override fun rewardsNotEscapedAcquisitionDateDesc(): LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc())

    //ACTIVE and LOST rewards :
    override fun rewardsEscapedAcquisitionDateDesc(): LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsEscapedAcquisitionDateDesc())

    //NON-ACTIVE rewards for specific LEVEL:
    override fun rewardsOfSPecificLevelNotActive(level: Int):  LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsOfSPecificLevelNotActive(level))

    //NON-ACTIVE or ACTIVE-and-ESCAPED rewards for specific LEVEL:
    override fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  LiveData<List<RewardModel>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(level))

    //COUNTS :
    override suspend fun allRewardsCount(): Int = rewardDao.getNumberOfRows()
    override suspend fun activeNotEscapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(level)
    override suspend fun escapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfEscapedRewardsForLevel(level)
}