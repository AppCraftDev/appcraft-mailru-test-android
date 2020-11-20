package ru.mailru_test.domain.global

import kotlinx.coroutines.Dispatchers

class CoroutineProvider {

    fun io() = Dispatchers.IO

    fun default() = Dispatchers.Default

    fun main() = Dispatchers.Main

    fun mainImmediate() = Dispatchers.Main.immediate
}
