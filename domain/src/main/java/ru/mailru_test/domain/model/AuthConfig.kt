package ru.mailru_test.domain.model

data class AuthConfig(
    var isAuth: Boolean = false,
    var isFirstLaunch: Boolean = false,
    var isSubscribedFcm: Boolean = false
)