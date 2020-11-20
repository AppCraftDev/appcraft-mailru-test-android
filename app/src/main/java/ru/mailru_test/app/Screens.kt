package ru.mailru_test.app

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.mailru_test.app.feature.InDevFragment
import ru.mailru_test.app.feature.contact.ContactDetailsFragment
import ru.mailru_test.app.feature.contacts.ContactsFragment
import ru.mailru_test.domain.global.asSimpleName
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.extension.fragment
import ru.mailru_test.resources.Constants.EXTRA_DATA

object Screens {

    fun contacts() = FragmentScreen(ContactsFragment::class.asSimpleName()) {
        fragment<ContactsFragment>()
    }

    fun contactDetails(contact: Contact) = FragmentScreen(ContactsFragment::class.asSimpleName()) {
        fragment<ContactDetailsFragment>(EXTRA_DATA to contact)
    }

    fun inDev() = FragmentScreen(InDevFragment::class.asSimpleName()) {
        fragment<InDevFragment>()
    }
}

object Route {

}