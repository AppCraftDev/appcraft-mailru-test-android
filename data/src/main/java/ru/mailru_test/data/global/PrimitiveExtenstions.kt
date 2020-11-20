package ru.mailru_test.data.global

import android.content.res.Resources
import ru.mailru_test.domain.global.Copyable
import java.util.*

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun <T : Copyable<T>> List<T>.copyListWithContent(): List<T> {
    return mutableListOf<T>().apply { addAll(this@copyListWithContent) }.map { it.copy() }
}

fun Double?.isZeroOrNull() = this == null || this == 0.0

fun Long.toDate() = Date(this)

fun Date?.getAge(): Int? {
    if (this == null) return null
    var age = 0
    val now = Calendar.getInstance()
    val dob = Calendar.getInstance()
    dob.time = this
    val year1 = now[Calendar.YEAR]
    val year2 = dob[Calendar.YEAR]
    age = year1 - year2
    val month1 = now[Calendar.MONTH]
    val month2 = dob[Calendar.MONTH]
    if (month2 > month1) {
        age--
    } else if (month1 == month2) {
        val day1 = now[Calendar.DAY_OF_MONTH]
        val day2 = dob[Calendar.DAY_OF_MONTH]
        if (day2 > day1) age--
    }
    return age
}

