package ru.mailru_test.app.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import ru.mailru_test.app.adapter.contactPreviewItem
import ru.mailru_test.domain.model.Contact

class ContactsPreviewController(private val onContact: (Contact) -> Unit) : TypedEpoxyController<List<Contact>>() {

    override fun buildModels(data: List<Contact>) {
        data.forEach { item ->
            contactPreviewItem {
                id(item.id)
                contact(item)
                clickListener(onContact)
            }
        }
    }
}