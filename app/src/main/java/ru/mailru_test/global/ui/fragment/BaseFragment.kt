package ru.mailru_test.global.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.mailru_test.R
import ru.mailru_test.domain.notifier.SystemMessage
import ru.mailru_test.global.extension.observe
import ru.mailru_test.global.navigation.AppRouter
import ru.mailru_test.global.ui.viewmodel.BaseViewModel
import ru.mailru_test.global.utils.showActionMessage
import ru.mailru_test.global.utils.showBarMessage

abstract class BaseFragment : Fragment() {

    open val refreshContent: Boolean = true

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
            val noNetwork = false
            val isShow = showError(error)
            if (isShow) errorLiveData.value?.setHandleContent()
            if (!isShow && noNetwork) {
                if (refreshContent) {
                    view?.showActionMessage(getString(R.string.error_unknown), getString(R.string.error_refresh), true, {
                        errorLiveData.value?.setHandleContent()
                        refreshContent()
                    }, SystemMessage.Level.ERROR)
                } else {
                    view?.showBarMessage(getString(R.string.error_unknown), SystemMessage.Level.ERROR)
                    errorLiveData.value?.setHandleContent()
                }
                true
            } else {
                errorLiveData.value?.setHandleContent()
                isShow
            }
        }
    }

    open fun showError(error: Throwable): Boolean {
        return false
    }

    open fun refreshContent() {}

    open fun visibleLoading(visible: Boolean) {}

    companion object {
        private const val STATUS_BAR_ANIM_DELAY = 0L
    }
}
