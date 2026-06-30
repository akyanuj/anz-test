package com.test.anzapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test.anzapplication.feature_user_detail.presentation.screen.UserDetailScreen
import com.test.anzapplication.feature_users.domain.model.User
import com.test.anzapplication.feature_users.presentation.screen.UsersScreen

sealed class Screen(val route: String) {
    object UserList : Screen("user_list")
    object UserDetail : Screen("user_detail")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route
    ) {
        composable(Screen.UserList.route) {
            UsersScreen(
                onUserClick = { user ->
                    // Save the user to the current backstack entry's SavedStateHandle
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_user", user)
                    navController.navigate(Screen.UserDetail.route)
                }
            )
        }

        composable(Screen.UserDetail.route) {
            // Retrieve the user from the previous backstack entry's SavedStateHandle
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("selected_user")
            
            if (user != null) {
                UserDetailScreen(
                    user = user,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}


