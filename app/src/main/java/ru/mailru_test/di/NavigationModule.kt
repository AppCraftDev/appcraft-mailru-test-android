package ru.mailru_test.di

import com.github.terrakok.cicerone.Cicerone
import org.koin.dsl.module
import ru.mailru_test.global.navigation.AppRouter

internal val navigationModule = module {
    val cicerone: Cicerone<AppRouter> = Cicerone.create(AppRouter())
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}