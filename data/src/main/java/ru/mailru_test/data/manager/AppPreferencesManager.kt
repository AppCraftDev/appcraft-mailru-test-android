package ru.mailru_test.data.manager

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import ru.mailru_test.domain.global.asPreferenceKey
import ru.mailru_test.domain.model.AuthConfig

class AppPreferencesManager(val context: Context) {

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences
            .create(
                "mailru_test_storage",
                "mailru_test_storage_key",
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    fun getAuthConfig(): AuthConfig {
        val isFirstLaunch = getFirstLaunch()
        val isSubscribedFcm = isSubscribedFcm()
        return AuthConfig(
            isAuth = !getAccessToken().isNullOrEmpty() && !getPasscode().isNullOrEmpty() && getPasscodeConfirm(),
            isFirstLaunch = isFirstLaunch,
            isSubscribedFcm = isSubscribedFcm
        )
    }

    fun getAccessToken() = this.sharedPreferences.getString(TOKEN_ACCESS_KEY, null)

    fun saveAccessToken(token: String) {
        this.sharedPreferences.edit().putString(TOKEN_ACCESS_KEY, token).apply()
    }

    fun getRefreshToken() = this.sharedPreferences.getString(TOKEN_REFRESH_KEY, null)

    fun saveRefreshToken(token: String) {
        this.sharedPreferences.edit().putString(TOKEN_REFRESH_KEY, token).apply()
    }

    fun clearTokens() {
        this.sharedPreferences.edit()
            .remove(TOKEN_ACCESS_KEY)
            .remove(TOKEN_REFRESH_KEY)
            .apply()
    }

    fun getFirstLaunch() = this.sharedPreferences.getBoolean(IS_FIRST_LAUNCH_KEY, true)

    fun saveFirstLaunch(value: Boolean) {
        this.sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH_KEY, value).apply()
    }

    fun saveSalt(value: String) {
        this.sharedPreferences.edit().putString(SALT, value).apply()
    }

    fun getSalt() = this.sharedPreferences.getString(SALT, null)

    fun savePasscode(value: String) {
        this.sharedPreferences.edit().putString(PASSCODE, value).apply()
    }

    fun getPasscode() = this.sharedPreferences.getString(PASSCODE, null)

    fun setPasscodeError(value: Int) {
        this.sharedPreferences.edit().putInt(PASSCODE_ERRORS, value).apply()
    }

    fun setPasscodeConfirm(confirm: Boolean) {
        this.sharedPreferences.edit().putBoolean(PASSCODE_CONFIRM, confirm).apply()
    }

    fun getPasscodeConfirm() = this.sharedPreferences.getBoolean(PASSCODE_CONFIRM, false)

    fun saveFcmToken(token: String) {
        this.sharedPreferences.edit().putString(TOKEN_FCM, token).apply()
    }

    fun getFcmToken() = this.sharedPreferences.getString(TOKEN_FCM, null)

    fun setSubscribedFcm(isSubscribed: Boolean) {
        this.sharedPreferences.edit().putBoolean(IS_SUBSCRIBED_FCM, isSubscribed).apply()
    }

    fun isSubscribedFcm() = this.sharedPreferences.getBoolean(IS_SUBSCRIBED_FCM, false)

    fun clear() {
        this.sharedPreferences.edit()
            .remove(TOKEN_ACCESS_KEY)
            .remove(TOKEN_REFRESH_KEY)
            .remove(SALT)
            .remove(PASSCODE)
            .remove(PASSCODE_ERRORS)
            .remove(PASSCODE_CONFIRM)
            .apply()
    }

    companion object {
        private val TOKEN_ACCESS_KEY = "token_access_key".asPreferenceKey()
        private val TOKEN_REFRESH_KEY = "token_refresh_key".asPreferenceKey()
        private val TOKEN_FCM = "fcm_token".asPreferenceKey()
        private val IS_SUBSCRIBED_FCM = "is_subscribed_fcm".asPreferenceKey()

        private val IS_FIRST_LAUNCH_KEY = "is_first_launch_key".asPreferenceKey()
        private val SALT = "salt".asPreferenceKey()
        private val PASSCODE = "passcode".asPreferenceKey()
        private val PASSCODE_CONFIRM = "passcode_confirm".asPreferenceKey()
        private val PASSCODE_ERRORS = "passcode_errors_count".asPreferenceKey()

    }
}