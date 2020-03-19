package fr.shining_cat.everyday.models

data class Mood(
    val timeOfRecord: Long,
    //values range from 1 (WORST) to 5 (BEST), 0 is for NOT SET
    val bodyValue: MoodValue,
    val thoughtsValue: MoodValue,
    val feelingsValue: MoodValue,
    val globalValue: MoodValue)
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

enum class MoodValue{
    WORST,
    BAD,
    NOT_SET,
    GOOD,
    BEST
}