package ru.mailru_test.app.feature

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_in_dev.*
import ru.mailru_test.R
import ru.mailru_test.global.extension.setupToolbar
import ru.mailru_test.global.ui.fragment.BaseFragment

class InDevFragment : BaseFragment() {

    override val contentView = R.layout.fragment_in_dev

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbarInDevView, true)
    }

    override fun observeViewModel() {
    }
}