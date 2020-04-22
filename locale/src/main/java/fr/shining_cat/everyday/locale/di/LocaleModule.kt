package fr.shining_cat.everyday.locale.di

import fr.shining_cat.everyday.locale.EveryDayRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localeModule = module {
    single { EveryDayRoomDatabase.getInstance(androidContext()) }
    single { (get() as EveryDayRoomDatabase).rewardDao() }
    single { (get() as EveryDayRoomDatabase).sessionPresetDao() }
    single { (get() as EveryDayRoomDatabase).sessionRecordDao() }
}