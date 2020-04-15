package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.repository.converter.RewardConverter

interface RewardRepository{
    suspend fun insert(rewards: List<Reward>): Array<Long>
    suspend fun update(rewards: List<Reward>): Int
    suspend fun delete(rewards: List<Reward>): Int
    suspend fun deleteAllRewards(): Int
    suspend fun getReward(rewardId: Long): Reward?
    suspend fun rewardsActiveAcquisitionDateAsc(): List<Reward>
    suspend fun rewardsActiveAcquisitionDateDesc(): List<Reward>
    suspend fun rewardsActiveLevelAsc(): List<Reward>
    suspend fun rewardsActiveLevelDesc(): List<Reward>
    suspend fun rewardsNotEscapedAcquisitionDateDesc(): List<Reward>
    suspend fun rewardsEscapedAcquisitionDateDesc(): List<Reward>
    suspend fun rewardsOfSPecificLevelNotActive(level: Int):  List<Reward>
    suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  List<Reward>
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

    override suspend fun getReward(rewardId: Long): Reward? = rewardConverter.convertEntitytoModel(rewardDao.getReward(rewardId))
    //rewards active
    override suspend fun rewardsActiveAcquisitionDateAsc(): List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveAcquisitionDateAsc())
    override suspend fun rewardsActiveAcquisitionDateDesc(): List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveAcquisitionDateDesc())
    override suspend fun rewardsActiveLevelAsc(): List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveLevelAsc())
    override suspend fun rewardsActiveLevelDesc(): List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsActiveLevelDesc())

    //ACTIVE and NOT-LOST rewards :
    override suspend fun rewardsNotEscapedAcquisitionDateDesc(): List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc())

    //ACTIVE and LOST rewards :
    override suspend fun rewardsEscapedAcquisitionDateDesc(): List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsEscapedAcquisitionDateDesc())

    //NON-ACTIVE rewards for specific LEVEL:
    override suspend fun rewardsOfSPecificLevelNotActive(level: Int):  List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsOfSpecificLevelNotActive(level))

    //NON-ACTIVE or ACTIVE-and-ESCAPED rewards for specific LEVEL:
    override suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int):  List<Reward> = rewardConverter.convertEntitiesToModels(rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(level))

    //COUNTS :
    override suspend fun allRewardsCount(): Int = rewardDao.getNumberOfRows()
    override suspend fun activeNotEscapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(level)
    override suspend fun escapedRewardsForLevel(level: Int):  Int = rewardDao.getNumberOfEscapedRewardsForLevel(level)
}