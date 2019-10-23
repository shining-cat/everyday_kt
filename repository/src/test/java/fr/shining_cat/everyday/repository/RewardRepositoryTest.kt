package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.localdata.dao.RewardDao
import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.testutils.model.RewardModelTestUtils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RewardRepositoryTest: AbstractBaseTest()  {

    //TODO: Use a mock DAO, and only check that its methods are called when expected by the repo, with the right object type param.
    // No need to test further, because the DAO is tested independently
    // No need to test the properties of the object, because the converter used by the repo is tested independently

    @Mock
    private lateinit var mockRewardDao: RewardDao

    @Mock
    lateinit var rewardDTOLive: LiveData<RewardDTO?>

    private lateinit var rewardRepo: RewardRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Assert.assertNotNull(mockRewardDao)
        Assert.assertNotNull(rewardDTOLive)
        rewardRepo = RewardRepository(mockRewardDao)
        Mockito.`when`(mockRewardDao.getRewardLive(anyLong())).thenReturn(rewardDTOLive)
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
        runBlocking {
            rewardRepo.insert(RewardModelTestUtils.generateReward())
            Mockito.verify(mockRewardDao).insert(any<RewardDTO>())
        }
    }

    @Test
    fun insertMulti() {
        runBlocking {
            rewardRepo.insert(RewardModelTestUtils.generateRewards(27))
            Mockito.verify(mockRewardDao).insert(any<List<RewardDTO>>())
        }
    }

    @Test
    fun updateReward() {
        runBlocking {
            rewardRepo.updateReward(RewardModelTestUtils.generateReward())
            Mockito.verify(mockRewardDao).updateReward(any<RewardDTO>())
        }
    }

    @Test
    fun updateRewards() {
        runBlocking {
            rewardRepo.updateRewards(RewardModelTestUtils.generateRewards(27))
            Mockito.verify(mockRewardDao).updateRewards(any<List<RewardDTO>>())
        }
    }

    @Test
    fun deleteReward() {
        runBlocking {
            rewardRepo.deleteReward(RewardModelTestUtils.generateReward())
            Mockito.verify(mockRewardDao).deleteReward(any<RewardDTO>())
        }
    }

    @Test
    fun deleteReward1() {
        runBlocking {
            rewardRepo.deleteReward(RewardModelTestUtils.generateRewards(27))
            Mockito.verify(mockRewardDao).deleteReward(any<List<RewardDTO>>())
        }
    }

    @Test
    fun deleteAllRewards() {
        runBlocking {
            rewardRepo.deleteAllRewards()
            Mockito.verify(mockRewardDao).deleteAllRewards()
        }
    }

    @Test
    fun getRewardLive() {
        runBlocking {
            rewardRepo.getRewardLive(8L)
            Mockito.verify(mockRewardDao).getRewardLive(anyLong())
        }
    }

    @Test
    fun rewardsActiveAcquisitionDateAsc() {
        fail("test to write")
    }

    @Test
    fun rewardsActiveAcquisitionDateDesc() {
        fail("test to write")
    }

    @Test
    fun rewardsActiveLevelAsc() {
        fail("test to write")
    }

    @Test
    fun rewardsActiveLevelDesc() {
        fail("test to write")
    }

    @Test
    fun rewardsNotEscapedAcquisitionDateDesc() {
        fail("test to write")
    }

    @Test
    fun rewardsEscapedAcquisitionDateDesc() {
        fail("test to write")
    }

    @Test
    fun rewardsOfSPecificLevelNotActive() {
        fail("test to write")
    }

    @Test
    fun rewardsOfSPecificLevelNotActiveOrEscaped() {
        fail("test to write")
    }

    @Test
    fun allRewardsCount() {
        fail("test to write")
    }

    @Test
    fun activeNotEscapedRewardsForLevel() {
        fail("test to write")
    }

    @Test
    fun escapedRewardsForLevel() {
        fail("test to write")
    }
}