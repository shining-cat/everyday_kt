package fr.shining_cat.everyday.localdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.shining_cat.everyday.localdata.dto.RewardDTO

@Dao
abstract class RewardDao{
    @Insert
    abstract fun insert(reward: RewardDTO): Long

    @Insert
    abstract fun insertMultiple(vararg rewards: RewardDTO): Array<Long>

    @Update
    abstract fun updateReward(reward: RewardDTO): Int

    @Update
    abstract fun updateMultipleReward(vararg rewards: RewardDTO): Int

    @Delete
    abstract fun deleteReward(reward: RewardDTO): Int

    @Query("DELETE FROM rewards_table")
    abstract fun deleteAllRewards(): Int

    //ROOM does not allow parameters for the ORDER BY clause to prevent injection so we need a proxy for each WHERE clause used

    //here we query for all "active" rewards, ie all rewards that have at one point been obtained, regardless if they have been lost or not
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

    ///here we query for ACTIVE AND NOT LOST rewards :
    @Query("SELECT * from rewards_table WHERE isActive == 1 AND isEscaped == 0 ORDER BY acquisitionDate DESC")
    abstract fun getAllRewardsNotEscapedAcquisitionDatDesc(): LiveData<List<RewardDTO>>

    //here we query for ACTIVE AND LOST rewards :
    @Query("SELECT * from rewards_table WHERE isActive == 1 AND isEscaped == 1 ORDER BY escapingDate DESC")
    abstract fun getAllRewardsEscapedAcquisitionDateDesc(): LiveData<List<RewardDTO>>

    //here we query for NON ACTIVE rewards for specific LEVEL:
    @Query("SELECT * from rewards_table WHERE level == :level AND isActive == 0")
    abstract fun getAllRewardsOfSPecificLevelNotActive(level: Int): LiveData<List<RewardDTO>>

    //here we query for NON ACTIVE or ACTIVE AND ESCAPED rewards for specific LEVEL:
    @Query("SELECT * from rewards_table WHERE level == :level AND (isActive == 0 OR isEscaped == 1)")
    abstract fun getAllRewardsOfSPecificLevelNotActiveOrEscaped(level: Int): LiveData<List<RewardDTO>>

    //just count entries in table (this is used to determine if possible rewards have been generated already or not)
    @Query("SELECT COUNT(id) FROM rewards_table")
    abstract fun getNumberOfRows(): Int

    //COUNTS :
    @Query("SELECT COUNT(id) FROM rewards_table WHERE level == :level ")
    abstract fun getNumberOfPossibleRewardsForLevel(level: Int): Int

    @Query("SELECT COUNT(id) FROM rewards_table WHERE level == :level AND isActive == 1 AND isEscaped == 0")
    abstract fun getNumberOfActiveNotEscapedRewardsForLevel(level: Int): Int

    @Query("SELECT COUNT(id) FROM rewards_table WHERE level == :level AND isActive == 1 AND isEscaped == 1")
    abstract fun getNumberOfEscapedRewardsForLevel(level: Int): Int

    //We will probably never query all the rewards
    @Query("SELECT * from rewards_table ORDER BY acquisitionDate ASC")
    abstract fun getAllRewardsAcquisitionDateAsc(): LiveData<List<RewardDTO>>

    //We will probably never query the inactive rewards (created but not used in app yet)
    @Query("SELECT * from rewards_table WHERE isActive == 0")
    abstract fun getAllRewardsInactive(): LiveData<List<RewardDTO>>

}
