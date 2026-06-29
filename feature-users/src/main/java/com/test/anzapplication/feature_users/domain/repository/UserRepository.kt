package com.test.anzapplication.feature_users.domain.repository

import com.test.anzapplication.feature_users.data.common.DataResult
import com.test.anzapplication.feature_users.domain.model.User

// domain knows nothing about retrofit or dto
interface UserRepository {
    suspend fun getUsers(): DataResult<List<User>>

}