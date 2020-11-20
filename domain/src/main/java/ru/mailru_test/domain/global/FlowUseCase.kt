package ru.mailru_test.domain.global

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import ru.mailru_test.domain.model.Result

abstract class FlowParamUseCase<in P, R : Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .flowOn(coroutineDispatcher)
        .map { Result.Success(it) as Result<R> }
        .catch { e -> emit(Result.Error(e as Exception)) }

    protected abstract fun execute(params: P): Flow<R>
}

abstract class FlowUseCase<R : Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(): Flow<Result<R>> = execute()
        .flowOn(coroutineDispatcher)
        .map { Result.Success(it) as Result<R> }
        .catch { e -> catchError(e) }

    protected abstract fun execute(): Flow<R>

    private suspend fun FlowCollector<Result<R>>.catchError(e: Throwable) {
        emit(Result.Error(e as Exception))
        catchCustomError(e)
    }
    open suspend fun FlowCollector<Result<R>>.catchCustomError(e: Throwable) {}
}