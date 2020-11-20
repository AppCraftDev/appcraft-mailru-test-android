package ru.mailru_test.app.adapter

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.mailru_test.R
import ru.mailru_test.global.adapter.KotlinEpoxyHolder

@EpoxyModelClass
abstract class PhoneItem : EpoxyModelWithHolder<PhoneItem.Holder>() {

    override fun getDefaultLayout() = R.layout.item_phone

    @EpoxyAttribute
    lateinit var phone: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: (String) -> Unit = {}

    override fun bind(holder: Holder) {
        holder.apply {
            tvPhone.text = phone
            tvPhone.setOnClickListener {
                clickListener(phone)
            }
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val tvPhone by bind<TextView>(R.id.tvPhone)
    }
}