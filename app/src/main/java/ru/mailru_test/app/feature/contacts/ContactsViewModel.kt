package ru.mailru_test.app.feature.contacts

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import ru.mailru_test.app.delegate.ContactsPermissionViewModelDelegate
import ru.mailru_test.app.delegate.PermissionViewModelDelegate
import ru.mailru_test.domain.model.Result
import ru.mailru_test.global.notifier.Notifier
import ru.mailru_test.domain.usecase.GetContactsUseCase
import ru.mailru_test.global.presentation.ErrorHandler
import ru.mailru_test.global.ui.viewmodel.BaseViewModel

class ContactsViewModel(
    errorHandler: ErrorHandler,
    notifier: Notifier,
    private val getContacts: GetContactsUseCase
) : BaseViewModel(errorHandler, notifier), PermissionViewModelDelegate by ContactsPermissionViewModelDelegate() {

    val contacts = permissionContactsState.distinctUntilChanged().switchMap {
        liveData {
            if (it.isGranted()) {
                emit(Result.Loading())
                emit(getContacts())
            }
        }
    }
}