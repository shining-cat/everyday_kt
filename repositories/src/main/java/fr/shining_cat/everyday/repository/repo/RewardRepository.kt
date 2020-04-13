package fr.shining_cat.everyday.repository.repo

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.repository.converter.RewardConverter

interface RewardRepository{
    suspend fun insert(rewards: List<Reward>): Array<Long>
    suspend fun update(rewards: List<Reward>): Int
    suspend fun delete(rewards: List<Reward>): Int
    suspend fun deleteAllRewards(): Int
    fun getRewardLive(rewardId: Long): LiveData<Reward?>
    fun rewardsActiveAcquisitionDateAsc(): LiveData<List<Reward>>
    fun rewardsActiveAcquisitionDateDesc(): LiveData<List<Reward>>
    fun rewardsActiveLevelAsc(): LiveData<List<Reward>>
    fun rewardsActiveLevelDesc(): LiveData<List<Reward>>
    fun rewardsNotEscapedAcquisitionDateDesc(): LiveData<List<Reward>>
    fun rewardsEscapedAcquisitionDateDesc(): LiveData<List<Reward>>
    fun rewardsOfSPecificLevelNotActive(level: Int):  LiveData<List<Reward>>
    fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  LiveData<List<Reward>>
    suspend fun allRewardsCount(): Int
    suspend fun activeNotEscapedRewardsForLevel(level: Int):  Int
    suspend fun escapedRewardsForLevel(level: Int):  Int
}

class RewardRepositoryImpl(
    private val rewardDao: RewardDao,
    private val rewardConverter: RewardConverter
): RewardRepository {

    override suspend fun insert(rewards: List<Reward>): Array<Long> = rewardDao.insert(rewardConverter.convertModelsToEntities(rewards))

    override suspend fun update(rewards: List<Reward>): Int = rewardDao.update(rewardConverter.convertModelsToEntities(rewards))

    override suspend fun delete(rewards: List<Reward>): Int = rewardDao.delete(rewardConverter.convertModelsToEntities(rewards))
    override suspend fun deleteAllRewards(): Int = rewardDao.deleteAllRewards()

    override fun getRewardLive(rewardId: Long): LiveData<Reward?> = rewardConverter.convertEntitytoModel(rewardDao.getRewardLive(rewardId))
    //rewards active
    override fun rewardsActiveAcquisitionDateAsc(): LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveAcquisitionDateAsc())
    override fun rewardsActiveAcquisitionDateDesc(): LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveAcquisitionDateDesc())
    override fun rewardsActiveLevelAsc(): LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveLevelAsc())
    override fun rewardsActiveLevelDesc(): LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveLevelDesc())

    //ACTIVE and NOT-LOST rewards :
    override fun rewardsNotEscapedAcquisitionDateDesc(): LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc())

    //ACTIVE and LOST rewards :
    override fun rewardsEscapedAcquisitionDateDesc(): LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsEscapedAcquisitionDateDesc())

    //NON-ACTIVE rewards for specific LEVEL:
    override fun rewardsOfSPecificLevelNotActive(level: Int):  LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsOfSPecificLevelNotActive(level))

    //NON-ACTIVE or ACTIVE-and-ESCAPED rewards for specific LEVEL:
    override fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  LiveData<List<Reward>> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(level))

    //COUNTS :
    override suspend fun allRewardsCount(): Int = rewardDao.getNumberOfRows()
    override suspend fun activeNotEscapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(level)
    override suspend fun escapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfEscapedRewardsForLevel(level)
}