package ru.mailru_test

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.mailru_test.di.appComponent
import ru.mailru_test.global.AppLifecycleObserver
import ru.mailru_test.global.utils.CrashlyticsTree
import ru.mailru_test.resources.BuildConfig
import timber.log.Timber

class MyApp : Application() {
    private val appLifecycleObserver: AppLifecycleObserver by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLifecycleObserver()
        initFirebase()
        initRxErrorHandler()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }

        if(BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG)
                androidLogger(Level.ERROR)
            androidContext(this@MyApp)
            modules(appComponent)
        }
    }

    private fun initLifecycleObserver() {
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(appLifecycleObserver)
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { e: Throwable ->
            e.printStackTrace()
            val error = when (e) {
                is UndeliverableException -> e.cause!!
                is IllegalStateException -> return@setErrorHandler
                else -> null
            }
            Timber.e(error, "Undeliverable exception received, not sure what to do")
        }
    }
}