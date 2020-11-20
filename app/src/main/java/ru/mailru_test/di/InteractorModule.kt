package ru.mailru_test.di

import org.koin.dsl.module
import ru.mailru_test.domain.usecase.GetContactsUseCase

internal val interactorModule = module {
    factory { GetContactsUseCase(get(), get()) }
}