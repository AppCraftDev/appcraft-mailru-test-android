package ru.mailru_test.app.feature.contact

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_contact.*
import ru.mailru_test.R
import ru.mailru_test.app.Route
import ru.mailru_test.app.Screens
import ru.mailru_test.app.adapter.controller.PhoneNumbersController
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.extension.loadImageCorner
import ru.mailru_test.global.glide.GlideApp
import ru.mailru_test.global.ui.fragment.BaseFragment
import ru.mailru_test.resources.Constants.EXTRA_DATA

class ContactFragment : BaseFragment() {

    override val contentView = R.layout.fragment_contact

    private val contact by lazy { requireArguments().getParcelable<Contact>(EXTRA_DATA)!! }

    private val controller by lazy { PhoneNumbersController(this::onPhone) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateContact(contact)
    }

    override fun observeViewModel() {}

    private fun updateContact(contact: Contact) {
        rvPhones.adapter = controller.adapter
        controller.setData(contact)
    }

    private fun onPhone(phone: String) {
        router.navigateTo(Route.callPhone(phone))
    }
}