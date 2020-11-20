package ru.mailru_test.global.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment?.hideKeyboard() = this?.activity.hideKeyboard()

fun Activity?.hideKeyboard() {
    val view = this?.currentFocus ?: return
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Fragment?.toggleKeyboard() = this?.activity.toggleKeyboard()

fun Activity?.toggleKeyboard() {
    val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment?.showKeyboard() = this?.activity.showKeyboard()

fun Activity?.showKeyboard() {
    this?.hideKeyboard()
    this?.toggleKeyboard()
}