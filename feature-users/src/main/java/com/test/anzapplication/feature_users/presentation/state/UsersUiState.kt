package com.test.anzapplication.feature_users.presentation.state

import com.test.anzapplication.feature_users.common.DomainError
import com.test.anzapplication.feature_users.domain.model.User

sealed interface UsersUiState {
    data object Loading : UsersUiState
    data class Success(
        val users: List<User>,
        val isRefreshing: Boolean = false
    ) : UsersUiState

    data class Error(
        val error: DomainError
    ) : UsersUiState

}