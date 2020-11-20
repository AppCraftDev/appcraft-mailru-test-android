//package ru.mailru_test.app.adapter
//
//import android.widget.TextView
//import androidx.annotation.ColorRes
//import androidx.annotation.StringRes
//import com.airbnb.epoxy.EpoxyAttribute
//import com.airbnb.epoxy.EpoxyModelClass
//import com.airbnb.epoxy.EpoxyModelWithHolder
//import ru.mailru_test.R
//import ru.mailru_test.app.adapter.ButtonItem.TypeButtonItem.*
//import ru.mailru_test.global.adapter.KotlinEpoxyHolder
//
//@EpoxyModelClass
//abstract class ButtonItem : EpoxyModelWithHolder<ButtonItem.Holder>() {
//
//    override fun getDefaultLayout(): Int {
//        return when (type) {
//            TEXT -> R.layout.item_text_button
//            COLORED -> R.layout.item_button
//            DISABLED -> R.layout.item_text_button
//        }
//    }
//
//    @EpoxyAttribute
//    @StringRes
//    var textRes: Int = 0
//
//    @EpoxyAttribute
//    @ColorRes
//    var bgColor: Int? = null
//
//    @EpoxyAttribute
//    @ColorRes
//    var textColor: Int? = null
//
//    @EpoxyAttribute
//    lateinit var type: TypeButtonItem
//
//    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
//    var clickListener: () -> Unit = {}
//
//    override fun bind(holder: Holder) {
//        holder.apply {
//            button.text = getString(textRes)
//            button.setOnClickListener { if (type != DISABLED) clickListener() }
//
//            val textColor = textColor ?: type.textColor
//            button.setTextColor(getColor(textColor))
//            val bgColor = bgColor ?: type.bgColor
//            button.setBackgroundColor(getColor(bgColor))
//        }
//    }
//
//    class Holder : KotlinEpoxyHolder() {
//        val button by bind<TextView>(R.id.button)
//    }
//
//    enum class TypeButtonItem(@ColorRes val textColor: Int, @ColorRes val bgColor: Int) {
//        TEXT(R.color.colorAccent, R.color.transparent),
//        COLORED(R.color.bn_color, R.color.colorAccent),
//        DISABLED(R.color.textHint, R.color.transparent)
//    }
//}