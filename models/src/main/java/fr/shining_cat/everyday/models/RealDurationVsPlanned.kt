package fr.shining_cat.everyday.models

enum class RealDurationVsPlanned(val key: Int){
    EQUAL(0),
    REAL_SHORTER(-1),
    REAL_LONGER(1);

    //TODO: WRITE TEST
    companion object {
        fun fromKey(key: Int): RealDurationVsPlanned {
            return when {
                key < 0 -> REAL_SHORTER
                key > 0 -> REAL_LONGER
                else -> EQUAL
            }
        }
    }
}