package ru.mailru_test.app.delegate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import ru.mailru_test.global.extension.PermissionState

interface PermissionViewModelDelegate {
    val permissionContactsState: LiveData<PermissionState>

    fun setPermissionState(state: PermissionState)
}

class ContactsPermissionViewModelDelegate : PermissionViewModelDelegate {

    private val _permissionContactsState = MutableLiveData<PermissionState>(PermissionState.NotRequest)
    override val permissionContactsState: LiveData<PermissionState> = _permissionContactsState

    override fun setPermissionState(state: PermissionState) {
        _permissionContactsState.value = state
    }
}