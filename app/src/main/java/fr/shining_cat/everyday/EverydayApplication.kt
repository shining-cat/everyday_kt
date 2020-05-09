package fr.shining_cat.everyday

import android.app.Application
import com.facebook.stetho.Stetho
import fr.shining_cat.everyday.commons.di.commonsModule
import fr.shining_cat.everyday.di.appModule
import fr.shining_cat.everyday.domain.di.domainModule
import fr.shining_cat.everyday.domain.di.settingsModule
import fr.shining_cat.everyday.locale.di.localeModule
import fr.shining_cat.everyday.models.di.modelsModule
import fr.shining_cat.everyday.repository.di.repositoriesModule
import fr.shining_cat.everyday.screens.di.screensModule
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

            //TODO: remember to add every new module here once it has its own Module declaration listing its dependencies
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