package com.test.anzapplication.feature_users.data.remote

data class UserDto(
    val address: String,
    val company: String,
    val country: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val photo: String,
    val state: String,
    val username: String,
    val zip: String
)