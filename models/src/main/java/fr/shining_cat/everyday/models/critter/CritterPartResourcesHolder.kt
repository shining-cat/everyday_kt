package fr.shining_cat.everyday.models.critter

abstract class CritterPartResourcesHolder {

    abstract val partsCount: Int
    abstract val firstItemIsOff: Boolean

    abstract fun getDefaultPartResourceId(): Int
    abstract fun getResourceIdForKey(key: Int): Int
}