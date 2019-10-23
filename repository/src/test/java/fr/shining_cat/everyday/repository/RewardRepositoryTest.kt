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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RewardRepositoryTest: AbstractBaseTest()  {

    // We use a mock DAO, and only check that its methods are called when expected by the repo, with the right object type param.
    // When needed, we mock a return object for mockRewardDao methods
    // No need to test further, because the DAO is tested independently
    // No need to test the properties of the object, because the converter used by the repo is tested independently

    @Mock
    private lateinit var mockRewardDao: RewardDao

    @Mock
    lateinit var rewardDTOLive: LiveData<RewardDTO?>

    @Mock
    lateinit var rewardDTOsLive: LiveData<List<RewardDTO>?>

    private lateinit var rewardRepo: RewardRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Assert.assertNotNull(mockRewardDao)
        Assert.assertNotNull(rewardDTOLive)
        rewardRepo = RewardRepository(mockRewardDao)
        Mockito.`when`(mockRewardDao.getRewardLive(anyLong())).thenReturn(rewardDTOLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveAcquisitionDateAsc()).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveAcquisitionDateDesc()).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveLevelAsc()).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveLevelDesc()).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc()).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc()).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsOfSPecificLevelNotActive(anyInt())).thenReturn(rewardDTOsLive)
        Mockito.`when`(mockRewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(anyInt())).thenReturn(rewardDTOsLive)
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
        runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateAsc()
            Mockito.verify(mockRewardDao).getAllRewardsActiveAcquisitionDateAsc()
        }
    }

    @Test
    fun rewardsActiveAcquisitionDateDesc() {
        runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateDesc()
            Mockito.verify(mockRewardDao).getAllRewardsActiveAcquisitionDateDesc()
        }
    }

    @Test
    fun rewardsActiveLevelAsc() {
        runBlocking {
            rewardRepo.rewardsActiveLevelAsc()
            Mockito.verify(mockRewardDao).getAllRewardsActiveLevelAsc()
        }
    }

    @Test
    fun rewardsActiveLevelDesc() {
        runBlocking {
            rewardRepo.rewardsActiveLevelDesc()
            Mockito.verify(mockRewardDao).getAllRewardsActiveLevelDesc()
        }
    }

    @Test
    fun rewardsNotEscapedAcquisitionDateDesc() {
        runBlocking {
            rewardRepo.rewardsNotEscapedAcquisitionDateDesc()
            Mockito.verify(mockRewardDao).getAllRewardsNotEscapedAcquisitionDatDesc()
        }
    }

    @Test
    fun rewardsEscapedAcquisitionDateDesc() {
        runBlocking {
            rewardRepo.rewardsEscapedAcquisitionDateDesc()
            Mockito.verify(mockRewardDao).getAllRewardsEscapedAcquisitionDateDesc()
        }
    }

    @Test
    fun rewardsOfSPecificLevelNotActive() {
        runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActive(3)
            Mockito.verify(mockRewardDao).getAllRewardsOfSPecificLevelNotActive(anyInt())
        }
    }

    @Test
    fun rewardsOfSPecificLevelNotActiveOrEscaped() {
        runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActiveOrEscaped(4)
            Mockito.verify(mockRewardDao).getAllRewardsOfSPecificLevelNotActiveOrEscaped(anyInt())
        }
    }

    @Test
    fun allRewardsCount() {
        runBlocking {
            rewardRepo.allRewardsCount()
            Mockito.verify(mockRewardDao).getNumberOfRows()
        }
    }

    @Test
    fun activeNotEscapedRewardsForLevel() {
        runBlocking {
            rewardRepo.activeNotEscapedRewardsForLevel(5)
            Mockito.verify(mockRewardDao).getNumberOfActiveNotEscapedRewardsForLevel(anyInt())
        }
    }

    @Test
    fun escapedRewardsForLevel() {
        runBlocking {
            rewardRepo.escapedRewardsForLevel(1)
            Mockito.verify(mockRewardDao).getNumberOfEscapedRewardsForLevel(anyInt())
        }
    }
}