package com.test.anzapplication.feature_users.presentation.screen

import android.text.Layout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.test.anzapplication.feature_users.presentation.state.UsersIntent
import com.test.anzapplication.feature_users.presentation.viewmodel.UsersViewmodel
import java.lang.reflect.Modifier

@Composable
fun UsersScreen(
    navController: NavController,
    viewModel: UsersViewmodel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    // Trigger initial load when screen opens
    LaunchedEffect(Unit) {
        viewModel.handleIntent(UsersIntent.)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Layout.Alignment.Center))
        }

        if (state.error != null) {
            org.w3c.dom.Text(text = state.error!!, modifier = Modifier.align(Alignment.Center))
        }

        if (state.users.isNotEmpty()) {
            UserList(users = state.users)
        }
    }
}