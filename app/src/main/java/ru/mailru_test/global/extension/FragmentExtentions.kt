package ru.mailru_test.global.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import java.util.*

inline fun <reified T : Fragment> fragment(
    vararg extraParcelables: Pair<String, Parcelable>
) = (T::class.java.constructors.first().newInstance() as T).apply {
    arguments = Bundle().apply {
        extraParcelables.forEach { extra -> putParcelable(extra.first, extra.second) }
    }
}