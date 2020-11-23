package ru.mailru_test.app.feature.contacts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.item_container_permission.*
import kotlinx.android.synthetic.main.item_progress_bar.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mailru_test.R
import ru.mailru_test.app.Route
import ru.mailru_test.app.Screens
import ru.mailru_test.app.adapter.controller.ContactsPreviewController
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.notifier.Notifier
import ru.mailru_test.global.extension.*
import ru.mailru_test.global.ui.fragment.BaseFragment

class ContactsFragment : BaseFragment() {

    override val contentView = R.layout.fragment_contacts

    private val contactsViewModel by viewModel<ContactsViewModel>()

    private val contactsController by lazy { ContactsPreviewController(this::onContact) }

    private val notifier: Notifier by inject()

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
            observe(permissionContactsState, ::handlePermissionState)
            observeResult(contacts, contactsController::setData)

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

    private fun handlePermissionState(state: PermissionState) {
        when (state) {
            is PermissionState.NotRequest -> enableContactsUI(false)
            is PermissionState.Denied -> {
                enableContactsUI(false)
                notifier.sendActionMessage(R.string.contacts_permission_rational, R.string.permission_setting, ::openSetting)
            }
            is PermissionState.Granted -> enableContactsUI(true)
        }
    }

    private fun enableContactsUI(enable: Boolean) {
        rvContacts.isVisible = enable
        containerContactsRequest.isVisible = !enable
    }

    private fun onContactsPermission() {
        requestContactsPermission(contactsViewModel::setPermissionState)
    }

    override fun visibleLoading(visible: Boolean) {
        progressView.visibleIf(visible)
    }

    private fun openSetting() {
        router.navigateTo(Route.openSettingDetails())
    }
}