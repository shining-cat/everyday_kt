package fr.shining_cat.everyday.locale.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ACTIVE_STATE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.DATE_ACQUISITION
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.DATE_ESCAPING
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.ESCAPED_STATE
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.REWARD_ID
import fr.shining_cat.everyday.locale.entities.RewardEntityColumnNames.LEVEL
import fr.shining_cat.everyday.locale.entities.RewardTable.REWARD_TABLE

@Dao
abstract class RewardDao {

    @Insert
    abstract suspend fun insert(rewards: List<RewardEntity>): Array<Long>

    @Update
    abstract suspend fun update(rewards: List<RewardEntity>): Int

    @Delete
    abstract suspend fun delete(reward: List<RewardEntity>): Int

    @Query("DELETE FROM $REWARD_TABLE")
    abstract suspend fun deleteAllRewards(): Int

    //ROOM does not allow parameters for the ORDER BY clause to prevent injection so we need a proxy for each WHERE clause used

////////////////////////////////////////////////////////////////

    @Query("SELECT * from $REWARD_TABLE WHERE $REWARD_ID =:rewardId")
    abstract fun getRewardLive(rewardId: Long): LiveData<RewardEntity?>

    //all "active" rewards, ie all rewards that have at one point been obtained, regardless if they have been lost or not
    //sort on acquisitionDate ASC
    @Query("SELECT * from $REWARD_TABLE WHERE $ACTIVE_STATE == 1 ORDER BY $DATE_ACQUISITION ASC")
    abstract fun getAllRewardsActiveAcquisitionDateAsc(): LiveData<List<RewardEntity>?>

    //sort on acquisitionDate DESC
    @Query("SELECT * from $REWARD_TABLE WHERE $ACTIVE_STATE == 1 ORDER BY $DATE_ACQUISITION DESC")
    abstract fun getAllRewardsActiveAcquisitionDateDesc(): LiveData<List<RewardEntity>?>

    //sort on level ASC
    @Query("SELECT * from $REWARD_TABLE WHERE $ACTIVE_STATE == 1 ORDER BY $LEVEL ASC")
    abstract fun getAllRewardsActiveLevelAsc(): LiveData<List<RewardEntity>?>

    //sort on level DESC
    @Query("SELECT * from $REWARD_TABLE WHERE $ACTIVE_STATE == 1 ORDER BY $LEVEL DESC")
    abstract fun getAllRewardsActiveLevelDesc(): LiveData<List<RewardEntity>?>

    ////////////////////////////////////////////////////////////////
    //ACTIVE and NOT LOST rewards :
    @Query("SELECT * from $REWARD_TABLE WHERE $ACTIVE_STATE == 1 AND $ESCAPED_STATE == 0 ORDER BY $DATE_ACQUISITION DESC")
    abstract fun getAllRewardsNotEscapedAcquisitionDatDesc(): LiveData<List<RewardEntity>?>

    //ACTIVE and LOST rewards :
    @Query("SELECT * from $REWARD_TABLE WHERE $ACTIVE_STATE == 1 AND $ESCAPED_STATE == 1 ORDER BY $DATE_ESCAPING DESC")
    abstract fun getAllRewardsEscapedAcquisitionDateDesc(): LiveData<List<RewardEntity>?>

    //NON ACTIVE rewards for specific LEVEL:
    @Query("SELECT * from $REWARD_TABLE WHERE $LEVEL == :level AND $ACTIVE_STATE == 0")
    abstract fun getAllRewardsOfSPecificLevelNotActive(level: Int): LiveData<List<RewardEntity>?>

    //NON ACTIVE or ACTIVE and ESCAPED rewards for specific LEVEL:
    @Query("SELECT * from $REWARD_TABLE WHERE $LEVEL == :level AND ($ACTIVE_STATE == 0 OR $ESCAPED_STATE == 1)")
    abstract fun getAllRewardsOfSPecificLevelNotActiveOrEscaped(level: Int): LiveData<List<RewardEntity>?>

////////////////////////////////////////////////////////////////
    //COUNTS :

    //ALL ENTRIES (this is used to determine if possible rewards have been generated already or not)

    @Query("SELECT COUNT($REWARD_ID) FROM $REWARD_TABLE")
    abstract suspend fun getNumberOfRows(): Int

    //ACTIVE and NOT LOST rewards for level

    @Query("SELECT COUNT($REWARD_ID) FROM $REWARD_TABLE WHERE $LEVEL == :level AND $ACTIVE_STATE == 1 AND $ESCAPED_STATE == 0")
    abstract suspend fun getNumberOfActiveNotEscapedRewardsForLevel(level: Int): Int

    //ACTIVE and LOST rewards for level

    @Query("SELECT COUNT($REWARD_ID) FROM $REWARD_TABLE WHERE $LEVEL == :level AND $ACTIVE_STATE == 1 AND $ESCAPED_STATE == 1")
    abstract suspend fun getNumberOfEscapedRewardsForLevel(level: Int): Int
}
