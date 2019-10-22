package fr.shining_cat.everyday.repository

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito

class RewardRepositoryTest {

    //TODO: Use a mock DAO, and only check that its methods are called when expected by the repo, with the right object type param.
    // No need to test further, because the DAO is tested independently
    // No need to test the properties of the object, because the converter used by the repo is tested independently

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun insert() {
    }

    @Test
    fun insert1() {
    }

    @Test
    fun updateReward() {
    }

    @Test
    fun updateRewards() {
    }

    @Test
    fun deleteReward() {
    }

    @Test
    fun deleteReward1() {
    }

    @Test
    fun deleteAllRewards() {
    }

    @Test
    fun getRewardLive() {
    }

    @Test
    fun rewardsActiveAcquisitionDateAsc() {
    }

    @Test
    fun rewardsActiveAcquisitionDateDesc() {
    }

    @Test
    fun rewardsActiveLevelAsc() {
    }

    @Test
    fun rewardsActiveLevelDesc() {
    }

    @Test
    fun rewardsNotEscapedAcquisitionDateDesc() {
    }

    @Test
    fun rewardsEscapedAcquisitionDateDesc() {
    }

    @Test
    fun rewardsOfSPecificLevelNotActive() {
    }

    @Test
    fun rewardsOfSPecificLevelNotActiveOrEscaped() {
    }

    @Test
    fun allRewardsCount() {
    }

    @Test
    fun activeNotEscapedRewardsForLevel() {
    }

    @Test
    fun escapedRewardsForLevel() {
    }
}