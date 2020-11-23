package ru.mailru_test.global.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mailru_test.domain.model.Result
import ru.mailru_test.global.notifier.Notifier
import ru.mailru_test.global.presentation.ErrorHandler
import ru.mailru_test.global.utils.Event

abstract class BaseViewModel(private val errorHandler: ErrorHandler, private val notifier: Notifier) : ViewModel() {

    val errorLiveData = MutableLiveData<Event<Result.Error>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    var processError: (throwable: Throwable) -> Boolean = { false }

    fun setProgress(show: Boolean) {
        loadingLiveData.value = show
    }

    fun handleError(throwable: Throwable) {
        errorHandler.proceed(throwable, { error ->
            return@proceed processError(error)
        }) { result ->
            notifier.sendMessage(result)
        }
    }
}
