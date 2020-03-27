package fr.shining_cat.everyday.models

enum class MoodValue(val key: Int){
    WORST(-2),
    BAD(-1),
    NOT_SET(0),
    GOOD(1),
    BEST(2);

    //TODO: WRITE TEST
    companion object {
        fun fromKey(key: Int?): MoodValue {
            return when (key) {
                -2 -> MoodValue.WORST
                -1 -> MoodValue.BAD
                1 -> MoodValue.GOOD
                2 -> MoodValue.BEST
                else -> MoodValue.NOT_SET
            }
        }
    }
}

