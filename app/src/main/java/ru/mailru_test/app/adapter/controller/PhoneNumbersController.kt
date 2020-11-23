package ru.mailru_test.app.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import ru.mailru_test.app.adapter.contactItem
import ru.mailru_test.app.adapter.contactPreviewItem
import ru.mailru_test.app.adapter.phoneItem
import ru.mailru_test.domain.model.Contact

class PhoneNumbersController(private val onPhone: (String) -> Unit) : TypedEpoxyController<Contact>() {

    override fun buildModels(data: Contact) {
        contactItem {
            id("contactItem")
            contact(data)
        }
        data.phoneNumbers.forEach { item ->
            phoneItem {
                id(item)
                phone(item)
                clickListener(onPhone)
            }
        }
    }
}