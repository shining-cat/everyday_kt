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

package fr.shining_cat.everyday.repository.di

import fr.shining_cat.everyday.repository.converters.RewardConverter
import fr.shining_cat.everyday.repository.converters.SessionPresetConverter
import fr.shining_cat.everyday.repository.converters.SessionRecordConverter
import fr.shining_cat.everyday.repository.repo.*
import org.koin.dsl.module

val repositoriesModule = module {

    // Converters
    factory {RewardConverter(get())}
    factory {SessionRecordConverter(get())}
    factory {SessionPresetConverter(get())}

    // Repositories
    factory {CritterPartsRepositoryImpl() as CritterPartsRepository}
    factory {
        RewardRepositoryImpl(
            get(),
            get()
        ) as RewardRepository
    }
    factory {
        SessionRecordRepositoryImpl(
            get(),
            get()
        ) as SessionRecordRepository
    }
    factory {
        SessionPresetRepositoryImpl(
            get(),
            get()
        ) as SessionPresetRepository
    }
}