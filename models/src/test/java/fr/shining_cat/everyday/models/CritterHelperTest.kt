package fr.shining_cat.everyday.models

import fr.shining_cat.everyday.models.critter.*
import fr.shining_cat.everyday.testutils.AbstractBaseTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CritterHelperTest : AbstractBaseTest() {
//TODO: if CritterHelper is indeed deleted, delete this test class too

//    @Mock
//    private lateinit var mockFlowerResourcesHolder: FlowerResourcesHolder
//    @Mock
//    private lateinit var mockMouthResourcesHolder: MouthResourcesHolder
//    @Mock
//    private lateinit var mockLegsResourcesHolder: LegsResourcesHolder
//    @Mock
//    private lateinit var mockEyesResourcesHolder: EyesResourcesHolder
//    @Mock
//    private lateinit var mockArmsResourcesHolder: ArmsResourcesHolder
//    @Mock
//    private lateinit var mockHornsResourcesHolder: HornsResourcesHolder
//
//
//    private lateinit var critterHelper: CritterHelper
//    private val fakeCode = "1_3_5_7_9_11"
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        Assert.assertNotNull(mockFlowerResourcesHolder)
//        Assert.assertNotNull(mockMouthResourcesHolder)
//        Assert.assertNotNull(mockLegsResourcesHolder)
//        Assert.assertNotNull(mockArmsResourcesHolder)
//        Assert.assertNotNull(mockEyesResourcesHolder)
//        Assert.assertNotNull(mockHornsResourcesHolder)
//        critterHelper = CritterHelper(
//            mockFlowerResourcesHolder,
//            mockMouthResourcesHolder,
//            mockLegsResourcesHolder,
//            mockArmsResourcesHolder,
//            mockEyesResourcesHolder,
//            mockHornsResourcesHolder
//        )
//        Mockito.`when`(mockFlowerResourcesHolder.getPartsCount()).thenReturn(6)
//        Mockito.`when`(mockMouthResourcesHolder.getPartsCount()).thenReturn(6)
//        Mockito.`when`(mockLegsResourcesHolder.getPartsCount()).thenReturn(6)
//        Mockito.`when`(mockArmsResourcesHolder.getPartsCount()).thenReturn(6)
//        Mockito.`when`(mockEyesResourcesHolder.getPartsCount()).thenReturn(6)
//        Mockito.`when`(mockHornsResourcesHolder.getPartsCount()).thenReturn(6)
//    }
//
//    @Test
//    fun getLevelTest() {
//        val fakeNumberOfVersionsForEachRewardPart = 13
//        for (i in 1..fakeNumberOfVersionsForEachRewardPart) {
//            for (j in 1..fakeNumberOfVersionsForEachRewardPart) {
//                assertEquals(Level.LEVEL_1, critterHelper.getLevel("${i}_${j}_0_0_0_0"))
//                for (k in 1..fakeNumberOfVersionsForEachRewardPart) {
//                    assertEquals(
//                        Level.LEVEL_2,
//                        critterHelper.getLevel("${i}_${j}_${k}_0_0_0")
//                    )
//                    for (l in 1..fakeNumberOfVersionsForEachRewardPart) {
//                        assertEquals(
//                            Level.LEVEL_3,
//                            critterHelper.getLevel("${i}_${j}_${k}_${l}_0_0")
//                        )
//                        for (m in 1..fakeNumberOfVersionsForEachRewardPart) {
//                            assertEquals(
//                                Level.LEVEL_4,
//                                critterHelper.getLevel("${i}_${j}_${k}_${l}_${m}_0")
//                            )
//                            for (n in 1..fakeNumberOfVersionsForEachRewardPart) {
//                                assertEquals(
//                                    Level.LEVEL_5,
//                                    critterHelper.getLevel("${i}_${j}_${k}_${l}_${m}_${n}")
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Test
//    fun getRandomCritterCodeTest() {
//        for (level in 0..4) {
//            for (i in 1..73) { //arbitrary number of test repetitions
//                assertEquals(
//                    Level.fromKey(level),
//                    critterHelper.getLevel(
//                        critterHelper.getRandomCritterCode(
//                            Level.fromKey(level)
//                        )
//                    )
//                )
//            }
//        }
//    }
//
//    @Test
//    fun getFlowerDrawableResourceTest() {
//        val flower = critterHelper.getFlowerDrawableResource(fakeCode)
//        Mockito.`when`(mockFlowerResourcesHolder.getFlowerDrawableResourceId(1))
//            .thenReturn(3)
//        assertEquals(3, flower)
//    }

}