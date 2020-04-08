package fr.shining_cat.everyday.locale.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import fr.shining_cat.everyday.locale.entities.RewardEntity
import fr.shining_cat.everyday.testutils.extensions.getValueBlocking
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class RewardDaoTest {

    //set the testing environment to use Main thread instead of background one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var rewardDao: RewardDao

    @Before
    fun setupEmptyTable(){
        tearDown()
        EveryDayRoomDatabase.TEST_MODE = true
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        rewardDao = EveryDayRoomDatabase.getInstance(appContext).rewardDao()
        emptyTableAndCheck()
    }

    private fun emptyTableAndCheck(){
        runBlocking {
            rewardDao.deleteAllRewards()
        }
        checkTotalCountIs(0)
    }

    private fun checkTotalCountIs(expectedCount: Int){
        val count = runBlocking {
            rewardDao.getNumberOfRows()
        }
        assertEquals(expectedCount, count)
    }

    @After
    fun tearDown() {
        EveryDayRoomDatabase.closeAndDestroy()
    }

    private fun generateReward(desiredLevel: Int = 1,
                       active:Boolean = true,
                       escaped:Boolean = false,
                       desiredId: Long = -1,
                       desiredCode: String = "any code",
                       yearAcquired: Int = 2000,
                       monthAcquired: Int = 5,
                       dayAcquired: Int = 13,
                       yearEscaped: Int = 2001,
                       monthEscaped: Int = 6,
                       dayEscaped: Int = 25,
                       desiredName: String = "this is my name",
                       desiredLegsColor: String = "#FF000000",
                       desiredBodyColor: String = "#00FF0000",
                       desiredArmsColor: String = "#0000FF00"):RewardEntity{
        if(desiredId == -1L){
            return RewardEntity(
                code = desiredCode,
                level = desiredLevel,
                acquisitionDate = GregorianCalendar(yearAcquired, monthAcquired, dayAcquired).timeInMillis,
                escapingDate = GregorianCalendar(yearEscaped, monthEscaped, dayEscaped).timeInMillis,
                isActive = active,
                isEscaped = escaped,
                name = desiredName,
                legsColor = desiredLegsColor,
                bodyColor = desiredBodyColor,
                armsColor = desiredArmsColor
            )
        }else {
            return RewardEntity(
                id = desiredId,
                code = desiredCode,
                level = desiredLevel,
                acquisitionDate = GregorianCalendar(yearAcquired, monthAcquired, dayAcquired).timeInMillis,
                escapingDate = GregorianCalendar(yearEscaped, monthEscaped, dayEscaped).timeInMillis,
                isActive = active,
                isEscaped = escaped,
                name = desiredName,
                legsColor = desiredLegsColor,
                bodyColor = desiredBodyColor,
                armsColor = desiredArmsColor
            )
        }
    }

    private fun generateRewards(numberOfRewardsDto: Int = 1, desiredLevel: Int = 1, active:Boolean = true, escaped:Boolean = false):List<RewardEntity>{
        val returnList = mutableListOf<RewardEntity>()
        for(i in 0 until numberOfRewardsDto){
            returnList.add(generateReward(desiredLevel, active, escaped))
        }
        return returnList
    }

////////////////////////////////////////////////////////////////
    @Test
    fun insertRewardTest() {

        val rewardEntity = generateReward(desiredId = 25)
        val rewardEntityTestID = runBlocking {
            rewardDao.insert(rewardEntity)
        }
        assertEquals(25L, rewardEntityTestID)
        checkTotalCountIs(1)
    }

    @Test
    fun insertMultiRewardTest(){
        val rewardsToInsertList = generateRewards(20)
        val insertedIds = runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        assertEquals(20, insertedIds.size)
        assertNotNull(insertedIds)
        var i = 1L
        for (id in insertedIds) {
            assertEquals(i, id)
            i++
        }
        checkTotalCountIs(20)
    }

    @Test
    fun deleteRewardFromEmptyTable() {
        val rewardEntityToDeleteTest = generateReward(desiredId = 25)
        //
        checkTotalCountIs(0)
        val countDeleted = runBlocking {
            rewardDao.deleteReward(rewardEntityToDeleteTest)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteNonExistentReward() {
        runBlocking {
            rewardDao.insert(generateReward(desiredId = 25))
            rewardDao.insert(generateReward(desiredId = 26))
            rewardDao.insert(generateReward(desiredId = 27))
            rewardDao.insert(generateReward(desiredId = 28))
            rewardDao.insert(generateReward(desiredId = 29))
            rewardDao.insert(generateReward(desiredId = 30))
        }
        val rewardEntityToDeleteTest = generateReward(desiredId = 723)
        //
        checkTotalCountIs(6)
        val countDeleted = runBlocking {
            rewardDao.deleteReward(rewardEntityToDeleteTest)
        }
        assertEquals(0, countDeleted)
        checkTotalCountIs(6)
    }

    @Test
    fun deleteOneRewardTest(){
        val rewardEntityToDeleteTest = generateReward(desiredId = 25)
        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.add(rewardEntityToDeleteTest)
        rewardsToInsertList.addAll(generateRewards(50))
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(51)
        //
        val countDeleted = runBlocking {
            rewardDao.deleteReward(rewardEntityToDeleteTest)
        }
        assertEquals(1, countDeleted)
        //
        checkTotalCountIs(50)

    }

    @Test
    fun deleteMultiRewardTestOnEmptyTable(){
        //create the test-subject list of items
        val rewardsToDeleteList = generateRewards(17)
        checkTotalCountIs(0)
        //delete test-subject list of items
        val numberOfDeletedRows = runBlocking {
            rewardDao.deleteReward(rewardsToDeleteList)
        }
        checkTotalCountIs(0)
        assertEquals(0, numberOfDeletedRows)
    }

    @Test
    fun deleteMultiRewardTest(){
        //insert the test-subject list of items
        val rewardsToDeleteList = generateRewards(17)
        //insert and collect the ids
        val insertedIds = runBlocking {
            rewardDao.insert(rewardsToDeleteList)
        }
        assertNotNull(insertedIds)
        assertEquals(17, insertedIds.size)
        var i = 1L //first index generated in DB will be 1 and not 0
        for (id in insertedIds) {
            assertEquals(i, id)
            rewardsToDeleteList[i.toInt()-1].id = id //update test-subject list with ids from insertion
            i++
        }
        checkTotalCountIs(17)
        //insert some more data
        runBlocking {
            rewardDao.insert(generateRewards(29))
        }
        checkTotalCountIs(46)
        //delete test-subject list of items
        val numberOfDeletedRows = runBlocking {
            rewardDao.deleteReward(rewardsToDeleteList)
        }
        checkTotalCountIs(29)
        assertEquals(17, numberOfDeletedRows)
    }

    @Test
    fun deleteAllRewardTestOnEmptyTable() {
        checkTotalCountIs(0)
        val numberOfDeletedRows = runBlocking {
            rewardDao.deleteAllRewards()
        }
        assertEquals(0, numberOfDeletedRows)
        checkTotalCountIs(0)
    }

    @Test
    fun deleteAllRewardTest() {
        runBlocking {
            rewardDao.insert(generateRewards(73))
        }
        checkTotalCountIs(73)
        val numberOfDeletedRows = runBlocking {
            rewardDao.deleteAllRewards()
        }
        assertEquals(73, numberOfDeletedRows)
        checkTotalCountIs(0)
    }

    @Test
    fun getRewardOnEmptyTableTest() {
        checkTotalCountIs(0)
        val rewardEntityExtractedLive = rewardDao.getRewardLive(75)
        assertNotNull(rewardEntityExtractedLive)
        val rewardEntityExtracted = rewardEntityExtractedLive.getValueBlocking()
        assertNull(rewardEntityExtracted)
    }

    @Test
    fun getNonExistentRewardTest() {
        runBlocking {
            rewardDao.insert(generateReward(desiredId = 25))
            rewardDao.insert(generateReward(desiredId = 26))
            rewardDao.insert(generateReward(desiredId = 27))
            rewardDao.insert(generateReward(desiredId = 28))
            rewardDao.insert(generateReward(desiredId = 29))
        }
        checkTotalCountIs(5)
        val rewardEntityExtractedLive = rewardDao.getRewardLive(73)
        assertNotNull(rewardEntityExtractedLive)
        val rewardEntityExtracted = rewardEntityExtractedLive.getValueBlocking()
        assertNull(rewardEntityExtracted)
    }

    @Test
    fun getOneRewardTest(){
        val rewardEntity = generateReward(
                                                desiredLevel = 3,
                                                active = true,
                                                escaped = false,
                                                desiredId = 83,
                                                yearAcquired = 1989,
                                                monthAcquired = 3,
                                                dayAcquired = 7,
                                                yearEscaped = 1997,
                                                monthEscaped = 8,
                                                dayEscaped = 13,
                                                desiredName = "getOneRewardTest is my name",
                                                desiredLegsColor = "getOneRewardTest legs color",
                                                desiredBodyColor = "getOneRewardTest body color",
                                                desiredArmsColor = "getOneRewardTest arms color")
        val rewardCode = rewardEntity.code
        val rewardEntityInsertedTestID = runBlocking {
            rewardDao.insert(rewardEntity)
        }
        assertNotNull(rewardEntityInsertedTestID)
        assertEquals(83L, rewardEntityInsertedTestID)
        //
        runBlocking {
            rewardDao.insert(generateRewards(13))
        }
        checkTotalCountIs(14)
        //
        val rewardEntityExtractedLive = rewardDao.getRewardLive(rewardEntityInsertedTestID)
        assertNotNull(rewardEntityExtractedLive)
        val rewardEntityExtracted = rewardEntityExtractedLive.getValueBlocking()
        assertNotNull(rewardEntityExtracted)
        if (rewardEntityExtracted != null) {
            assertEquals(83, rewardEntityExtracted.id)
            assertEquals(3, rewardEntityExtracted.level)
            assertEquals(rewardCode, rewardEntityExtracted.code)
            assertEquals(GregorianCalendar(1989, 3, 7).timeInMillis, rewardEntityExtracted.acquisitionDate)
            assertEquals(GregorianCalendar(1997, 8, 13).timeInMillis, rewardEntityExtracted.escapingDate)
            assertEquals(true, rewardEntityExtracted.isActive)
            assertEquals(false, rewardEntityExtracted.isEscaped)
            assertEquals("getOneRewardTest is my name", rewardEntityExtracted.name)
            assertEquals("getOneRewardTest legs color", rewardEntityExtracted.legsColor)
            assertEquals("getOneRewardTest body color", rewardEntityExtracted.bodyColor)
            assertEquals("getOneRewardTest arms color", rewardEntityExtracted.armsColor)
        }
    }

    @Test
    fun updateOneRewardTestOnEmptyTable(){
        checkTotalCountIs(0)
        val rewardEntityToUpdate = generateReward(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            rewardDao.updateReward(rewardEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(0)
    }

    @Test
    fun updateNonExistentRewardTest(){
        runBlocking {
            rewardDao.insert(generateReward(desiredId = 25))
            rewardDao.insert(generateReward(desiredId = 26))
            rewardDao.insert(generateReward(desiredId = 27))
            rewardDao.insert(generateReward(desiredId = 28))
            rewardDao.insert(generateReward(desiredId = 29))
        }
        checkTotalCountIs(5)
        val rewardEntityToUpdate = generateReward(desiredId = 73)
        val numberOfUpdatedItems = runBlocking {
            rewardDao.updateReward(rewardEntityToUpdate)
        }
        assertEquals(0, numberOfUpdatedItems)
        checkTotalCountIs(5)
    }

    @Test
    fun updateOneRewardTest(){
        val rewardEntity = generateReward(
            desiredLevel = 5,
            active = true,
            escaped = false,
            desiredId = 43,
            yearAcquired = 1987,
            monthAcquired = 2,
            dayAcquired = 9,
            yearEscaped = 1995,
            monthEscaped = 9,
            dayEscaped = 11,
            desiredName = "updateOneRewardTest is my name",
            desiredLegsColor = "updateOneRewardTest legs color",
            desiredBodyColor = "updateOneRewardTest body color",
            desiredArmsColor = "updateOneRewardTest arms color")
        val rewardCode = rewardEntity.code
        val rewardEntityInsertedTestID = runBlocking {
            rewardDao.insert(rewardEntity)
        }
        assertNotNull(rewardEntityInsertedTestID)
        assertEquals(43L, rewardEntityInsertedTestID)

        runBlocking {
            rewardDao.insert(generateRewards(53))
        }
        checkTotalCountIs(54)
        //
        rewardEntity.acquisitionDate = GregorianCalendar(2019, 6, 16).timeInMillis
        rewardEntity.escapingDate = GregorianCalendar(2020, 5, 22).timeInMillis
        rewardEntity.isActive = false
        rewardEntity.isEscaped = true
        rewardEntity.name = "I have updated my name"
        rewardEntity.legsColor = "#00FF000000"
        rewardEntity.bodyColor = "#0000FF00"
        rewardEntity.armsColor = "#FFFFFFFF"
        //
        val numberOfUpdatedItems = runBlocking {
            rewardDao.updateReward(rewardEntity)
        }
        assertEquals(1, numberOfUpdatedItems)
        checkTotalCountIs(54)
        val rewardEntityUpdatedLive = rewardDao.getRewardLive(rewardEntity.id)
        assertNotNull(rewardEntityUpdatedLive)
        val rewardEntityUpdated = rewardEntityUpdatedLive.getValueBlocking()
        assertNotNull(rewardEntityUpdated)
        if (rewardEntityUpdated != null) {
            assertEquals(43, rewardEntityUpdated.id)
            assertEquals(5, rewardEntityUpdated.level)
            assertEquals(rewardCode, rewardEntityUpdated.code)
            assertEquals(GregorianCalendar(2019, 6, 16).timeInMillis, rewardEntityUpdated.acquisitionDate)
            assertEquals(GregorianCalendar(2020, 5, 22).timeInMillis, rewardEntityUpdated.escapingDate)
            assertEquals(false, rewardEntityUpdated.isActive)
            assertEquals(true, rewardEntityUpdated.isEscaped)
            assertEquals("I have updated my name", rewardEntityUpdated.name)
            assertEquals("#00FF000000", rewardEntityUpdated.legsColor)
            assertEquals("#0000FF00", rewardEntityUpdated.bodyColor)
            assertEquals("#FFFFFFFF", rewardEntityUpdated.armsColor)
        }
        //

    }

    @Test
    fun updateMultipleRewardsTest(){
        val rewardsToTestUpdateList = listOf(
            generateReward(desiredId = 13),
            generateReward(desiredId = 17),
            generateReward(desiredId = 23),
            generateReward(desiredId = 37))
        val insertedIds = runBlocking {
            rewardDao.insert(rewardsToTestUpdateList)
        }
        assertEquals(4, insertedIds.size)
        //
        rewardsToTestUpdateList[0].acquisitionDate = GregorianCalendar(2019, 1, 16).timeInMillis
        rewardsToTestUpdateList[0].escapingDate = GregorianCalendar(2020, 1, 22).timeInMillis
        rewardsToTestUpdateList[0].isActive = false
        rewardsToTestUpdateList[0].isEscaped = true
        rewardsToTestUpdateList[0].name = "name updated 0"
        rewardsToTestUpdateList[0].legsColor = "legsColor updated 0"
        rewardsToTestUpdateList[0].bodyColor = "bodyColor updated 0"
        rewardsToTestUpdateList[0].armsColor = "armsColor updated 0"
        //
        rewardsToTestUpdateList[1].acquisitionDate = GregorianCalendar(2019, 2, 16).timeInMillis
        rewardsToTestUpdateList[1].escapingDate = GregorianCalendar(2020, 2, 22).timeInMillis
        rewardsToTestUpdateList[1].isActive = true
        rewardsToTestUpdateList[1].isEscaped = false
        rewardsToTestUpdateList[1].name = "name updated 1"
        rewardsToTestUpdateList[1].legsColor = "legsColor updated 1"
        rewardsToTestUpdateList[1].bodyColor = "bodyColor updated 1"
        rewardsToTestUpdateList[1].armsColor = "armsColor updated 1"
        //
        rewardsToTestUpdateList[2].acquisitionDate = GregorianCalendar(2019, 3, 16).timeInMillis
        rewardsToTestUpdateList[2].escapingDate = GregorianCalendar(2020, 3, 22).timeInMillis
        rewardsToTestUpdateList[2].isActive = true
        rewardsToTestUpdateList[2].isEscaped = true
        rewardsToTestUpdateList[2].name = "name updated 2"
        rewardsToTestUpdateList[2].legsColor = "legsColor updated 2"
        rewardsToTestUpdateList[2].bodyColor = "bodyColor updated 2"
        rewardsToTestUpdateList[2].armsColor = "armsColor updated 2"
        //
        rewardsToTestUpdateList[3].acquisitionDate = GregorianCalendar(2019, 4, 16).timeInMillis
        rewardsToTestUpdateList[3].escapingDate = GregorianCalendar(2020, 4, 22).timeInMillis
        rewardsToTestUpdateList[3].isActive = false
        rewardsToTestUpdateList[3].isEscaped = false
        rewardsToTestUpdateList[3].name = "name updated 3"
        rewardsToTestUpdateList[3].legsColor = "legsColor updated 3"
        rewardsToTestUpdateList[3].bodyColor = "bodyColor updated 3"
        rewardsToTestUpdateList[3].armsColor = "armsColor updated 3"
        //
        val numberOfUpdatedItems = runBlocking {
            rewardDao.updateRewards(rewardsToTestUpdateList)
        }
        assertEquals(4, numberOfUpdatedItems)
        //
        var rewardEntityUpdatedLive = rewardDao.getRewardLive(rewardsToTestUpdateList[0].id)
        assertNotNull(rewardEntityUpdatedLive)
        val rewardEntityUpdated0 = rewardEntityUpdatedLive.getValueBlocking()
        assertNotNull(rewardEntityUpdated0)
        if (rewardEntityUpdated0 != null) {
            assertEquals(rewardsToTestUpdateList[0].id, rewardEntityUpdated0.id)
            assertEquals(rewardsToTestUpdateList[0].level, rewardEntityUpdated0.level)
            assertEquals(rewardsToTestUpdateList[0].code, rewardEntityUpdated0.code)
            assertEquals(GregorianCalendar(2019, 1, 16).timeInMillis, rewardEntityUpdated0.acquisitionDate)
            assertEquals(GregorianCalendar(2020, 1, 22).timeInMillis, rewardEntityUpdated0.escapingDate)
            assertEquals(false, rewardEntityUpdated0.isActive)
            assertEquals(true, rewardEntityUpdated0.isEscaped)
            assertEquals("name updated 0", rewardEntityUpdated0.name)
            assertEquals("legsColor updated 0", rewardEntityUpdated0.legsColor)
            assertEquals("bodyColor updated 0", rewardEntityUpdated0.bodyColor)
            assertEquals("armsColor updated 0", rewardEntityUpdated0.armsColor)
        }
        //
        rewardEntityUpdatedLive = rewardDao.getRewardLive(rewardsToTestUpdateList[1].id)
        assertNotNull(rewardEntityUpdatedLive)
        val rewardEntityUpdated1 = rewardEntityUpdatedLive.getValueBlocking()
        assertNotNull(rewardEntityUpdated1)
        if (rewardEntityUpdated1 != null) {
            assertEquals(rewardsToTestUpdateList[1].id, rewardEntityUpdated1.id)
            assertEquals(rewardsToTestUpdateList[1].level, rewardEntityUpdated1.level)
            assertEquals(rewardsToTestUpdateList[1].code, rewardEntityUpdated1.code)
            assertEquals(GregorianCalendar(2019, 2, 16).timeInMillis, rewardEntityUpdated1.acquisitionDate)
            assertEquals(GregorianCalendar(2020, 2, 22).timeInMillis, rewardEntityUpdated1.escapingDate)
            assertEquals(true, rewardEntityUpdated1.isActive)
            assertEquals(false, rewardEntityUpdated1.isEscaped)
            assertEquals("name updated 1", rewardEntityUpdated1.name)
            assertEquals("legsColor updated 1", rewardEntityUpdated1.legsColor)
            assertEquals("bodyColor updated 1", rewardEntityUpdated1.bodyColor)
            assertEquals("armsColor updated 1", rewardEntityUpdated1.armsColor)
        }
        //
        rewardEntityUpdatedLive = rewardDao.getRewardLive(rewardsToTestUpdateList[2].id)
        assertNotNull(rewardEntityUpdatedLive)
        val rewardEntityUpdated2 = rewardEntityUpdatedLive.getValueBlocking()
        assertNotNull(rewardEntityUpdated2)
        if (rewardEntityUpdated2 != null) {
            assertEquals(rewardsToTestUpdateList[2].id, rewardEntityUpdated2.id)
            assertEquals(rewardsToTestUpdateList[2].level, rewardEntityUpdated2.level)
            assertEquals(rewardsToTestUpdateList[2].code, rewardEntityUpdated2.code)
            assertEquals(GregorianCalendar(2019, 3, 16).timeInMillis, rewardEntityUpdated2.acquisitionDate)
            assertEquals(GregorianCalendar(2020, 3, 22).timeInMillis, rewardEntityUpdated2.escapingDate)
            assertEquals(true, rewardEntityUpdated2.isActive)
            assertEquals(true, rewardEntityUpdated2.isEscaped)
            assertEquals("name updated 2", rewardEntityUpdated2.name)
            assertEquals("legsColor updated 2", rewardEntityUpdated2.legsColor)
            assertEquals("bodyColor updated 2", rewardEntityUpdated2.bodyColor)
            assertEquals("armsColor updated 2", rewardEntityUpdated2.armsColor)
        }
        //
        rewardEntityUpdatedLive = rewardDao.getRewardLive(rewardsToTestUpdateList[3].id)
        assertNotNull(rewardEntityUpdatedLive)
        val rewardEntityUpdated3 = rewardEntityUpdatedLive.getValueBlocking()
        assertNotNull(rewardEntityUpdated3)
        if (rewardEntityUpdated3 != null) {
            assertEquals(rewardsToTestUpdateList[3].id, rewardEntityUpdated3.id)
            assertEquals(rewardsToTestUpdateList[3].level, rewardEntityUpdated3.level)
            assertEquals(rewardsToTestUpdateList[3].code, rewardEntityUpdated3.code)
            assertEquals(GregorianCalendar(2019, 4, 16).timeInMillis, rewardEntityUpdated3.acquisitionDate)
            assertEquals(GregorianCalendar(2020, 4, 22).timeInMillis, rewardEntityUpdated3.escapingDate)
            assertEquals(false, rewardEntityUpdated3.isActive)
            assertEquals(false, rewardEntityUpdated3.isEscaped)
            assertEquals("name updated 3", rewardEntityUpdated3.name)
            assertEquals("legsColor updated 3", rewardEntityUpdated3.legsColor)
            assertEquals("bodyColor updated 3", rewardEntityUpdated3.bodyColor)
            assertEquals("armsColor updated 3", rewardEntityUpdated3.armsColor)
        }
        //

    }

//////////////////////////////////////////////////////////////////


    @Test
    fun getAllRewardsActiveAcquisitionDateAscTest() {
        val rewardsToInsertList = listOf(
            generateReward(active = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            generateReward(active = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            generateReward(active = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            generateReward(active = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            generateReward(active = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            generateReward(active = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            generateReward(active = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardEntitySortedLive = rewardDao.getAllRewardsActiveAcquisitionDateAsc()
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted)
        if(rewardEntitySorted != null){
            assertEquals(4, rewardEntitySorted.size)
            var date = 0L
            for(i in rewardEntitySorted.indices){
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].acquisitionDate >= date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }

    }

    @Test
    fun getAllRewardsActiveAcquisitionDateDescTest() {

        val rewardsToInsertList = listOf(
            generateReward(active = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            generateReward(active = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            generateReward(active = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            generateReward(active = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            generateReward(active = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            generateReward(active = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            generateReward(active = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardEntitySortedLive = rewardDao.getAllRewardsActiveAcquisitionDateDesc()
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted)
        if(rewardEntitySorted != null){
            assertEquals(4, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[3].isActive)
            var date = rewardEntitySorted[3].acquisitionDate
            for(i in 2 downTo 0){
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].acquisitionDate <= date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }

    }

    @Test
    fun getAllRewardsActiveLevelAscTest() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(42, desiredLevel = 2, active = true))
        rewardsToInsertList.addAll(generateRewards(51, desiredLevel = 3, active = true))
        rewardsToInsertList.addAll(generateRewards(67, desiredLevel = 4, active = true))
        rewardsToInsertList.addAll(generateRewards(83, desiredLevel = 5, active = true))
        rewardsToInsertList.addAll(generateRewards(21, desiredLevel = 2, active = false))
        rewardsToInsertList.addAll(generateRewards(34, desiredLevel = 3, active = false))
        rewardsToInsertList.addAll(generateRewards(47, desiredLevel = 4, active = false))
        rewardsToInsertList.addAll(generateRewards(59, desiredLevel = 5, active = false))
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(404)
        val rewardEntitySortedLive = rewardDao.getAllRewardsActiveLevelAsc()
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(243, rewardEntitySorted.size)
            var level = 0
            for(i in rewardEntitySorted.indices) {
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].level <= level)
                level = rewardEntitySorted[i].level
            }
        }
        //

    }

    @Test
    fun getAllRewardsActiveLevelDescTest() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(29, desiredLevel = 2, active = true))
        rewardsToInsertList.addAll(generateRewards(19, desiredLevel = 3, active = true))
        rewardsToInsertList.addAll(generateRewards(42, desiredLevel = 4, active = true))
        rewardsToInsertList.addAll(generateRewards(81, desiredLevel = 5, active = true))
        rewardsToInsertList.addAll(generateRewards(8, desiredLevel = 2, active = false))
        rewardsToInsertList.addAll(generateRewards(17, desiredLevel = 3, active = false))
        rewardsToInsertList.addAll(generateRewards(38, desiredLevel = 4, active = false))
        rewardsToInsertList.addAll(generateRewards(91, desiredLevel = 5, active = false))
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(325)

        val rewardEntitySortedLive = rewardDao.getAllRewardsActiveLevelDesc()
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(171, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[rewardEntitySorted.size - 1].isActive)
            var level = rewardEntitySorted[rewardEntitySorted.size - 1].level
            for(i in (rewardEntitySorted.size - 1) downTo 0){
                assertEquals(true, rewardEntitySorted[i].isActive)
                assert(rewardEntitySorted[i].level <= level)
                level = rewardEntitySorted[i].level
                assertEquals(level, rewardEntitySorted[i].level)
            }
        }
        //

    }

    @Test
    fun getAllRewardsNotEscapedAcquisitionDatDescTest() {

        val rewardsToInsertList = listOf(
            generateReward(escaped = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            generateReward(escaped = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            generateReward(escaped = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            generateReward(escaped = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            generateReward(escaped = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            generateReward(escaped = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            generateReward(escaped = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardEntitySortedLive = rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc()
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(3, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[2].isActive)
            assertEquals(false, rewardEntitySorted[2].isEscaped)
            var date = rewardEntitySorted[2].acquisitionDate
            for(i in 1 downTo 0){
                assertEquals(true, rewardEntitySorted[i].isActive)
                assertEquals(false, rewardEntitySorted[i].isEscaped)
                assert(rewardEntitySorted[i].acquisitionDate < date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }
        //

    }

    @Test
    fun getAllRewardsEscapedAcquisitionDateDescTest() {

        val rewardsToInsertList = listOf(
            generateReward(escaped = true, yearAcquired = 1987, monthAcquired = 2,dayAcquired = 9),
            generateReward(escaped = false, yearAcquired = 1985, monthAcquired = 3,dayAcquired = 11),
            generateReward(escaped = true, yearAcquired = 1984, monthAcquired = 4,dayAcquired = 13),
            generateReward(escaped = false, yearAcquired = 1983, monthAcquired = 5,dayAcquired = 15),
            generateReward(escaped = true, yearAcquired = 1982, monthAcquired = 6,dayAcquired = 17),
            generateReward(escaped = true, yearAcquired = 1981, monthAcquired = 7,dayAcquired = 19),
            generateReward(escaped = false, yearAcquired = 1980, monthAcquired = 8,dayAcquired = 21)
        )
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(7)
        val rewardEntitySortedLive = rewardDao.getAllRewardsEscapedAcquisitionDateDesc()
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted)
        if (rewardEntitySorted != null) {
            assertEquals(4, rewardEntitySorted.size)
            assertEquals(true, rewardEntitySorted[rewardEntitySorted.size - 1].isActive)
            assertEquals(true, rewardEntitySorted[rewardEntitySorted.size - 1].isEscaped)
            var date = rewardEntitySorted[rewardEntitySorted.size - 1].acquisitionDate
            for(i in (rewardEntitySorted.size - 1) downTo 0){
                assertEquals(true, rewardEntitySorted[i].isActive)
                assertEquals(true, rewardEntitySorted[i].isEscaped)
                assert(rewardEntitySorted[i].acquisitionDate < date)
                date = rewardEntitySorted[i].acquisitionDate
                assertEquals(date, rewardEntitySorted[i].acquisitionDate)
            }
        }
        //

    }

    @Test
    fun getAllRewardsOfSPecificLevelNotActiveTest() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(5, 3, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(3, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(3, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(6, 4, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 4, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 4, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(2, 5, active = true, escaped = true))
        //
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(33)
        //
        var rewardEntitySortedLive = rewardDao.getAllRewardsOfSPecificLevelNotActive(3)
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted3 = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted3)
        if (rewardEntitySorted3 != null) {
            assertEquals(5, rewardEntitySorted3.size)
            for(rewardEntity in rewardEntitySorted3){
                assertEquals(false, rewardEntity.isActive)
                assertEquals(false, rewardEntity.isEscaped)
            }
        }
        //
        rewardEntitySortedLive = rewardDao.getAllRewardsOfSPecificLevelNotActive(4)
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted4 = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted4)
        if (rewardEntitySorted4 != null) {
            assertEquals(6, rewardEntitySorted4.size)
            for(rewardEntity in rewardEntitySorted4){
                assertEquals(false, rewardEntity.isActive)
                assertEquals(false, rewardEntity.isEscaped)
            }
        }
        //
        rewardEntitySortedLive = rewardDao.getAllRewardsOfSPecificLevelNotActive(5)
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted5 = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted5)
        if (rewardEntitySorted5 != null) {
            assertEquals(0, rewardEntitySorted5.size)
        }
        //

    }

    @Test
    fun getAllRewardsOfSPecificLevelNotActiveOrEscapedTest() {

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(5, 3, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(6, 4, active = false, escaped = false))
        rewardsToInsertList.addAll(generateRewards(3, 4, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(8, 4, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(9, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(2, 5, active = true, escaped = false))
        //
        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(40)
        //
        var rewardEntitySortedLive = rewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(3)
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted3 = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted3)
        if (rewardEntitySorted3 != null) {
            assertEquals(12, rewardEntitySorted3.size)
            for(rewardEntity in rewardEntitySorted3){
                val desiredState = (rewardEntity.isActive && rewardEntity.isEscaped) || (!rewardEntity.isActive && !rewardEntity.isEscaped)
                assertEquals(true, desiredState)
            }
        }
        //
        rewardEntitySortedLive = rewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(4)
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted4 = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted4)
        if (rewardEntitySorted4 != null) {
            assertEquals(9, rewardEntitySorted4.size)
            for(rewardEntity in rewardEntitySorted4){
                val desiredState = (rewardEntity.isActive && rewardEntity.isEscaped) || (!rewardEntity.isActive && !rewardEntity.isEscaped)
                assertEquals(true, desiredState)
            }
        }
        //
        rewardEntitySortedLive = rewardDao.getAllRewardsOfSPecificLevelNotActiveOrEscaped(5)
        assertNotNull(rewardEntitySortedLive)
        val rewardEntitySorted5 = rewardEntitySortedLive.getValueBlocking()
        assertNotNull(rewardEntitySorted5)
        if (rewardEntitySorted5 != null) {
            assertEquals(0, rewardEntitySorted5.size)
        }
        //

    }

////////////////////////////////////////////////////////////////
    @Test
    fun countRewardsTest(){

        runBlocking {
            rewardDao.insert(generateRewards(125))
        }
        val totalRewards = runBlocking {
            rewardDao.getNumberOfRows()
        }
        assertEquals(125, totalRewards)
        checkTotalCountIs(125)

    }

    @Test
    fun countRewardsActiveNotEscapedLevelTest(){

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(3, 1, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(5, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(9, 5, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(13, 5, active = true, escaped = true))

        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(37)
        val numberLevel1 = runBlocking {
            rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(1)
        }
        assertEquals(0, numberLevel1)
        //
        val numberLevel3 = runBlocking {
            rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(3)
        }
        assertEquals(5, numberLevel3)
        //
        val numberLevel5 = runBlocking {
            rewardDao.getNumberOfActiveNotEscapedRewardsForLevel(5)
        }
        assertEquals(9, numberLevel5)

    }

    @Test
    fun countRewardsEscapedLevelTest(){

        val rewardsToInsertList = mutableListOf<RewardEntity>()
        rewardsToInsertList.addAll(generateRewards(3, 1, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(5, 3, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(7, 3, active = true, escaped = true))
        rewardsToInsertList.addAll(generateRewards(9, 5, active = true, escaped = false))
        rewardsToInsertList.addAll(generateRewards(13, 5, active = true, escaped = true))

        runBlocking {
            rewardDao.insert(rewardsToInsertList)
        }
        checkTotalCountIs(37)
        val numberLevel1 = runBlocking {
            rewardDao.getNumberOfEscapedRewardsForLevel(1)
        }
        assertEquals(0, numberLevel1)
        //
        val numberLevel3 = runBlocking {
            rewardDao.getNumberOfEscapedRewardsForLevel(3)
        }
        assertEquals(7, numberLevel3)
        //
        val numberLevel5 = runBlocking {
            rewardDao.getNumberOfEscapedRewardsForLevel(5)
        }
        assertEquals(13, numberLevel5)

    }

}