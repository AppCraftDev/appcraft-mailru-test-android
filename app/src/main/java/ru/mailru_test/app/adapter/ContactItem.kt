package ru.mailru_test.app.adapter

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
abstract class ContactItem : EpoxyModelWithHolder<ContactItem.Holder>() {

    override fun getDefaultLayout() = R.layout.item_contact

    @EpoxyAttribute
    lateinit var contact: Contact

    override fun bind(holder: Holder) {
        holder.apply {
            tvName.text = contact.label
            ivAvatar.loadImageCorner(contact.avatarThumbnail, holder = R.drawable.ic_launcher_foreground)
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val tvName by bind<TextView>(R.id.tvName)
        val ivAvatar by bind<ImageView>(R.id.ivAvatar)
    }
}