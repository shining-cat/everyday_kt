package fr.shining_cat.everyday.di

import fr.shining_cat.everyday.viewmodels.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel(get(), get()) }
}