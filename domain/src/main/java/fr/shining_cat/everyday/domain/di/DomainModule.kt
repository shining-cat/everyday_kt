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

package fr.shining_cat.everyday.domain.di

import fr.shining_cat.everyday.domain.FileMetadataRetrieveUseCase
import fr.shining_cat.everyday.domain.InitDefaultPrefsValuesUseCase
import fr.shining_cat.everyday.domain.sessionspresets.CreateSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.DeleteSessionPresetUseCase
import fr.shining_cat.everyday.domain.sessionspresets.LoadSessionPresetsUseCase
import fr.shining_cat.everyday.domain.sessionspresets.UpdateSessionPresetUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        InitDefaultPrefsValuesUseCase(get(), get())
    }
    factory {
        CreateSessionPresetUseCase(get(), get())
    }
    factory {
        UpdateSessionPresetUseCase(get(), get())
    }
    factory {
        LoadSessionPresetsUseCase(get(), get())
    }
    factory {
        DeleteSessionPresetUseCase(get(), get())
    }
    factory {
        FileMetadataRetrieveUseCase(get())
    }
}