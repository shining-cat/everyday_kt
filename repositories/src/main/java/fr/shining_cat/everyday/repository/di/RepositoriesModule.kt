package fr.shining_cat.everyday.repository.di

import fr.shining_cat.everyday.repository.converter.RewardConverter
import fr.shining_cat.everyday.repository.converter.SessionPresetConverter
import fr.shining_cat.everyday.repository.converter.SessionRecordConverter
import fr.shining_cat.everyday.repository.repo.*
import org.koin.dsl.module

val repositoriesModule = module {

    // Converters
    factory { RewardConverter(get()) }
    factory { SessionRecordConverter(get()) }
    factory { SessionPresetConverter(get()) }

    //Repositories
    factory { CritterPartsRepositoryImpl() as CritterPartsRepository }
    factory { RewardRepositoryImpl(get(), get()) as RewardRepository }
    factory { SessionRecordRepositoryImpl(get(), get()) as SessionRecordRepository }
    factory { SessionPresetRepositoryImpl(get(), get()) as SessionPresetRepository }
}