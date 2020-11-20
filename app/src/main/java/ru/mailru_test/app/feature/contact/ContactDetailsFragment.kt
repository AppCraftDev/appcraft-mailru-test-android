package ru.mailru_test.app.feature.contact

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_contact_details.*
import ru.mailru_test.R
import ru.mailru_test.app.adapter.controller.ContactsPreviewController
import ru.mailru_test.app.adapter.controller.PhoneNumbersController
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.glide.GlideApp
import ru.mailru_test.global.ui.fragment.BaseFragment
import ru.mailru_test.resources.Constants.EXTRA_DATA

class ContactDetailsFragment : BaseFragment() {

    override val contentView = R.layout.fragment_contact_details

    private val contact by lazy { requireArguments().getParcelable<Contact>(EXTRA_DATA)!! }

    private val controller by lazy { PhoneNumbersController(this::onPhone) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(contact)
    }

    override fun observeViewModel() {}

    private fun initUI(contact: Contact) {
        tvName.text = contact.label

        GlideApp.with(ivAvatar)
            .load(contact.avatar)
            .into(ivAvatar)

        rvPhones.adapter = controller.adapter
        controller.setData(contact.phoneNumbers)
    }

    private fun onPhone(phone: String) {
        //TODO: open phone
    }
}