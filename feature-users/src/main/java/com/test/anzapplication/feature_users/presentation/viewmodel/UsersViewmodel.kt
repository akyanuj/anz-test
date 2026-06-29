package com.test.anzapplication.feature_users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.domain.usecase.GetUsersUseCase
import com.test.anzapplication.feature_users.presentation.state.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.test.anzapplication.feature_users.presentation.state.UsersIntent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun onIntent(intent: UsersIntent) {
        when (intent) {
            UsersIntent.LoadUsers ->  fetchUsers()
            UsersIntent.Refresh -> fetchUsers(isRefresh = true)
            is UsersIntent.UserClicked -> {
            }
        }
    }

    private fun fetchUsers(
        isRefresh: Boolean = false
    ) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = !isRefresh,
                    isRefreshing = isRefresh,
                    error = null
                )
            }

            when (val result = getUsersUseCase()) {
                is DataResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = result.error
                        )
                    }
                }

                is DataResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            users = result.data,
                            error = null
                        )
                    }
                }
            }
        }
    }
}