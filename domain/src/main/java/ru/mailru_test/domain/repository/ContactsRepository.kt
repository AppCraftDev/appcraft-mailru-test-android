package ru.mailru_test.domain.repository

interface ContactsRepository {

    suspend fun getContacts(): List<String>

}