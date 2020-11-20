package ru.mailru_test.global.extension

//fun Fragment.showConfirmTaskDialog(
//    number: String,
//    onToTask: () -> Unit,
//    onToList: () -> Unit
//) {
//    ConfirmDialog(
//        colorContent = R.color.colorSecondary,
//        colorTitle = R.color.textMainLight,
//        image = R.drawable.ic_cloud_success,
//        title = getString(R.string.task_was_added_to_list, number),
//        subtitle = null,
//        okText = R.string.back_to_task,
//        cancelText = R.string.back_to_list,
//        callbackOk = onToTask,
//        callbackCancel = onToList
//    ).show(childFragmentManager, ConfirmDialog::class.simpleName)
//}
//
//fun Fragment.showErrorMessageDialog(
//    @StringRes text: Int,
//    @StringRes subtitle: Int,
//    @StringRes bnOkText: Int,
//    @StringRes bnCancelText: Int,
//    onOk: () -> Unit,
//    onCancel: () -> Unit = {}
//) {
//    ConfirmDialog(
//        colorContent = R.color.bgButtonError,
//        colorTitle = R.color.bgInputError,
//        image = R.drawable.ic_cloud_error,
//        title = getString(text),
//        subtitle = subtitle,
//        okText = bnOkText,
//        cancelText = bnCancelText,
//        callbackOk = onOk,
//        callbackCancel = onCancel
//    ).show(childFragmentManager, ConfirmDialog::class.simpleName)
//}
//
//fun Fragment.showSuccessMessageDialog(
//    @StringRes text: Int,
//    @StringRes bnText: Int,
//    onToTask: () -> Unit
//) {
//    ConfirmDialog(
//        colorContent = R.color.colorSecondary,
//        colorTitle = R.color.textMainLight,
//        image = R.drawable.ic_cloud_success,
//        title = getString(text),
//        okText = bnText,
//        callbackOk = onToTask
//    ).show(childFragmentManager, ConfirmDialog::class.simpleName)
//}
//
//fun Fragment.showDevicesCountDialog(devices: List<Devices>) {
//    DevicesDialog(devices).show(childFragmentManager, DevicesDialog::class.simpleName)
//}
//
//fun Fragment.showSelectDestinationDialog(sendToEmail: (String) -> Unit, sendToYourself: () -> Unit, callbackCancel: () -> Unit = {}) {
//    SelectDestinationDialog(
//        sendToEmail = {
//            showInputEmailDialog(sendToEmail, callbackCancel)
//        },
//        sendToYourself = {
//            sendToYourself()
//        },
//        callbackCancel = {
//            callbackCancel()
//        }
//    ).show(childFragmentManager, SelectDestinationDialog::class.simpleName)
//}
//
//fun Fragment.showInputEmailDialog(sendToEmail: (String) -> Unit, callbackCancel: () -> Unit) {
//    InputEmailDialog(
//        sendToEmail = {
//            sendToEmail(it)
//        },
//        callbackCancel = {
//            callbackCancel()
//        }
//    ).show(childFragmentManager, SelectDestinationDialog::class.simpleName)
//}
//
//fun Fragment.showConfirmStepDialog(
//    @StringRes title: Int,
//    @StringRes subtitle: Int,
//    @DrawableRes logo: Int,
//    onOk: () -> Unit,
//    onCancel: () -> Unit = {}
//) {
//    NextStepDialog(
//        title = title,
//        subtitle = subtitle,
//        logo = logo,
//        callbackOk = onOk,
//        callbackCancel = onCancel
//    ).show(childFragmentManager, NextStepDialog::class.simpleName)
//}
//
//fun Fragment.showNotFillingStepDialog(
//    @StringRes title: Int,
//    subtitle: String,
//    @DrawableRes logo: Int,
//    onOk: () -> Unit,
//    onCancel: () -> Unit = {}
//) {
//    NextStepDialog(
//        title = title,
//        subtitleStr = subtitle,
//        logo = logo,
//        next = R.string.go_to,
//        callbackOk = onOk,
//        callbackCancel = onCancel
//    ).show(childFragmentManager, NextStepDialog::class.simpleName)
//}
//
//fun Fragment.showDialogAttach(
//    selectCamera: () -> Unit,
//    selectGallery: () -> Unit = {}
//) {
//    AddAttachDialog(
//        selectCamera = selectCamera,
//        selectGallery = selectGallery
//    ).show(childFragmentManager, AddAttachDialog::class.simpleName)
//}
//
//fun Fragment.showSendingDialog() = SendingDocsDialog().also { dialog ->
//    dialog.show(childFragmentManager, SendingDocsDialog::class.simpleName)
//}
//
//fun Fragment.completeTaskDialog() {
//    CompleteTaskDialog().show(childFragmentManager, CompleteTaskDialog::class.simpleName)
//}
//
//fun Fragment.showConfirmDialog(
//    @StringRes title: Int,
//    @StringRes message: Int,
//    @StringRes yes: Int = R.string.confirm,
//    @StringRes no: Int = R.string.to_reject,
//    cancelable: Boolean = true,
//    callback: (positive: Boolean) -> Unit = {}
//) {
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(title)
//        .setMessage(message)
//        .setPositiveButton(yes) { _, _ -> callback(true) }
//        .setNegativeButton(no) { _, _ -> callback(false) }
//        .setOnCancelListener { callback(false) }
//        .setCancelable(cancelable)
//        .show()
//}

//fun Fragment.showCameraRequestDialog(accept: () -> (Unit), reject: () -> (Unit)) {
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(R.string.camera_permission_title)
//        .setMessage(R.string.camera_permission_description)
//        .setPositiveButton(R.string.to_allow) { _, _ ->
//            accept()
//        }
//        .setNegativeButton(R.string.to_reject) { _, _ ->
//            reject()
//        }
//        .show()
//}
//
//fun Fragment.showQrErrorDialog(@StringRes title: Int = R.string.qr_code_error_title, callback: () -> Unit = {}) {
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(title)
//        .setMessage(R.string.qr_code_error_subtitle)
//        .setPositiveButton(R.string.close) { _, _ -> callback() }
//        .setOnCancelListener { callback() }
//        .show()
//}
//
//fun Fragment.showSuccessDialog(
//    @StringRes title: Int = R.string.order_success,
//    @StringRes yes: Int = R.string.close,
//    callback: () -> Unit = {}
//) {
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(title)
//        .setMessage(empty())
//        .setPositiveButton(yes) { _, _ -> callback() }
//        .setOnCancelListener { callback() }
//        .setCancelable(true)
//        .show()
//}
//