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

package fr.shining_cat.everyday.screens.di

import fr.shining_cat.everyday.screens.viewmodels.HomeViewModel
import fr.shining_cat.everyday.screens.viewmodels.RewardsViewModel
import fr.shining_cat.everyday.screens.viewmodels.SessionsViewModel
import fr.shining_cat.everyday.screens.viewmodels.StatisticsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screensModule = module {
    // View models
    viewModel { HomeViewModel(get(), get()) }
    viewModel { StatisticsViewModel(get(), get()) }
    viewModel { SessionsViewModel(get(), get()) }
    viewModel { RewardsViewModel(get(), get()) }
}
