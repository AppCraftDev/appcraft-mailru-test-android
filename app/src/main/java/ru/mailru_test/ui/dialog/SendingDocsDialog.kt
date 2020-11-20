package ru.mailru_test.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SendingDocsDialog() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
//        val view = layoutInflater.inflate(R.layout.dialog_sending_docs, null)
//        bindView(view)
//        builder.setView(view)
//        isCancelable = false
        return builder
            .create()
    }

    private fun bindView(view: View) {
    }
}