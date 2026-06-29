package com.test.anzapplication.feature_users.data.common

import com.test.anzapplication.feature_users.common.DomainError

sealed interface DataResult<out T> {
    data class Success<out T>(
        val data: T
    ) : DataResult<T>

    data class Failure(
        val error: DomainError
    ) : DataResult<Nothing>
}