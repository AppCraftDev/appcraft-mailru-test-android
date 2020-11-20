package ru.mailru_test.global.extension

import android.app.Activity
import android.content.Intent
import ru.mailru_test.app.feature.app.MainActivity
import kotlin.system.exitProcess

fun Activity?.restartApp() {
    val intent = Intent(this, MainActivity::class.java)
    val restartIntent = Intent.makeRestartActivityTask(intent.component)
    this?.startActivity(restartIntent)
    exitProcess(0)
}