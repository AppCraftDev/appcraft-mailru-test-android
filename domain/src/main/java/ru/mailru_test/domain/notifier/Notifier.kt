package ru.mailru_test.domain.notifier

import androidx.annotation.StringRes
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class Notifier {
    private val notifierRelay: PublishRelay<SystemMessage> = PublishRelay.create()
    val notifier: Observable<SystemMessage> = notifierRelay.hide()

    fun sendMessage(text: String, level: SystemMessage.Level = SystemMessage.Level.NORMAL) {
        val msg = SystemMessage(
            text = text,
            messageType = SystemMessage.SystemMessageType.BAR,
            level = level
        )
        notifierRelay.accept(msg)
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
        notifierRelay.accept(msg)
    }

    fun sendAlert(text: String) {
        val msg = SystemMessage(
            text = text,
            messageType = SystemMessage.SystemMessageType.ALERT
        )
        notifierRelay.accept(msg)
    }

    fun sendAlert(@StringRes stringRes: Int) {
        val msg = SystemMessage(
            stringRes = stringRes,
            messageType = SystemMessage.SystemMessageType.ALERT
        )
        notifierRelay.accept(msg)
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
        notifierRelay.accept(msg)
    }
}