package ru.mailru_test.app.feature.contacts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mailru_test.R
import ru.mailru_test.app.Screens
import ru.mailru_test.app.adapter.controller.ContactsPreviewController
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.extension.*
import ru.mailru_test.global.ui.fragment.BaseFragment

class ContactsFragment : BaseFragment() {

    override val contentView = R.layout.fragment_contacts

    private val contactsViewModel by viewModel<ContactsViewModel>()

    private val contactsController by lazy { ContactsPreviewController(this::onContact) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        initListeners()
    }

    private fun initAdapters() {
        rvContacts.adapter = contactsController.adapter
    }

    override fun observeViewModel() {
        contactsViewModel.withHandlers {
            observe(permissionCameraState) { state ->
                when (state) {
                    is PermissionState.NotRequest, is PermissionState.Denied -> enableContactsUI(false)
                    is PermissionState.Granted -> enableContactsUI(true)
                }
            }
            observe(contacts) {
                contactsController.setData(it)
            }
            if (permissionContactsIsGranted() == PermissionState.Granted) {
                setPermissionState(PermissionState.Granted)
            }
        }
    }

    private fun onContact(contact: Contact) {
        router.navigateTo(Screens.contactDetails(contact))
    }

    private fun initListeners() {
        bnPermission.setOnClickListener {
            onContactsPermission()
        }
    }

    private fun enableContactsUI(enable: Boolean) {
        rvContacts.isVisible = enable
        containerContactsRequest.isVisible = !enable
    }

    private fun onContactsPermission() {
        requestContactsPermission(contactsViewModel::setPermissionState)
    }
}