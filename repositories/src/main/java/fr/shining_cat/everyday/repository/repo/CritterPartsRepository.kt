/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

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

// returns from this repo are based on hard-coded objects, they will not be encapsulated inside Output objects because there is no failure possible retrieving them
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
