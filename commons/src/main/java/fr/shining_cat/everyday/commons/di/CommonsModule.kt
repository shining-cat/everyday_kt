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

package fr.shining_cat.everyday.commons.di

import android.content.Context
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelperSettings
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val commonsModule = module {

    single { Logger() }
    single { SharedPrefsHelper(get()) }
    single {
        (get() as Context).getSharedPreferences(
            SharedPrefsHelperSettings.NAME,
            Context.MODE_PRIVATE
        )
    }

    factory {
        AppDispatchers(
            Dispatchers.Main,
            Dispatchers.IO
        )
    }
}