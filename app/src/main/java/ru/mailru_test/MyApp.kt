package ru.mailru_test

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.mailru_test.di.appComponent
import ru.mailru_test.global.utils.CrashlyticsTree
import ru.mailru_test.resources.BuildConfig
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initFirebase()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG)
                androidLogger(Level.ERROR)
            androidContext(this@MyApp)
            modules(appComponent)
        }
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }
}