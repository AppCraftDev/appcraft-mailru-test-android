package ru.mailru_test.app.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.mailru_test.R
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.global.adapter.KotlinEpoxyHolder
import ru.mailru_test.global.extension.loadImageCorner

@EpoxyModelClass
abstract class ContactPreviewItem : EpoxyModelWithHolder<ContactPreviewItem.Holder>() {

    override fun getDefaultLayout() = R.layout.item_preview_contact

    @EpoxyAttribute
    lateinit var contact: Contact

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: (Contact) -> Unit = {}

    override fun bind(holder: Holder) {
        holder.apply {
            tvName.text = contact.label
            tvPhone.text = contact.phoneNumbers.firstOrNull()
            ivAvatar.loadImageCorner(contact.avatarThumbnail, holder = R.drawable.ic_launcher_foreground)
            cvContact.setOnClickListener { clickListener(contact) }
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val cvContact by bind<View>(R.id.cvContact)
        val tvPhone by bind<TextView>(R.id.tvPhone)
        val tvName by bind<TextView>(R.id.tvName)
        val ivAvatar by bind<ImageView>(R.id.ivAvatar)
    }
}