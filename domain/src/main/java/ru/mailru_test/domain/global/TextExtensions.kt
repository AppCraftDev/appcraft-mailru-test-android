package ru.mailru_test.domain.global

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.KClass

fun String?.withSpace(toAppend: String?): String = (this ?: empty()).plus(" ").plus(toAppend ?: empty())

fun String.withDoubleSpace(toAppend: String): String = plus("  ").plus(toAppend)

fun String.withComma(toAppend: String?): String = if (toAppend.isNullOrEmpty()) this else plus(", ").plus(toAppend)

fun String.comma(): String = plus(",")

fun String.withSpacedSlash(toAppend: String): String = plus(" / ").plus(toAppend)

fun String.withSlash(toAppend: String): String = plus("/").plus(toAppend)

fun String.withParagraph(toAppend: String): String = plus("\n").plus(toAppend)

fun String.capitalizeWords() = toLowerCase().words().map { it.capitalize() }.joinToString { space() }

fun String.words() = split(space())

fun comma() = ","

fun String.removeWhitespaces(): String = replace("\\s".toRegex(), "")

fun String.removePlus(): String = replace("+", "")

fun String.removeLastChar(): String = substring(0, count() - 1)

fun dot() = "."

fun zero() = "0"

fun slash() = "/"

fun empty() = ""

fun space() = " "

fun grill() = "#"

fun hyphen() = "-"

fun hyphenLarge() = "—"

fun plus() = "+"

fun underscore() = "_"

fun ellipsis() = "..."

fun percent() = "%"

fun colon() = ":"

fun newLine() = "\n"

fun String.hyphenToUnderscore() = replace(hyphen(), underscore())

fun String.insert(text: String, position: Int) = substring(0, position) + text + substring(position)

fun String.insertBefore(text: String, before: String): String {
    val lastIndex = lastIndexOf(".")
    return substring(0, lastIndex) + text + substring(lastIndex)
}

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
} else {
    Html.fromHtml(this)
}

fun String.getFileNameFromPath(): String {
    return substring(lastIndexOf("/") + 1)
}

fun String.getFileExtFromPath(skipFirst: Boolean = false): String {
    val skip = if (skipFirst) 1 else 0
    return substring(lastIndexOf(".") + skip)
}

fun String.getFileName(): String {
    return substring(0, lastIndexOf("."))
}


fun Long.getBytesFormatted(): String {
    return if (this > 1024) (this / 1024).toString().plus("КБ")
    else toString().plus("Б")
}

fun TextView.clearText() {
    text = null
}

fun String.substringWithDots(startIndex: Int): String = if (length > startIndex) substring(0, startIndex).plus(ellipsis()) else this

private object NameResolver {
    private val packageName = NameResolver::class.java.`package`
    val bundleKey = "$packageName.bundle"
    val actionKey = "$packageName.action"
    val preferenceKey = "$packageName.preference"
    val loggerKey = "mailru_test"
    val nameClass = javaClass.simpleName
}

fun String.asPreferenceKey(): String = "${NameResolver.preferenceKey}.$this"

fun String.asTag(): String = "${NameResolver.loggerKey}.${this.toUpperCase(Locale.getDefault())}"

fun <T : Any> KClass<T>.asSimpleName(): String = this::class.java.simpleName

inline fun <reified T> Moshi.toJson(value: T): String {
    return adapter(T::class.java).toJson(value)
}

inline fun <reified T> Moshi.fromJsonList(value: String): List<T>? {
    val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
    return adapter<List<T>>(type).fromJson(value)
}

inline fun <reified T> Moshi.fromJson(value: String): T? {
    return adapter(T::class.java).fromJson(value)
}

fun Double.fmt(): String {
    return if (this.compareTo(this.toLong()) == 0)
        String.format("%d", this.toLong())
    else
        String.format("%.2f", this)
}

fun getNotEmptyParagraph(v1: String, v2: String): String {
    return if (v1.isEmpty() && v2.isEmpty()) return empty()
    else if (v1.isEmpty()) v2
    else if (v2.isEmpty()) v1
    else v1.withParagraph(v2)
}