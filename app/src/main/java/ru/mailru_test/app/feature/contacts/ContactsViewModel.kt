package ru.mailru_test.app.feature.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.domain.notifier.Notifier
import ru.mailru_test.domain.usecase.GetContactsUseCase
import ru.mailru_test.global.extension.PermissionState
import ru.mailru_test.global.presentation.ErrorHandler
import ru.mailru_test.global.ui.viewmodel.BaseViewModel

class ContactsViewModel(
    errorHandler: ErrorHandler,
    notifier: Notifier,
    private val getContactsUseCase: GetContactsUseCase
) : BaseViewModel(errorHandler, notifier) {

    val permissionCameraState = MutableLiveData<PermissionState>(PermissionState.NotRequest)

    val contacts: LiveData<List<Contact>> = liveData {
        getContactsUseCase().handleState()?.let {
            emit(it)
        }
    }

    fun setPermissionState(state: PermissionState) {
        permissionCameraState.value = state
    }

}