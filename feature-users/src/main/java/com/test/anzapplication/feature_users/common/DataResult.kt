package com.test.anzapplication.feature_users.common

sealed interface DataResult<out T> {
    data class Success<out T>(
        val data: T
    ) : DataResult<T>

    data class Failure(
        val error: DomainError
    ) : DataResult<Nothing>
}