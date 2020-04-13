package fr.shining_cat.everyday.models

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CritterHelperTest {

    private lateinit var critterHelper: CritterHelper

    @Before
    fun setUp() {
        critterHelper = CritterHelper()
    }

    @Test
    fun getLevelTest() {
        val fakeNumberOfVersionsForEachRewardPart = 13
        for (i in 1..fakeNumberOfVersionsForEachRewardPart) {
            for (j in 1..fakeNumberOfVersionsForEachRewardPart) {
                assertEquals(CritterLevel.LEVEL_1, critterHelper.getLevel("${i}_${j}_0_0_0_0"))
                for (k in 1..fakeNumberOfVersionsForEachRewardPart) {
                    assertEquals(
                        CritterLevel.LEVEL_2,
                        critterHelper.getLevel("${i}_${j}_${k}_0_0_0")
                    )
                    for (l in 1..fakeNumberOfVersionsForEachRewardPart) {
                        assertEquals(
                            CritterLevel.LEVEL_3,
                            critterHelper.getLevel("${i}_${j}_${k}_${l}_0_0")
                        )
                        for (m in 1..fakeNumberOfVersionsForEachRewardPart) {
                            assertEquals(
                                CritterLevel.LEVEL_4,
                                critterHelper.getLevel("${i}_${j}_${k}_${l}_${m}_0")
                            )
                            for (n in 1..fakeNumberOfVersionsForEachRewardPart) {
                                assertEquals(
                                    CritterLevel.LEVEL_5,
                                    critterHelper.getLevel("${i}_${j}_${k}_${l}_${m}_${n}")
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun getRandomCritterCodeTest() {
        for (level in 0..4) {
        for (i in 1..73) { //arbitrary number of test repetitions
                assertEquals(
                    CritterLevel.fromKey(level),
                    critterHelper.getLevel(
                        critterHelper.getRandomCritterCode(
                            CritterLevel.fromKey(level)
                        )
                    )
                )
            }
        }
    }
}