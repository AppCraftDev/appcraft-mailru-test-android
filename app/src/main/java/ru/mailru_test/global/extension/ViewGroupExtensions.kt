package ru.mailru_test.global.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

operator fun ViewGroup.get(index: Int): View? = getChildAt(index)

fun ViewGroup.forEachChild(action: (View) -> Unit) {
    for (index in 0 until childCount) {
        action(get(index)!!)
    }
}

fun ViewGroup.getAllChildren(): List<View> {
    val results = mutableListOf<View>()
    forEachChild { view ->
        results.add(view)
    }
    return results
}

fun ViewGroup.inflateLayout(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)!!