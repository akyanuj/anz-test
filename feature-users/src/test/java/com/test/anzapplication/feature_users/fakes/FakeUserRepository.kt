package com.test.anzapplication.feature_users.fakes

import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.domain.model.User
import com.test.anzapplication.feature_users.domain.repository.UserRepository

class FakeUserRepository : UserRepository {
    var result: DataResult<List<User>> = DataResult.Success(emptyList())

    override suspend fun getUsers(): DataResult<List<User>> {
        return result
    }
}