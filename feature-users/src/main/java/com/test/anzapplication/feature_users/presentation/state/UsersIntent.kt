package com.test.anzapplication.feature_users.presentation.state

sealed interface UsersIntent {
    data object Load : UsersIntent
    data object Refresh : UsersIntent
}