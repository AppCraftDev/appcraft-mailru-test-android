package ru.mailru_test.domain.global

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val DATE_SIMPLE_FORMAT = "dd MMMM yyyy"
const val DATE_SIMPLE_PROFILE_FORMAT = "dd.MM.yyyy"
const val DATE_SIMPLE_ALT_FORMAT = "dd.MM.yy"
const val TIME_FORMAT = "HH:mm"
const val MOTH_FORMAT = "dd MMMM"
const val CHAT_FORMAT = "dd MMMM"
const val MONTH_FORMAT = "LLLL"
const val DAY_FORMAT = "dd EEE"
const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm:ss"
const val DATE_TIME_FORMAT_ALARM = "dd.MM.yy.HH:mm"

fun Long.getTimeFormatted() = String.format(
    Locale.getDefault(), "%02d:%02d",
    TimeUnit.MILLISECONDS.toMinutes(this),
    this - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(this))
)

fun Long.toDate() = Date(this)

fun Long.getTimeFormatted(pattern: String): String = SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))

fun Date.getTimeFormatted(pattern: String): String = SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun Date.getTimeSecond() = time / 1000

fun String.parseDate(pattern: String): Date {
    return if (this.isBlank()) Date()
    else SimpleDateFormat(pattern, Locale.getDefault()).parse(this)!!
}

fun Date.resetTime(): Date {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.parse(formatter.format(this))
}

fun getDefaultTimeDeparture(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 14)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    return calendar.time
}

fun Date.afterDay(whenDate: Date): Boolean {
    val c1 = Calendar.getInstance()
    c1.time = this
    val c2 = Calendar.getInstance()
    c2.time = whenDate
    return c2.get(Calendar.YEAR) > c1.get(Calendar.YEAR) || c2.get(Calendar.DAY_OF_YEAR) > c1.get(Calendar.DAY_OF_YEAR)
}

fun Date.afterMonth(whenDate: Date): Boolean {
    val c1 = Calendar.getInstance()
    c1.time = this
    val c2 = Calendar.getInstance()
    c2.time = whenDate
    return c2.get(Calendar.YEAR) > c1.get(Calendar.YEAR) || c2.get(Calendar.MONTH) > c1.get(Calendar.MONTH)
}

fun getDateMinYear(year: Int): Date {
    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR, -year)
    return cal.time
}

fun Date.beforeDay(days: Int): Boolean {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, days)
    return this.resetTime().before(cal.time.resetTime())
}

fun Date.beforeDay(whenDate: Date): Boolean {
    return this.resetTime().before(whenDate.resetTime())
}

fun Date.plusDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, days)
    return calendar.time
}

fun Date.toUtcDate(): Date? {
    val dateStr = getTimeFormatted(DATE_TIME_FORMAT_ALARM)
    val sdf = SimpleDateFormat(DATE_TIME_FORMAT_ALARM, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC");
    return sdf.parse(dateStr)
}