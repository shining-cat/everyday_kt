package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.locale.dao.RewardDao
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.models.reward.Reward
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.converter.RewardConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RewardRepositoryImplTest {

    // We use a mock DAO, and only check that its methods are called by the repo when expected, with the right object type param.
    // When needed, we mock a return object for mockRewardDao methods
    // No need to test further, because the DAO is tested independently
    // No need to test the properties of the object, because the converter used by the repo is tested independently

    @MockK
    private lateinit var mockRewardDao: RewardDao

    @MockK
    private lateinit var mockRewardConverter: RewardConverter

    @MockK
    private lateinit var mockReward: Reward

    @MockK
    lateinit var mockRewardEntity: RewardEntity

    private lateinit var rewardRepo: RewardRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Assert.assertNotNull(mockRewardDao)
        Assert.assertNotNull(mockReward)
        Assert.assertNotNull(mockRewardEntity)
        Assert.assertNotNull(mockRewardConverter)
        rewardRepo =
            RewardRepositoryImpl(
                mockRewardDao,
                mockRewardConverter
            )
    }

    ///////////////////////////////
    @Test
    fun insert() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.insert(any()) } returns arrayOf(1L,2L,3L)
        val output = runBlocking {
            rewardRepo.insert(listOf(mockReward, mockReward, mockReward))
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.insert(any()) }
        assertTrue(output is Output.Success)
        assertEquals(arrayOf(1L,2L,3L), (output as Output.Success).result)
    }

    @Test
    fun updateRewards() {
        coEvery { mockRewardConverter.convertModelsToEntities(any()) } returns listOf(
            mockRewardEntity,
            mockRewardEntity,
            mockRewardEntity
        )
        coEvery { mockRewardDao.update(any()) } returns 3
        val output = runBlocking {
            rewardRepo.update(listOf(mockReward, mockReward, mockReward))
        }
        coVerify { mockRewardConverter.convertModelsToEntities(any()) }
        coVerify { mockRewardDao.update(any()) }
        assertTrue(output is Output.Success)
        assertEquals(3, (output as Output.Success).result)
    }

    @Test
    fun deleteAllRewards() {
        coEvery { mockRewardDao.deleteAllRewards() } returns 7
        val output = runBlocking {
            rewardRepo.deleteAllRewards()
        }
        coVerify { mockRewardDao.deleteAllRewards() }
        assertEquals(7, output)
    }

    ////////////////
    //GETTERS
    @Test
    fun getSpecificReward() {
        coEvery { mockRewardDao.getReward(8L) } returns mockRewardEntity
        coEvery { mockRewardConverter.convertEntitytoModel(any()) } returns mockReward
        val output = runBlocking {
            rewardRepo.getReward(8L)
        }
        coVerify { mockRewardConverter.convertEntitytoModel(any()) }
        coVerify { mockRewardDao.getReward(any()) }
        assertTrue(output is Output.Success)
        assertEquals(mockReward, (output as Output.Success).result)
    }

    @Test
    fun rewardsActiveAcquisitionDateAsc() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateAsc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsActiveAcquisitionDateDesc() {
        coEvery { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveAcquisitionDateDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsActiveLevelAsc() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelAsc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelAsc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelAsc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsActiveLevelDesc() {
        coEvery { mockRewardDao.getAllRewardsActiveLevelDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsActiveLevelDesc()
        }
        coVerify { mockRewardDao.getAllRewardsActiveLevelDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsNotEscapedAcquisitionDateDesc() {
        coEvery { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsNotEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsNotEscapedAcquisitionDatDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsEscapedAcquisitionDateDesc() {
        coEvery { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsEscapedAcquisitionDateDesc()
        }
        coVerify { mockRewardDao.getAllRewardsEscapedAcquisitionDateDesc() }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsOfSpecificLevelNotActive() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(any()) } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActive(3)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActive(any()) }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    @Test
    fun rewardsOfSPecificLevelNotActiveOrEscaped() {
        coEvery { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(any()) } returns listOf(
            mockRewardEntity
        )
        coEvery { mockRewardConverter.convertEntitiesToModels(any()) } returns listOf(mockReward)
        val output = runBlocking {
            rewardRepo.rewardsOfSPecificLevelNotActiveOrEscaped(4)
        }
        coVerify { mockRewardDao.getAllRewardsOfSpecificLevelNotActiveOrEscaped(any()) }
        coVerify { mockRewardConverter.convertEntitiesToModels(any()) }
        assertTrue(output is Output.Success)
        assertEquals(listOf(mockReward), (output as Output.Success).result)
    }

    ////////////////
    //COUNTS
    @Test
    fun allRewardsCount() {
        coEvery { mockRewardDao.getNumberOfRows() } returns 13
        val count = runBlocking {
            rewardRepo.countAllRewards()
        }
        coVerify { mockRewardDao.getNumberOfRows() }
        assertEquals(13, count)
    }

    @Test
    fun activeNotEscapedRewardsForLevel() {
        coEvery { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(any()) } returns 1
        coEvery { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(4) } returns 7
        val count4 = runBlocking {
            rewardRepo.countActiveNotEscapedRewardsForLevel(4)
        }
        val count5 = runBlocking {
            rewardRepo.countActiveNotEscapedRewardsForLevel(5)
        }
        coVerify { mockRewardDao.getNumberOfActiveNotEscapedRewardsForLevel(any()) }
        assertEquals(7, count4)
        assertEquals(1, count5)
    }

    @Test
    fun escapedRewardsForLevel() {
        coEvery { mockRewardDao.getNumberOfEscapedRewardsForLevel(any()) } returns 1
        coEvery { mockRewardDao.getNumberOfEscapedRewardsForLevel(3) } returns 9
        val count3 = runBlocking {
            rewardRepo.countEscapedRewardsForLevel(3)
        }
        val count5 = runBlocking {
            rewardRepo.countEscapedRewardsForLevel(5)
        }
        coVerify (exactly = 2){ mockRewardDao.getNumberOfEscapedRewardsForLevel(any()) }
        assertEquals(9, count3)
        assertEquals(1, count5)
    }
}