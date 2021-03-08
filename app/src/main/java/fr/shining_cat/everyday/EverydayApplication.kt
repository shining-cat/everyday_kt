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

package fr.shining_cat.everyday

import android.app.Application
import com.facebook.stetho.Stetho
import fr.shining_cat.everyday.commons.di.commonsModule
import fr.shining_cat.everyday.di.appModule
import fr.shining_cat.everyday.domain.di.domainModule
import fr.shining_cat.everyday.locale.di.localeModule
import fr.shining_cat.everyday.models.di.modelsModule
import fr.shining_cat.everyday.repository.di.repositoriesModule
import fr.shining_cat.everyday.screens.di.screensModule
import fr.shining_cat.everyday.settings.di.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EverydayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
        configureStetho()
    }

    fun configureDi() {
        startKoin {

            androidContext(this@EverydayApplication)

            modules(
                listOf(
                    appModule,
                    commonsModule,
                    localeModule,
                    modelsModule,
                    repositoriesModule,
                    screensModule,
                    domainModule,
                    settingsModule
                )
            )
        }
    }

    private fun configureStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}