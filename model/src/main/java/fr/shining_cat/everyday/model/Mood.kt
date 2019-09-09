package fr.shining_cat.everyday.model

data class Mood(
    val timeOfRecord: Long,
    //values range from 1 (WORST) to 5 (BEST), 0 is for NOT SET
    val bodyValue: Int,
    val thoughtsValue: Int,
    val feelingsValue: Int,
    val globalValue: Int)
   {

    override fun toString() = "MOOD : timeStamp = " + timeOfRecord +
                                "\n\tBody = " + bodyValue +
                                "\n\tThoughts = " + thoughtsValue +
                                "\n\tFeelings = " + feelingsValue +
                                "\n\tGlobal = " + globalValue
}

object MoodConstants{
    const val NO_VALUE_SET: Int = 0
}