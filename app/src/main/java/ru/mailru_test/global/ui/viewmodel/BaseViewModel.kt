package ru.mailru_test.global.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mailru_test.domain.model.Result
import ru.mailru_test.domain.model.data
import ru.mailru_test.domain.notifier.Notifier
import ru.mailru_test.global.presentation.ErrorHandler
import ru.mailru_test.global.utils.Event
import timber.log.Timber

abstract class BaseViewModel(val errorHandler: ErrorHandler, val notifier: Notifier) : ViewModel() {

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

    //TODO: refactor, add queue for progress
    fun <T> LiveData<Result<List<T>>>.handleListStates(progress: Boolean = true): LiveData<List<T>> =
        Transformations.map(this) { result ->
            when (result) {
                is Result.Success -> {
                    if (progress) loadingLiveData.value = false
                    return@map result.data
                }
                is Result.Error -> {
                    Timber.e(result.exception)
                    errorLiveData.value = Event(result)
                    if (progress) loadingLiveData.value = false
                }
                is Result.Loading -> {
                    if (progress) loadingLiveData.value = result.loading
                }
            }
            return@map emptyList<T>()
        }

    fun <T> LiveData<Result<T>>.handleState(progress: Boolean = true): LiveData<T?> =
        Transformations.map(this) { result ->
            when (result) {
                is Result.Success -> {
                    if (progress) loadingLiveData.value = false
                    return@map result.data
                }
                is Result.Error -> {
                    errorLiveData.value = Event(result)
                    if (progress) loadingLiveData.value = false
                }
                is Result.Loading -> {
                    if (progress) loadingLiveData.value = result.loading
                }
            }
            return@map null
        }

    fun <T> Result<T>.handleState(progress: Boolean = true): T? = when (this) {
        is Result.Success -> {
            if (progress) loadingLiveData.value = false
            data
        }
        is Result.Error -> {
            errorLiveData.value = Event(this)
            if (progress) loadingLiveData.value = false
            data
        }
        is Result.Loading -> {
            if (progress) loadingLiveData.value = loading
            null
        }
    }

    fun <T> Flow<Result<T>>.handleState(progress: Boolean = true): Flow<T?> =
        map { result ->
            when (result) {
                is Result.Success -> {
                    if (progress) loadingLiveData.value = false
                    return@map result.data
                }
                is Result.Error -> {
                    errorLiveData.value = Event(result)
                    if (progress) loadingLiveData.value = false
                }
                is Result.Loading -> {
                    if (progress) loadingLiveData.value = result.loading
                }
            }
            return@map null
        }
}
