package ru.mailru_test.global.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.mailru_test.global.utils.Event
import ru.mailru_test.global.utils.EventObserver


fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

fun <T : Any, L : LiveData<T?>> Fragment.observeNullable(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

fun <T : Any?, L : LiveData<T?>> Fragment.observeNonNullable(liveData: L, body: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer {
        if (it != null) body(it)
    })

fun <T : Any, L : LiveData<Event<T?>>> Fragment.observeNonNullableEvent(liveData: L, body: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, EventObserver {
        if (it != null) body(it)
    })
}

fun <T : Any, L : LiveData<Event<T>>> Fragment.observeEvent(liveData: L, body: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, EventObserver(body))
}

fun <T : Any, L : LiveData<Event<T?>>> Fragment.observeNullableEvent(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, EventObserver(body))
}

fun <T> MutableLiveData<T>.setValue(default: (() -> T)? = null, callback: T.() -> Unit) {
    val nValue = value ?: default?.let { it() }
    nValue?.let(callback)
    value = nValue
}

fun <T> MutableLiveData<T>.updateDuplicate(nValue: T) {
    if (value == null || value != nValue) value = nValue
}

fun <T> MutableLiveData<Event<T>>.postEvent(value: T) {
    postValue(Event(value))
}