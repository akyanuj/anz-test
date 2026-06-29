package com.test.anzapplication.feature_users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.test.anzapplication.feature_users.data.common.DataResult
import com.test.anzapplication.feature_users.domain.usecase.GetUsersUseCase
import com.test.anzapplication.feature_users.presentation.state.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.test.anzapplication.feature_users.presentation.state.UsersIntent
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewmodel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchUsers()
    }

    fun onIntent(intent: UsersIntent) {
        when (intent) {
            UsersIntent.Refresh -> fetchUsers(isRefresh = true)
            is UsersIntent.UserClicked -> {
                emitNavigation(intent.user)
            }
        }
    }

    private fun fetchUsers(
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            _uiState.value = UsersUiState.Loading
            val result = getUsersUseCase()

            when (result) {
                is DataResult.Failure -> {
                    _uiState.value =
                        UsersUiState.Error(result.error)
                }

                is DataResult.Success -> {
                    _uiState.value = UsersUiState.Success(result.data)
                }

            }
        }
    }


}