package ru.mailru_test.global.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import ru.mailru_test.global.extension.observe
import ru.mailru_test.global.navigation.AppRouter
import ru.mailru_test.global.ui.viewmodel.BaseViewModel
import ru.mailru_test.global.utils.Event

abstract class BaseFragment : Fragment() {

    protected val router: AppRouter by inject()

    @get:LayoutRes
    protected abstract val contentView: Int

    protected abstract fun observeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(contentView, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    fun <T : BaseViewModel> T.withHandlers(callback: T.() -> Unit) {
        callback(this)
        observe(errorLiveData) { event ->
            event.getNotHandled()?.let {
                handleError(it.exception)
            }
        }
        observe(loadingLiveData) {
            visibleLoading(it)
        }
        processError = { error ->
            showError(error)
        }
    }

    open fun showError(error: Throwable): Boolean {
        return false
    }

    open fun visibleLoading(visible: Boolean) {}

    fun <T : Any, L : LiveData<ru.mailru_test.domain.model.Result<T>>> BaseViewModel.observeResult(liveData: L, body: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ru.mailru_test.domain.model.Result.Success -> {
                    loadingLiveData.value = false
                    body(result.data)
                }
                is ru.mailru_test.domain.model.Result.Error -> {
                    errorLiveData.value = Event(result)
                    loadingLiveData.value = false
                }
                is ru.mailru_test.domain.model.Result.Loading -> {
                    loadingLiveData.value = result.loading
                }
            }
        })
    }
}
