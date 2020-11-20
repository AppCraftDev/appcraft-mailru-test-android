package ru.mailru_test.global.extension

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.mailru_test.R
import java.io.Serializable
import java.util.*

inline fun Fragment.addOnBackPressedCallback(crossinline onBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(owner = viewLifecycleOwner) {
        onBackPressed()
    }
}

inline fun <reified T : Fragment> fragment(
    vararg extraParcelables: Pair<String, Parcelable?>?,
    extraBoolean: Pair<String, Boolean>? = null,
    extraString: Pair<String, String?>? = null,
    extraLong: Pair<String, Long?>? = null,
    extraInt: Pair<String, Int>? = null,
    extraSerialized: Pair<String, Serializable>? = null,
    extraParcelablesList: Pair<String, ArrayList<out Parcelable>>? = null
) =
    (T::class.java.constructors.first().newInstance() as T).apply {
        arguments = Bundle().apply {
            extraParcelables.forEach { it?.let { extra -> extra.second?.let { putParcelable(extra.first, it) } } }
            extraBoolean?.let { putBoolean(it.first, it.second) }
            extraString?.let { it.second?.let { str -> putString(it.first, str) } }
            extraLong?.let { it.second?.let { id -> putLong(it.first, id) } }
            extraInt?.let { putInt(it.first, it.second) }
            extraSerialized?.let { putSerializable(it.first, it.second) }
            extraParcelablesList?.let { putParcelableArrayList(it.first, it.second) }
        }
    }

infix fun <A, B> A.toNullable(that: B?): Pair<A, B>? = that?.let { Pair(this, it) }

fun Fragment.getColor(@ColorRes color: Int) = ContextCompat.getColor(requireContext(), color)

fun Fragment.showDateAndTimePicker(date: Date?, maxDate: Date? = null, minDate: Date? = null, callback: (date: Date) -> Unit, callbackCancel: () -> Unit = {}) {
    showDatePicker(date, maxDate, minDate, callback = { dateDays ->
        showTimePicker(dateDays, callback = { dateTime ->
            callback(dateTime)
        }, callbackCancel = callbackCancel)
    }, callbackCancel = callbackCancel)
}

fun Fragment.showDatePicker(date: Date?, maxDate: Date?, minDate: Date?, callback: (date: Date) -> Unit, callbackCancel: () -> Unit = {}) {
    val currentCalendar = Calendar.getInstance()
    currentCalendar.time = date ?: Date()
    DatePickerDialog(
        requireContext(),
        R.style.AppThemeCalendar,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            callback(selectedCalendar.time)
        },
        currentCalendar.get(Calendar.YEAR),
        currentCalendar.get(Calendar.MONTH),
        currentCalendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        maxDate?.time?.let { datePicker.maxDate = it }
        minDate?.let { datePicker.minDate = minDate.time }
        setOnCancelListener {
            callbackCancel()
        }
        show()
    }
}

fun Fragment.showTimePicker(
    date: Date?,
    callback: (date: Date) -> Unit,
    callbackCancel: () -> Unit = {}
) {
    val currentCalendar = Calendar.getInstance()
    currentCalendar.time = date ?: Date()
    TimePickerDialog(
        requireContext(),
        R.style.TimePickerDialog,
        { _, hour, min ->
            val calendar = Calendar.getInstance().apply {
                date?.let { time = date }
                this.set(Calendar.HOUR_OF_DAY, hour)
                this.set(Calendar.MINUTE, min)
            }
            callback(calendar.time)
        }, 0, 0, true
    ).apply {
        setOnCancelListener {
            callbackCancel()
        }
        show()
    }
}

fun Fragment.getDimensionPixelOffset(@DimenRes id: Int) = resources.getDimensionPixelOffset(id)

fun Fragment.setupToolbar(toolbarView: Toolbar, withHome: Boolean = false) {
    setHasOptionsMenu(true)
    val activity = (requireActivity() as AppCompatActivity)
    activity.setSupportActionBar(toolbarView)
    if (withHome) {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}