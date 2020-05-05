package fr.shining_cat.everyday.commons.di

import fr.shining_cat.everyday.commons.AppDispatchers
import fr.shining_cat.everyday.commons.Logger
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonsModule = module {

    single { Logger() }

    factory {
        AppDispatchers(
            Dispatchers.Main,
            Dispatchers.IO
        )
    }
}