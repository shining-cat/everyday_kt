package fr.shining_cat.everyday.repository.repo

import fr.shining_cat.everyday.commons.ui.resourcesholders.critter.CritterPartResourcesHolder

interface CritterPartsRepository {
    suspend fun getArmsResourcesHolder(): CritterPartResourcesHolder
    suspend fun getEyesResourcesHolder(): CritterPartResourcesHolder
    suspend fun getFlowerResourcesHolder(): CritterPartResourcesHolder
    suspend fun getHornsResourcesHolder(): CritterPartResourcesHolder
    suspend fun getLegsResourcesHolder(): CritterPartResourcesHolder
    suspend fun getMouthResourcesHolder(): CritterPartResourcesHolder
}

//returns from this repo are based on hard-coded objects, they will not be encapsulated inside Output objects because there is no failure possible retrieving them
class CritterPartsRepositoryImpl : CritterPartsRepository {

    var armsHolder: CritterPartResourcesHolder? = null
    var eyesHolder: CritterPartResourcesHolder? = null
    var flowerHolder: CritterPartResourcesHolder? = null
    var hornsHolder: CritterPartResourcesHolder? = null
    var legsHolder: CritterPartResourcesHolder? = null
    var mouthHolder: CritterPartResourcesHolder? = null

    override suspend fun getArmsResourcesHolder(): CritterPartResourcesHolder =
        armsHolder ?: CritterPartResourcesHolder.ArmsResourcesHolder()

    override suspend fun getEyesResourcesHolder(): CritterPartResourcesHolder =
        eyesHolder ?: CritterPartResourcesHolder.EyesResourcesHolder()

    override suspend fun getFlowerResourcesHolder(): CritterPartResourcesHolder =
        flowerHolder ?: CritterPartResourcesHolder.FlowerResourcesHolder()

    override suspend fun getHornsResourcesHolder(): CritterPartResourcesHolder =
        hornsHolder ?: CritterPartResourcesHolder.HornsResourcesHolder()

    override suspend fun getLegsResourcesHolder(): CritterPartResourcesHolder =
        legsHolder ?: CritterPartResourcesHolder.LegsResourcesHolder()

    override suspend fun getMouthResourcesHolder(): CritterPartResourcesHolder =
        mouthHolder ?: CritterPartResourcesHolder.MouthResourcesHolder()
}