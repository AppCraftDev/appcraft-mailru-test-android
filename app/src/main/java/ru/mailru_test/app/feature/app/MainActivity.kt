package ru.mailru_test.app.feature.app

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import ru.mailru_test.R
import ru.mailru_test.app.Screens
import ru.mailru_test.domain.global.empty
import ru.mailru_test.domain.model.SchedulersProvider
import ru.mailru_test.domain.notifier.Notifier
import ru.mailru_test.domain.notifier.SystemMessage
import ru.mailru_test.global.navigation.AppRouter
import ru.mailru_test.global.navigation.SupportAppNavigation
import ru.mailru_test.global.presentation.ErrorHandler
import ru.mailru_test.global.utils.showActionMessage
import ru.mailru_test.global.utils.showAlert
import ru.mailru_test.global.utils.showBarMessage

class MainActivity : AppCompatActivity() {

    private val notifier: Notifier by inject()

    private var notifierDisposable: Disposable? = null
    private val schedulersProvider: SchedulersProvider by inject()
    private val errorHandler: ErrorHandler by inject()

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: AppRouter by inject()
    private val backgroundDrawable by lazy { ColorDrawable(ContextCompat.getColor(this@MainActivity, R.color.bgMain)) }

    private val navigator: Navigator = object : SupportAppNavigation(this, R.id.fragmentContainer) {
        override fun setupFragmentTransaction(fragmentTransaction: FragmentTransaction, currentFragment: Fragment?, nextFragment: Fragment?) {
            super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.newRootScreen(Screens.contacts())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        router.exit()
        return super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        subscribeOnSystemMessages()
    }

    override fun onStop() {
        unsubscribeOnSystemMessages()
        super.onStop()
    }

    private fun subscribeOnSystemMessages() {
        notifierDisposable = notifier.notifier
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(::onNextMessageNotify)
    }

    private fun unsubscribeOnSystemMessages() {
        notifierDisposable?.let {
            if (!it.isDisposed)
                it.dispose()
        }
    }

    private fun onNextMessageNotify(systemMessage: SystemMessage) {
        val text = if (systemMessage.stringRes == null) {
            systemMessage.text ?: return
        } else {
            getString(systemMessage.stringRes!!)
        }
        val actionText = if (systemMessage.actionRes == null) {
            systemMessage.action ?: empty()
        } else {
            getString(systemMessage.actionRes!!)
        }

        val container = findViewById<View>(R.id.fragmentContainer)
        when (systemMessage.messageType) {
            SystemMessage.SystemMessageType.BAR -> {
                container.showBarMessage(text, systemMessage.level)
            }
            SystemMessage.SystemMessageType.ALERT -> {
                showAlert(text)
            }
            SystemMessage.SystemMessageType.ACTION -> {
                container.showActionMessage(
                    text,
                    actionText,
                    false,
                    systemMessage.actionCallback,
                    systemMessage.level
                )
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
