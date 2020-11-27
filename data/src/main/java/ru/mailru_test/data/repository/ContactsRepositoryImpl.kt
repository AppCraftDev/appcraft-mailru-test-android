package ru.mailru_test.data.repository

import android.content.Context
import ru.mailru_test.data.helper.ContactHelper
import ru.mailru_test.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val context: Context
) : ContactsRepository {

    override suspend fun getContacts() = ContactHelper.getContacts(context.contentResolver)
}