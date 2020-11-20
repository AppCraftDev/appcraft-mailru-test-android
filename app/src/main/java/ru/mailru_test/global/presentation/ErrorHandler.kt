package ru.mailru_test.global.presentation

import android.content.res.Resources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import ru.mailru_test.R
import ru.mailru_test.resources.BuildConfig
import timber.log.Timber
import java.io.FileNotFoundException
import java.io.IOException

class ErrorHandler(var res: Resources) {

    fun proceed(error: Throwable, process: (Throwable) -> Boolean, callback: (String) -> Unit) {
        Timber.e(error)
        FirebaseCrashlytics.getInstance().recordException(error)
        val processError = process(error)
        if (!processError) callback(getUserMessage(error))
    }

    @Suppress("ConstantConditionIf")
    private fun getUserMessage(error: Throwable): String {
        return when (error) {
            is IOException -> res.getString(getIOErrorMessageRes(error))
            is IllegalArgumentException -> {
                return if (error.message?.contains("checkParameterIsNotNull") == true) {

                    val messageHeader = res.getString(R.string.error_argument_common)
                    val regex = Regex(".* parameter (?:\\\$\\w+\\\$)?(\\w+)")
                    val nullParameter = error.message?.replace(regex, "$1") ?: ""
                    val messageDetails = if (nullParameter == "collectionSizeOrDefault")
                        res.getString(R.string.error_argument_list_null)
                    else res.getString(R.string.error_argument_null, nullParameter)

                    "$messageHeader\n$messageDetails"
                } else error.message ?: ""
            }
            else -> {
                if (BuildConfig.BUILD_TYPE == "debug" || BuildConfig.BUILD_TYPE == "internal")
                    error.message ?: res.getString(R.string.error_unknown)
                else
                    res.getString(R.string.error_unknown)
            }
        }
    }

    private fun getIOErrorMessageRes(error: IOException): Int {
        return when (error) {
            is FileNotFoundException -> R.string.error_file_not_found
            else -> R.string.error_unknown_network
        }
    }
}