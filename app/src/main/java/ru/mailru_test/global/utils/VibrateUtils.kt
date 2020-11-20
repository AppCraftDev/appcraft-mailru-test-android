package ru.mailru_test.global.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

object VibrateUtils {
    private const val DEFAULT_DURATION_MS = 500L

    @Suppress("DEPRECATION")
    fun vibrate(context: Context, durationMs: Long = DEFAULT_DURATION_MS) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    durationMs,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(durationMs)
        }
    }
}