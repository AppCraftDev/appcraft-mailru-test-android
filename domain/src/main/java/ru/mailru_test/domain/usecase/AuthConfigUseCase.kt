package ru.mailru_test.domain.usecase

import ru.mailru_test.domain.global.CoroutineProvider
import ru.mailru_test.domain.global.UseCase
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.domain.repository.ContactsRepository

class GetContactsUseCase(
    private val contactsRepository: ContactsRepository,
    coroutineProvider: CoroutineProvider
) : UseCase<List<Contact>>(coroutineProvider.io()) {
    override suspend fun execute() = contactsRepository.getContacts()
}