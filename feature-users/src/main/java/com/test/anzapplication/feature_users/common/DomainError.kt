package com.test.anzapplication.feature_users.common

import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface DomainError {
    data object NoInternet : DomainError
    data object NetworkTimeout : DomainError
    data class ServerError(
        val code: Int,
        val message: String?
    ) : DomainError

    data class Unknown(
        val throwable: Throwable? = null
    ) : DomainError
}




fun Throwable.toDomainError(): DomainError {
    return when (this) {
        is SocketTimeoutException -> DomainError.NetworkTimeout
        is UnknownHostException -> DomainError.NoInternet
        else -> DomainError.Unknown(this)
    }
}