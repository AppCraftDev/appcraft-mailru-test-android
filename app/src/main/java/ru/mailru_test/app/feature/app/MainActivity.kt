package ru.mailru_test.app.feature.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.inject
import ru.mailru_test.R
import ru.mailru_test.app.Screens
import ru.mailru_test.domain.global.empty
import ru.mailru_test.global.extension.observeEvent
import ru.mailru_test.global.navigation.AppRouter
import ru.mailru_test.global.navigation.SupportAppNavigation
import ru.mailru_test.global.notifier.Notifier
import ru.mailru_test.global.notifier.SystemMessage
import ru.mailru_test.global.utils.showActionMessage
import ru.mailru_test.global.utils.showAlert
import ru.mailru_test.global.utils.showBarMessage

class MainActivity : AppCompatActivity() {

    private val notifier: Notifier by inject()

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: AppRouter by inject()

    private val navigator: Navigator = object : SupportAppNavigation(this, R.id.fragmentContainer) {}

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

    private fun subscribeOnSystemMessages() {
        observeEvent(notifier.notifier, ::onNextMessageNotify)
    }

    private fun onNextMessageNotify(systemMessage: SystemMessage) {
        val text = systemMessage.stringRes?.let { getString(it) } ?: systemMessage.text ?: return
        val actionText = systemMessage.actionRes?.let { getString(it) } ?: systemMessage.action ?: empty()

        val container = findViewById<View>(R.id.fragmentContainer)
        when (systemMessage.messageType) {
            SystemMessage.SystemMessageType.BAR -> {
                container.showBarMessage(text, systemMessage.level)
            }
            SystemMessage.SystemMessageType.ALERT -> {
                showAlert(text)
            }
            SystemMessage.SystemMessageType.ACTION -> {
                container.showActionMessage(text, actionText, false, systemMessage.actionCallback, systemMessage.level)
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
