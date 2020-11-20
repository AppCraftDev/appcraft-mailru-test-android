package ru.mailru_test.global

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

class AppLifecycleObserver(private val context: Context) : LifecycleObserver {
    private val disposables = CompositeDisposable()

    /**
     * When app enters foreground
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
    }

    /**
     * When app enters background
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
    }

    /**
     * When app surely enters foreground
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
    }

    protected fun finalize() = disposables.clear()
}