package ru.mailru_test.global.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T : Fragment> fragment(
    vararg extraParcelables: Pair<String, Parcelable>
) = (T::class.java.constructors.first().newInstance() as T).apply {
    arguments = Bundle().apply {
        extraParcelables.forEach { extra -> putParcelable(extra.first, extra.second) }
    }
}

fun Fragment.setupToolbar(title: String, withHome: Boolean = false) {
    val activity = (requireActivity() as AppCompatActivity)
    activity.title = title

    if (withHome) {
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}