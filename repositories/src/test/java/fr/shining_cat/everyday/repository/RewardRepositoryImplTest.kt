package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.Reward
import fr.shining_cat.everyday.repository.converter.RewardConverter
import fr.shining_cat.everyday.repository.repo.RewardRepository
import fr.shining_cat.everyday.repository.repo.RewardRepositoryImpl
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RewardRepositoryImplTest: AbstractBaseTest()  {

    // We use a mock DAO, and only check that its methods are called by the repo when expected, with the right object type param.
    // When needed, we mock a return object for mockRewardDao methods
    // No need to test further, because the DAO is tested independently
    // No need to test the properties of the object, because the converter used by the repo is tested independently

    @Mock
    private lateinit var mockRewardDao: RewardDao

    @Mock
    private lateinit var mockRewardConverter: RewardConverter

    @Mock
    private lateinit var mockReward: Reward
    
    @Mock
    lateinit var rewardEntityLive: LiveData<RewardEntity?>

    @Mock
    lateinit var rewardEntitiesLive: LiveData<List<RewardEntity>?>

    private lateinit var rewardRepo: RewardRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Assert.assertNotNull(mockRewardDao)
        Assert.assertNotNull(mockReward)
        Assert.assertNotNull(rewardEntityLive)
        Assert.assertNotNull(rewardEntitiesLive)
        Assert.assertNotNull(mockRewardConverter)
        rewardRepo =
            RewardRepositoryImpl(
                mockRewardDao,
                mockRewardConverter
            )
        Mockito.`when`(mockRewardDao.getRewardLive(anyLong())).thenReturn(rewardEntityLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveAcquisitionDateAsc()).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveAcquisitionDateDesc()).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveLevelAsc()).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsActiveLevelDesc()).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc()).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc()).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsOfSPecificLevelNotActive(anyInt())).thenReturn(rewardEntitiesLive)
        Mockito.`when`(mockRewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(anyInt())).thenReturn(rewardEntitiesLive)
    }
    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }

    ///////////////////////////////
    @Test
    fun insert() {
        runBlocking {
            rewardRepo.insert(listOf(mockReward))
            Mockito.verify(mockRewardDao).insert(any())
        }
    }

    @Test
    fun updateRewards() {
        runBlocking {
            rewardRepo.update(listOf(mockReward))
            Mockito.verify(mockRewardDao).update(any())
        }
    }

    @Test
    fun deleteReward() {
        runBlocking {
            rewardRepo.delete(listOf(mockReward))
            Mockito.verify(mockRewardDao).delete(any())
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