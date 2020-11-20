package ru.mailru_test.global.extension

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.color
import ru.mailru_test.domain.global.space

fun Context.getColorString(@ColorRes colorRes: Int, @StringRes text: Int, @StringRes textColor: Int): SpannableStringBuilder {
    return getColorString(colorRes, getString(text), getString(textColor))
}

fun Context.getColorString(@ColorRes colorRes: Int, text: String, textColor: String): SpannableStringBuilder {
    val color = ContextCompat.getColor(this, colorRes)
    return SpannableStringBuilder()
        .append(text)
        .append(space())
        .color(color) { bold { append(textColor) } }
}