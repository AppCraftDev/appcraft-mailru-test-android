package ru.mailru_test.domain.repository

import ru.mailru_test.domain.model.Contact

interface ContactsRepository {

    suspend fun getContacts(): List<Contact>
}