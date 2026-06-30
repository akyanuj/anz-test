package com.test.anzapplication.feature_users.common

import retrofit2.HttpException
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




fun Throwable.toDomainError(): DomainError = when (this) {
    is SocketTimeoutException -> DomainError.NetworkTimeout
    is UnknownHostException -> DomainError.NoInternet
    is HttpException ->
        DomainError.ServerError(
            code = code(),
            message = message()
        )
    else -> DomainError.Unknown(this)
}