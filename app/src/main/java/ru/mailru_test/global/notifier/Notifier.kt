package ru.mailru_test.global.notifier

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import ru.mailru_test.global.extension.postEvent
import ru.mailru_test.global.utils.Event

class Notifier {
    val notifier: MutableLiveData<Event<SystemMessage>> = MutableLiveData()

    fun sendMessage(text: String, level: SystemMessage.Level = SystemMessage.Level.NORMAL) {
        val msg = SystemMessage(
            text = text,
            messageType = SystemMessage.SystemMessageType.BAR,
            level = level
        )
        notifier.postEvent(msg)
    }

    fun sendMessage(
        @StringRes stringRes: Int,
        level: SystemMessage.Level = SystemMessage.Level.NORMAL
    ) {
        val msg = SystemMessage(
            stringRes = stringRes,
            messageType = SystemMessage.SystemMessageType.BAR,
            level = level
        )
        notifier.postEvent(msg)
    }

    fun sendAlert(text: String) {
        val msg = SystemMessage(
            text = text,
            messageType = SystemMessage.SystemMessageType.ALERT
        )
        notifier.postEvent(msg)
    }

    fun sendAlert(@StringRes stringRes: Int) {
        val msg = SystemMessage(
            stringRes = stringRes,
            messageType = SystemMessage.SystemMessageType.ALERT
        )
        notifier.postEvent(msg)
    }

    fun sendActionMessage(
        @StringRes textRes: Int,
        @StringRes actionTextRes: Int,
        actionCallback: () -> Unit?
    ) {
        val msg = SystemMessage(
            stringRes = textRes,
            actionRes = actionTextRes,
            actionCallback = actionCallback,
            messageType = SystemMessage.SystemMessageType.ACTION
        )
        notifier.postEvent(msg)
    }
}