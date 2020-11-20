package ru.mailru_test.global.adapter

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseViewHolder(view: View) :
    RecyclerView.ViewHolder(view), LayoutContainer {

    protected fun <V : View> bind(id: Int): ReadOnlyProperty<BaseViewHolder, V> =
        Lazy { holder: BaseViewHolder, prop ->
            itemView.findViewById(id) as V?
                ?: throw IllegalStateException("View ID $id for '${prop.name}' not found.")
        }

    val context: Context = itemView.context

    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)

    fun getDrawable(@DrawableRes drawable: Int) =
        ContextCompat.getDrawable(itemView.context, drawable)

    fun getString(@StringRes stringRes: Int): String = itemView.context.getString(stringRes)

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String =
        itemView.context.getString(stringRes, *formatArgs)

    private class Lazy<V>(
        private val initializer: (BaseViewHolder, KProperty<*>) -> V
    ) : ReadOnlyProperty<BaseViewHolder, V> {
        private object EMPTY

        private var value: Any? = EMPTY

        override fun getValue(thisRef: BaseViewHolder, property: KProperty<*>): V {
            if (value == EMPTY) {
                value = initializer(thisRef, property)
            }
            @Suppress("UNCHECKED_CAST")
            return value as V
        }
    }
}