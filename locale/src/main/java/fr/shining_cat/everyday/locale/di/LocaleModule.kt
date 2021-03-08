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

package fr.shining_cat.everyday.locale.di

import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localeModule = module {
    single { EveryDayRoomDatabase.getInstance(androidContext()) }
    single { (get() as EveryDayRoomDatabase).rewardDao() }
    single { (get() as EveryDayRoomDatabase).sessionPresetDao() }
    single { (get() as EveryDayRoomDatabase).sessionRecordDao() }
    single { (get() as EveryDayRoomDatabase).sessionTypeDao() }
}