package com.test.anzapplication.feature_users.presentation.state

import androidx.compose.runtime.Immutable
import com.test.anzapplication.feature_users.common.DomainError
import com.test.anzapplication.feature_users.domain.model.User

@Immutable
data class UsersUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: DomainError? = null
) {
    val hasError: Boolean = error != null
    val isEmpty: Boolean = !isLoading && users.isEmpty()
}
