package ru.mailru_test.domain.notifier

import androidx.annotation.StringRes

data class SystemMessage(
    @StringRes val stringRes: Int? = null,
    val text: String? = null,
    @StringRes val title: Int? = null,
    val action: String? = null,
    @StringRes val actionRes: Int? = null,
    val actionCallback: (() -> Unit?)? = null,
    val messageType: SystemMessageType = SystemMessageType.BAR,
    val level: Level = Level.NORMAL,
    @StringRes val bnOkText: Int? = null,
    @StringRes val bnCancelText: Int? = null,
    val cancelable: Boolean = true,
    val error: Exception? = null,
    val handleError: Boolean = false
) {
    enum class SystemMessageType {
        ALERT, BAR, ACTION
    }

    enum class Level {
        NORMAL,
        ERROR
    }
}