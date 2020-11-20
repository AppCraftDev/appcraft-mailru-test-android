package ru.mailru_test.app.feature.splash

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mailru_test.R
import ru.mailru_test.global.extension.PermissionState
import ru.mailru_test.global.extension.observe
import ru.mailru_test.global.extension.permissionContactsIsGranted
import ru.mailru_test.global.extension.requestContactsPermission
import ru.mailru_test.global.ui.fragment.BaseFragment

class ContactsFragment : BaseFragment() {

    override val contentView = R.layout.fragment_contacts

    private val contactsViewModel by viewModel<ContactsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun observeViewModel() {
        contactsViewModel.withHandlers {
            observe(permissionCameraState) { state ->
                when (state) {
                    is PermissionState.NotRequest, is PermissionState.Denied -> enableContactsUI(false)
                    is PermissionState.Granted -> enableContactsUI(true)
                }
            }
            if (permissionContactsIsGranted() == PermissionState.Granted) {
                setPermissionState(PermissionState.Granted)
            }
        }
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