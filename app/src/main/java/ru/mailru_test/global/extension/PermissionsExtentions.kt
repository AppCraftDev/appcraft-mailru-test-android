package ru.mailru_test.global.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener

fun Fragment.permissionContactsIsGranted(): PermissionState = requireContext().permissionIsGranted(getPermissionsContacts())

fun Fragment.requestContactsPermission(callback: (state: PermissionState) -> Unit): Boolean = requireContext().requestContactsPermission(callback)

fun Context.requestContactsPermission(callback: (state: PermissionState) -> Unit): Boolean {
    val permissions = getPermissionsContacts()
    return checkPermissions(permissions, callback)
}

fun Context.permissionIsGranted(permissions: List<String>): PermissionState {
    return if (checkPermissionIsGranted(permissions)) PermissionState.Granted else PermissionState.Denied()
}

fun Context.checkPermissionIsGranted(permissions: List<String>): Boolean {
    return permissions
        .map { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
        .all { it }
}

private fun Context.checkPermissions(permissions: List<String>, callback: (permissionsGranted: PermissionState) -> Unit): Boolean {
    val permissionsGranted = checkPermissionIsGranted(permissions)
    if (permissionsGranted) {
        callback(PermissionState.Granted)
    } else {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(
                object : BaseMultiplePermissionsListener() {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        val state = if (report?.areAllPermissionsGranted() == true) PermissionState.Granted
                        else PermissionState.Denied()
                        callback(state)
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        super.onPermissionRationaleShouldBeShown(permissions, token)
                        token?.continuePermissionRequest()
//                        callback(PermissionState.Denied(true))
                    }
                }
            )
            .onSameThread()
            .check()
    }
    return permissionsGranted
}

private fun getPermissionsContacts() = listOf(Manifest.permission.READ_CONTACTS)

sealed class PermissionState {
    data class Denied(val rationale: Boolean = false) : PermissionState()
    object Granted : PermissionState()
    object NotRequest : PermissionState()

    fun isGranted() = this is Granted

    fun isNeedRational() = this is Denied && this.rationale
}