package com.test.anzapplication.feature_users.presentation.state

sealed interface UsersIntent {

    data object Refresh : UsersIntent

    data class UserClicked(val userId: Int) : UsersIntent

}