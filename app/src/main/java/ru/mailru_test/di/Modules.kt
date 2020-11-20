package ru.mailru_test.di

val appComponent = listOf(
    appModule,
    repositoriesModule,
    interactorModule,
    databaseModule,
    viewModelsModule,
    navigationModule
)