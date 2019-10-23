package fr.shining_cat.everyday.localdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.shining_cat.everyday.localdata.dto.RewardDTO

@Dao
abstract class RewardDao{
    
    @Insert
    abstract suspend fun insert(reward: RewardDTO): Long

    
    @Insert
    abstract suspend fun insert(rewards: List<RewardDTO>): Array<Long>

    
    @Update
    abstract suspend fun updateReward(reward: RewardDTO): Int

    
    @Update
    abstract suspend fun updateRewards(rewards: List<RewardDTO>): Int

    
    @Delete
    abstract suspend fun deleteReward(reward: RewardDTO): Int

    
    @Delete
    abstract suspend fun deleteReward(reward: List<RewardDTO>): Int

    
    @Query("DELETE FROM rewards_table")
    abstract suspend fun deleteAllRewards(): Int

    //ROOM does not allow parameters for the ORDER BY clause to prevent injection so we need a proxy for each WHERE clause used

////////////////////////////////////////////////////////////////
    
    @Query("SELECT * from rewards_table WHERE id =:rewardId")
    abstract fun getRewardLive(rewardId: Long): LiveData<RewardDTO?>

    //all "active" rewards, ie all rewards that have at one point been obtained, regardless if they have been lost or not
    //sort on acquisitionDate ASC
    @Query("SELECT * from rewards_table WHERE isActive == 1 ORDER BY acquisitionDate ASC")
    abstract fun getAllRewardsActiveAcquisitionDateAsc(): LiveData<List<RewardDTO>>

    //sort on acquisitionDate DESC
    @Query("SELECT * from rewards_table WHERE isActive == 1 ORDER BY acquisitionDate DESC")
    abstract fun getAllRewardsActiveAcquisitionDateDesc(): LiveData<List<RewardDTO>>

    //sort on level ASC
    @Query("SELECT * from rewards_table WHERE isActive == 1 ORDER BY level ASC")
    abstract fun getAllRewardsActiveLevelAsc(): LiveData<List<RewardDTO>>

    //sort on level DESC
    @Query("SELECT * from rewards_table WHERE isActive == 1 ORDER BY level DESC")
    abstract fun getAllRewardsActiveLevelDesc(): LiveData<List<RewardDTO>>

////////////////////////////////////////////////////////////////
    //ACTIVE and NOT LOST rewards :
    @Query("SELECT * from rewards_table WHERE isActive == 1 AND isEscaped == 0 ORDER BY acquisitionDate DESC")
    abstract fun getAllRewardsNotEscapedAcquisitionDatDesc(): LiveData<List<RewardDTO>>

    //ACTIVE and LOST rewards :
    @Query("SELECT * from rewards_table WHERE isActive == 1 AND isEscaped == 1 ORDER BY escapingDate DESC")
    abstract fun getAllRewardsEscapedAcquisitionDateDesc(): LiveData<List<RewardDTO>>

    //NON ACTIVE rewards for specific LEVEL:
    @Query("SELECT * from rewards_table WHERE level == :level AND isActive == 0")
    abstract fun getAllRewardsOfSPecificLevelNotActive(level: Int): LiveData<List<RewardDTO>>

    //NON ACTIVE or ACTIVE and ESCAPED rewards for specific LEVEL:
    @Query("SELECT * from rewards_table WHERE level == :level AND (isActive == 0 OR isEscaped == 1)")
    abstract fun getAllRewardsOfSPecificLevelNotActiveOrEscaped(level: Int): LiveData<List<RewardDTO>>

////////////////////////////////////////////////////////////////
    //COUNTS :

    //ALL ENTRIES (this is used to determine if possible rewards have been generated already or not)
    
    @Query("SELECT COUNT(id) FROM rewards_table")
    abstract suspend fun getNumberOfRows(): Int

    //ACTIVE and NOT LOST rewards for level
    
    @Query("SELECT COUNT(id) FROM rewards_table WHERE level == :level AND isActive == 1 AND isEscaped == 0")
    abstract suspend fun getNumberOfActiveNotEscapedRewardsForLevel(level: Int): Int

    //ACTIVE and LOST rewards for level
    
    @Query("SELECT COUNT(id) FROM rewards_table WHERE level == :level AND isActive == 1 AND isEscaped == 1")
    abstract suspend fun getNumberOfEscapedRewardsForLevel(level: Int): Int
}
