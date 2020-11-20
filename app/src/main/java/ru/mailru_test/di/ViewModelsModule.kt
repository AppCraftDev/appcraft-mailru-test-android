package ru.mailru_test.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mailru_test.app.feature.contacts.ContactsViewModel

internal val viewModelsModule = module {
    viewModel { ContactsViewModel(get(), get(), get()) }
}