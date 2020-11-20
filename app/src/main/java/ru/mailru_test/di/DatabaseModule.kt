package ru.mailru_test.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mailru_test.data.storage.SystemDb

internal val databaseModule = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                SystemDb::class.java,
                "mailru_test_system.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<SystemDb>().getUserDao() }
}