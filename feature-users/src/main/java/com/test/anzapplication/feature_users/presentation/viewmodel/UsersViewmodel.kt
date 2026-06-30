package com.test.anzapplication.feature_users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.domain.usecase.GetUsersUseCase
import com.test.anzapplication.feature_users.presentation.state.UsersIntent
import com.test.anzapplication.feature_users.presentation.state.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchUsers()
    }

    fun onIntent(intent: UsersIntent) {
        when (intent) {
            UsersIntent.Refresh -> fetchUsers(isRefresh = true)
            is UsersIntent.UserClicked -> Unit
        }
    }

    private fun fetchUsers(
        isRefresh: Boolean = false
    ) {

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = !isRefresh,
                    isRefreshing = isRefresh,
                    error = null
                )
            }

            when (val result = getUsersUseCase()) {
                is DataResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            users = result.data,
                            isLoading = false,
                            isRefreshing = false,
                            error = null
                        )
                    }
                }

                is DataResult.Failure -> {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = result.error
                        )
                    }
                }
            }
        }
    }
}
