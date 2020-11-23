package ru.mailru_test.global.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.mailru_test.global.utils.Event
import ru.mailru_test.global.utils.EventObserver

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

fun <T> MutableLiveData<Event<T>>.postEvent(value: T) {
    postValue(Event(value))
}

fun <T : Any, L : LiveData<Event<T>>> AppCompatActivity.observeEvent(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, EventObserver(body))
}