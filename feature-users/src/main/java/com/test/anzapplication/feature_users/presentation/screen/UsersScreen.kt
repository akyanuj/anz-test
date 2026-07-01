package com.test.anzapplication.feature_users.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.anzapplication.feature.users.R
import com.test.anzapplication.feature_users.common.toMessage
import com.test.anzapplication.feature_users.domain.model.User
import com.test.anzapplication.feature_users.presentation.state.UsersIntent
import com.test.anzapplication.feature_users.presentation.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onUserClick: (User) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(UsersIntent.Load)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.users_list))
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onIntent(UsersIntent.Refresh)
                        },
                        enabled = !state.isLoading && !state.isRefreshing
                    ) {
                        if (state.isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = stringResource(R.string.refresh)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.hasError && state.users.isEmpty() -> {
                    ErrorContent(
                        error = state.error,
                        onRetry = {
                            viewModel.onIntent(UsersIntent.Load)
                        },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                    state.users.isEmpty() -> {
                        Text(
                            text = stringResource(R.string.no_users_found),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                else ->
                    UserRowItem(
                        users = state.users,
                        onUserClick = onUserClick
                    )

            }
        }
    }
}

