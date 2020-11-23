package ru.mailru_test.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.ArrayMap
import ru.mailru_test.data.helper.ContactHelper
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.domain.repository.ContactsRepository
import java.lang.NullPointerException

class ContactsRepositoryImpl(
    private val context: Context
) : ContactsRepository {

    override suspend fun getContacts() = ContactHelper.getContacts(context.contentResolver)
}