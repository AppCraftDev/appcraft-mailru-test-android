package ru.mailru_test.global.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.mailru_test.R
import ru.mailru_test.domain.notifier.SystemMessage

fun View.showBarMessage(text: String, level: SystemMessage.Level) {
    val snackbar =
        Snackbar.make(this, text, Snackbar.LENGTH_LONG)
    val snackView = snackbar.view
    val snackbarTextView = snackView.findViewById<TextView>(R.id.snackbar_text)
    snackbarTextView.isSingleLine = false

    snackbarTextView.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextWhite))
//    snackView.setBackgroundResource(backgroundResource)

    snackbar.show()
}

fun View.showActionMessage(text: String, action: String?, indefinite: Boolean = false, actionCallback: (() -> Unit?)?, level: SystemMessage.Level) {
//    val backgroundResource = if (level == SystemMessage.Level.ERROR) R.drawable.bg_rounded_red else R.drawable.bg_rounded_primary
    val duration = if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
    val snackbar = Snackbar.make(this, text, duration)
    val snackView = snackbar.view
    val snackbarTextView = snackView.findViewById<TextView>(R.id.snackbar_text)
    snackbarTextView.isSingleLine = false

//    snackbarTextView.setTextColor(ContextCompat.getColor(this, R.color.colorTextWhite))
//    snackView.setBackgroundResource(backgroundResource)

    if (!action.isNullOrBlank() && actionCallback != null) {
        snackbar.setAction(action) {
            actionCallback.invoke()
        }
    }
    snackbar.show()
}

fun Context.showAlert(message: String) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .show()
}