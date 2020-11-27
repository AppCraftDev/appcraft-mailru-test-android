package ru.mailru_test.app

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.mailru_test.app.feature.contact.ContactFragment
import ru.mailru_test.app.feature.contacts.ContactsFragment
import ru.mailru_test.domain.global.asSimpleName
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.extension.fragment
import ru.mailru_test.resources.Constants.EXTRA_DATA

object Screens {

    fun contacts() = FragmentScreen(ContactsFragment::class.asSimpleName()) {
        fragment<ContactsFragment>()
    }

    fun contactDetails(contact: Contact) = FragmentScreen(ContactFragment::class.asSimpleName()) {
        fragment<ContactFragment>(EXTRA_DATA to contact)
    }
}

object Route {

    fun callPhone(phone: String) = ActivityScreen("callPhone") {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:".plus(phone))
        }
    }

    fun openSettingDetails() = ActivityScreen("callPhone") {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + it.packageName)).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}