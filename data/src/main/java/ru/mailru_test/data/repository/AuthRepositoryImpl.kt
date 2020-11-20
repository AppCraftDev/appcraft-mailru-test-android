package ru.mailru_test.data.repository

import android.content.Context
import ru.mailru_test.data.manager.AppPreferencesManager
import ru.mailru_test.data.storage.SystemDb
import ru.mailru_test.domain.repository.ContactsRepository

class AuthRepositoryImpl(
    private val context: Context,
    private val systemDb: SystemDb,
    private val prefManager: AppPreferencesManager
) : ContactsRepository {

    override suspend fun getContacts(): List<String> {
        TODO("Not yet implemented")
    }
}