package fr.shining_cat.everyday.commons.di

import android.content.Context
import fr.shining_cat.everyday.commons.viewmodels.AppDispatchers
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelper
import fr.shining_cat.everyday.commons.helpers.SharedPrefsHelperSettings
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