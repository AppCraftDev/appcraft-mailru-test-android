package ru.mailru_test.global.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class RxViewAfterTextChange(private val view: TextView) : Observable<CharSequence>() {

    override fun subscribeActual(observer: Observer<in CharSequence>) {
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.addTextChangedListener(listener)
    }

    class Listener(private val textView: TextView, private val observer: Observer<in CharSequence>)
        : TextWatcher, MainThreadDisposable() {
        override fun onDispose() {
            textView.removeTextChangedListener(this)
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            if (!isDisposed) {
                observer.onNext(editable.toString())
            }
        }
    }
}