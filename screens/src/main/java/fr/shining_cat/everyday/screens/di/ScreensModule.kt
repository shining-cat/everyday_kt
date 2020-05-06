package fr.shining_cat.everyday.screens.di

import fr.shining_cat.everyday.screens.views.home.HomeViewModel
import fr.shining_cat.everyday.screens.views.rewards.RewardsViewModel
import fr.shining_cat.everyday.screens.views.sessions.SessionsViewModel
import fr.shining_cat.everyday.screens.views.statistics.StatisticsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screensModule = module {
    // View models
    viewModel { HomeViewModel(get(), get()) }
    viewModel { StatisticsViewModel(get(), get()) }
    viewModel { SessionsViewModel(get(), get()) }
    viewModel { RewardsViewModel(get(), get()) }
}