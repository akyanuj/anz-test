package com.test.anzapplication.feature_users.fakes

import com.test.anzapplication.feature_users.data.remote.UserApi
import com.test.anzapplication.feature_users.data.remote.UserDto

class FakeUserApi : UserApi {

    var users: List<UserDto> = emptyList()

    var throwable: Throwable? = null

    override suspend fun getUsers(): List<UserDto> {
        throwable?.let { throw it }
        return users

    }
}