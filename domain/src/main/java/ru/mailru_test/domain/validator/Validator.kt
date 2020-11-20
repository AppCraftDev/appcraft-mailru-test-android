package ru.mailru_test.domain.validator

import android.text.TextUtils
import android.util.Patterns

object Validator {

    fun isEmailValid(email: CharSequence): Boolean = !TextUtils.isEmpty(email) &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isTextValidLenght(text: CharSequence) = text.trim().length in 2..300

    fun isTextValid(text: CharSequence?) = !TextUtils.isEmpty(text)

    fun isTextLengthValid(text: CharSequence, minLength: Int, maxLength: Int) = text.trim().length in minLength..maxLength

}