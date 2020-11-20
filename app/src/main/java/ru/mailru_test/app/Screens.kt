package ru.mailru_test.app

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.mailru_test.app.feature.InDevFragment
import ru.mailru_test.app.feature.splash.ContactsFragment
import ru.mailru_test.domain.global.asSimpleName
import ru.mailru_test.global.extension.fragment

object Screens {

    fun Splash() = FragmentScreen(ContactsFragment::class.asSimpleName()) {
        fragment<ContactsFragment>()
    }

    fun InDev() = FragmentScreen(InDevFragment::class.asSimpleName()) {
        fragment<InDevFragment>()
    }
}

object Route {

}