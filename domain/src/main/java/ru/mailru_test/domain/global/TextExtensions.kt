package ru.mailru_test.domain.global

import kotlin.reflect.KClass

fun empty() = ""

fun hyphen() = "-"

fun plus() = "+"

fun underscore() = "_"

fun ellipsis() = "..."

fun <T : Any> KClass<T>.asSimpleName(): String = this::class.java.simpleName
