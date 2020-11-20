package ru.mailru_test.domain.model

enum class NetStatus {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: NetStatus,
    val msg: String? = null) {
    companion object {
        val LOADED = NetworkState(NetStatus.SUCCESS)
        val LOADING = NetworkState(NetStatus.RUNNING)
        fun error(msg: String?) = NetworkState(NetStatus.FAILED, msg)
    }
}