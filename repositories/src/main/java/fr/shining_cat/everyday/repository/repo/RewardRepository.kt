package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_INSERT_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_NO_RESULT
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_UPDATE_FAILED
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converter.RewardConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RewardRepository {
    suspend fun insert(rewards: List<Reward>): Output<Array<Long>>
    suspend fun update(rewards: List<Reward>): Output<Int>
    suspend fun deleteAllRewards(): Int
    suspend fun getReward(rewardId: Long): Output<Reward>
    suspend fun rewardsActiveAcquisitionDateAsc(): Output<List<Reward>>
    suspend fun rewardsActiveAcquisitionDateDesc(): Output<List<Reward>>
    suspend fun rewardsActiveLevelAsc(): Output<List<Reward>>
    suspend fun rewardsActiveLevelDesc(): Output<List<Reward>>
    suspend fun rewardsNotEscapedAcquisitionDateDesc(): Output<List<Reward>>
    suspend fun rewardsEscapedAcquisitionDateDesc(): Output<List<Reward>>
    suspend fun rewardsOfSPecificLevelNotActive(level: Int): Output<List<Reward>>
    suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int): Output<List<Reward>>
    suspend fun allRewardsCount(): Int
    suspend fun activeNotEscapedRewardsForLevel(level: Int): Int
    suspend fun escapedRewardsForLevel(level: Int): Int
}

class RewardRepositoryImpl(
    private val rewardDao: RewardDao,
    private val rewardConverter: RewardConverter
) : RewardRepository {

    override suspend fun insert(rewards: List<Reward>): Output<Array<Long>> {
        val inserted = withContext(Dispatchers.IO) {
            rewardDao.insert(
                rewardConverter.convertModelsToEntities(rewards)
            )
        }
        return if (inserted.size == rewards.size) {
            Output.Success(inserted)
        } else {
            Output.Error(
                ERROR_CODE_DATABASE_OPERATION_FAILED,
                ERROR_MESSAGE_INSERT_FAILED,
                Exception(ERROR_MESSAGE_INSERT_FAILED)
            )
        }
    }

    override suspend fun update(rewards: List<Reward>): Output<Int> {
        val updated = withContext(Dispatchers.IO) {
            rewardDao.update(
                rewardConverter.convertModelsToEntities(rewards)
            )
        }
        return if (updated == rewards.size) {
            Output.Success(updated)
        } else {
            Output.Error(
                ERROR_CODE_DATABASE_OPERATION_FAILED,
                ERROR_MESSAGE_UPDATE_FAILED,
                Exception(ERROR_MESSAGE_UPDATE_FAILED)
            )
        }
    }

    override suspend fun deleteAllRewards(): Int =
        withContext(Dispatchers.IO) { rewardDao.deleteAllRewards() }

    override suspend fun getReward(rewardId: Long): Output<Reward> {
        val rewardEntity = withContext(Dispatchers.IO) { rewardDao.getReward(rewardId) }
        return if (rewardEntity == null) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT,
                ERROR_MESSAGE_NO_RESULT,
                NullPointerException(ERROR_MESSAGE_NO_RESULT)
            )
        } else {
            Output.Success(
                withContext(Dispatchers.Default) {
                    rewardConverter.convertEntitytoModel(rewardEntity)
                }
            )
        }

    }

    //rewards active
    private suspend fun handleQueryResult(rewardEntities: List<RewardEntity>): Output<List<Reward>> {
        return if (rewardEntities.isEmpty()) {
            Output.Error(
                Constants.ERROR_CODE_NO_RESULT,
                ERROR_MESSAGE_NO_RESULT,
                NullPointerException(ERROR_MESSAGE_NO_RESULT)
            )
        } else {
            Output.Success(
                withContext(Dispatchers.Default) {
                    rewardConverter.convertEntitiesToModels(rewardEntities)
                }
            )
        }
    }

    override suspend fun rewardsActiveAcquisitionDateAsc(): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsActiveAcquisitionDateAsc()
        }
        return handleQueryResult(rewardEntities)
    }

    override suspend fun rewardsActiveAcquisitionDateDesc(): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsActiveAcquisitionDateDesc()
        }
        return handleQueryResult(rewardEntities)
    }

    override suspend fun rewardsActiveLevelAsc(): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsActiveLevelAsc()
        }
        return handleQueryResult(rewardEntities)
    }

    override suspend fun rewardsActiveLevelDesc(): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsActiveLevelDesc()
        }
        return handleQueryResult(rewardEntities)
    }

    //ACTIVE and NOT-LOST rewards :
    override suspend fun rewardsNotEscapedAcquisitionDateDesc(): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc()
        }
        return handleQueryResult(rewardEntities)
    }

    //ACTIVE and LOST rewards :
    override suspend fun rewardsEscapedAcquisitionDateDesc(): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsEscapedAcquisitionDateDesc()
        }
        return handleQueryResult(rewardEntities)
    }

    //NON-ACTIVE rewards for specific LEVEL:
    override suspend fun rewardsOfSPecificLevelNotActive(level: Int): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsOfSpecificLevelNotActive(level)
        }
        return handleQueryResult(rewardEntities)
    }

    //NON-ACTIVE or ACTIVE-and-ESCAPED rewards for specific LEVEL:
    override suspend fun rewardsOfSPecificLevelNotActiveOrEscaped(level: Int): Output<List<Reward>> {
        val rewardEntities = withContext(Dispatchers.IO) {
            rewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(level)
        }
        return handleQueryResult(rewardEntities)
    }

    //COUNTS :
    override suspend fun allRewardsCount(): Int = rewardDao.getNumberOfRows()

    override suspend fun activeNotEscapedRewardsForLevel(level: Int): Int =
        rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(level)

    override suspend fun escapedRewardsForLevel(level: Int): Int =
        rewardDao.getNumberOfEscapedRewardsForLevel(level)
}