package ru.mailru_test.app.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import ru.mailru_test.app.adapter.phoneItem

class PhoneNumbersController(private val onPhone: (String) -> Unit) : TypedEpoxyController<List<String>>() {

    override fun buildModels(data: List<String>) {
        data.forEach { item ->
            phoneItem {
                id(item)
                phone(item)
                clickListener(onPhone)
            }
        }
    }
}