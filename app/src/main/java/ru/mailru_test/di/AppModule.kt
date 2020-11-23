package ru.mailru_test.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mailru_test.domain.global.CoroutineProvider
import ru.mailru_test.global.notifier.Notifier
import ru.mailru_test.global.presentation.ErrorHandler

internal val appModule = module {
    factory { androidContext().resources }

    single { Notifier() }
    single { ErrorHandler(get()) }
    single { CoroutineProvider() }
}