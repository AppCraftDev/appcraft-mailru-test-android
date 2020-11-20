package ru.mailru_test.data.global

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

inline fun <T, R> Flow<List<T>>.mapList(crossinline transform: suspend (value: T) -> R): Flow<List<R>> = transform { value ->
    return@transform emit(value.map { transform(it) })
}

inline fun <T, R> List<T>.mapList(crossinline transform: (value: T) -> R) = map { transform(it) }

