package ru.mailru_test.di

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mailru_test.data.manager.AppPreferencesManager
import ru.mailru_test.data.manager.SecurityManager
import ru.mailru_test.data.manager.SecurityManagerImpl
import ru.mailru_test.domain.global.CoroutineProvider
import ru.mailru_test.domain.model.SchedulersProvider
import ru.mailru_test.domain.notifier.Notifier
import ru.mailru_test.global.AppLifecycleObserver
import ru.mailru_test.global.event.EventDispatcher
import ru.mailru_test.global.presentation.ErrorHandler

internal val appModule = module {
    factory { androidContext().resources }

    factory { AppLifecycleObserver(get()) }

    single { Notifier() }
    single { EventDispatcher() }
    single { ErrorHandler(get()) }
    single { CoroutineProvider() }

    //Managers
    single { AppPreferencesManager(androidContext()) }
    single<SecurityManager> { SecurityManagerImpl(get()) }

    single<SchedulersProvider> {
        object : SchedulersProvider {
            override fun ui(): Scheduler = AndroidSchedulers.mainThread()
            override fun computation(): Scheduler = Schedulers.computation()
            override fun trampoline(): Scheduler = Schedulers.trampoline()
            override fun newThread(): Scheduler = Schedulers.newThread()
            override fun io(): Scheduler = Schedulers.io()
        }
    }
}