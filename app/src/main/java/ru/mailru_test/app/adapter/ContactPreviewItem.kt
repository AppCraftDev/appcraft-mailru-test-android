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
import ru.mailru_test.global.glide.GlideApp

@EpoxyModelClass
abstract class ContactPreviewItem : EpoxyModelWithHolder<ContactPreviewItem.Holder>() {

    override fun getDefaultLayout() = R.layout.item_contact

    @EpoxyAttribute
    lateinit var contact: Contact

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: (Contact) -> Unit = {}

    override fun bind(holder: Holder) {
        holder.apply {
            tvName.text = contact.label
            tvPhone.text = contact.phoneNumbers.firstOrNull()
            containerContact.setOnClickListener {
                clickListener(contact)
            }
            GlideApp.with(ivAvatar)
                .load(contact.avatarThumbnail)
                .into(ivAvatar)
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val containerContact by bind<View>(R.id.containerContact)
        val tvPhone by bind<TextView>(R.id.tvPhone)
        val tvName by bind<TextView>(R.id.tvName)
        val ivAvatar by bind<ImageView>(R.id.ivAvatar)
    }
}