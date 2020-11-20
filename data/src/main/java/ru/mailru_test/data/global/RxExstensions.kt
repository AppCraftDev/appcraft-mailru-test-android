package ru.mailru_test.data.global

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun <T> Maybe<T>.zipAndReturn(funcCall: (item: T) -> Completable) = flatMap {
    funcCall(it).andThen(Maybe.just(it))
}

fun <T> Flowable<T>.zipAndReturn(funcCall: (item: T) -> Completable) = flatMap {
    funcCall(it).andThen(Flowable.just(it))
}

fun <T> Single<T>.zipAndReturn(funcCall: (item: T) -> Completable) = flatMap {
    funcCall(it).andThen(Single.just(it))
}

fun <T> Single<T>.onErrorResumeNextNet(resumeFunctionInCaseOfError: Single<T>): Single<T> {
    return onErrorResumeNext {
        if (it is HttpException || it is TimeoutException || it is UnknownHostException) {
            resumeFunctionInCaseOfError
        } else {
            this
        }
    }
}

fun <T, R> Single<T>.zipWithPair(execute: Single<R>): Single<Pair<T, R>> {
    return zipWith(execute, io.reactivex.functions.BiFunction<T, R, Pair<T, R>> { t1, t2 ->
        t1 to t2
    })
}
